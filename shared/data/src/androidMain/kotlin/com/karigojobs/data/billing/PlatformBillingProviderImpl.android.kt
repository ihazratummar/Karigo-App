package com.karigojobs.data.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.karigojobs.datastore.store.MonetizationStore
import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class PlatformBillingProviderImpl(
    private val context: Context,
    private val monetizationStore: MonetizationStore
) : PlatformBillingProvider, PurchasesUpdatedListener {

    private val scope = CoroutineScope(Dispatchers.IO)

    // Continuation stored while waiting for user to complete or cancel Play Store purchase UI
    private var pendingPurchaseContinuation: CancellableContinuation<Result<Boolean>>? = null

    // Starts as null = prices not yet loaded from Play Store
    private val _availablePackages = MutableStateFlow<List<PaywallPackage>>(emptyList())
    actual override val availablePackages: StateFlow<List<PaywallPackage>> = _availablePackages.asStateFlow()

    private val _isPriceLoading = MutableStateFlow(true)
    actual override val isPriceLoading: StateFlow<Boolean> = _isPriceLoading.asStateFlow()

    private val productDetailsMap = mutableMapOf<String, ProductDetails>()

    private val pendingPurchasesParams = PendingPurchasesParams.newBuilder()
        .enableOneTimeProducts()
        .build()

    private val billingClient: BillingClient by lazy {
        BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases(pendingPurchasesParams)
            .build()
    }

    init {
        connectAndQuery()
    }

    private fun connectAndQuery() {
        if (billingClient.isReady) return
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    scope.launch {
                        queryProductPackages()
                        restorePurchases()
                    }
                } else {
                    _isPriceLoading.value = false
                }
            }

            override fun onBillingServiceDisconnected() {
                _isPriceLoading.value = false
            }
        })
    }

    actual override suspend fun queryProductPackages(): List<PaywallPackage> =
        suspendCancellableCoroutine { continuation ->
            if (!billingClient.isReady) {
                connectAndQuery()
                _isPriceLoading.value = false
                continuation.resume(emptyList())
                return@suspendCancellableCoroutine
            }

            _isPriceLoading.value = true
            val allPackages = mutableListOf<PaywallPackage>()

            // ── 1. Subscriptions (SUBS): Monthly & Yearly ──────────────────────
            val subsParams = QueryProductDetailsParams.newBuilder()
                .setProductList(
                    listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("pro_monthly_79")
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build(),
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("pro_yearly_599")
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
                    )
                ).build()

            billingClient.queryProductDetailsAsync(subsParams) { subsResult, subsDetailsResult ->
                if (subsResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    subsDetailsResult.productDetailsList.forEach { details ->
                        productDetailsMap[details.productId] = details
                        val pricing = details.subscriptionOfferDetails
                            ?.firstOrNull()
                            ?.pricingPhases
                            ?.pricingPhaseList
                            ?.firstOrNull()

                        if (pricing != null) {
                            when (details.productId) {
                                "pro_monthly_79" -> allPackages.add(
                                    PaywallPackage(
                                        planTier = PlanTier.PRO_MONTHLY,
                                        productId = details.productId,
                                        formattedPrice = pricing.formattedPrice,
                                        rawPrice = pricing.priceAmountMicros / 1_000_000.0,
                                        currencyCode = pricing.priceCurrencyCode
                                    )
                                )

                                "pro_yearly_599" -> allPackages.add(
                                    PaywallPackage(
                                        planTier = PlanTier.PRO_YEARLY,
                                        productId = details.productId,
                                        formattedPrice = pricing.formattedPrice,
                                        rawPrice = pricing.priceAmountMicros / 1_000_000.0,
                                        currencyCode = pricing.priceCurrencyCode,
                                        discountPercentBadge = "37% off"
                                    )
                                )
                            }
                        }
                    }
                }

                // ── 2. One-time purchase (INAPP): Lifetime ───────────────────────
                val inAppParams = QueryProductDetailsParams.newBuilder()
                    .setProductList(
                        listOf(
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId("pro_lifetime_349")
                                .setProductType(BillingClient.ProductType.INAPP)
                                .build()
                        )
                    ).build()

                billingClient.queryProductDetailsAsync(inAppParams) { inAppResult, inAppDetailsResult ->
                    if (inAppResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        inAppDetailsResult.productDetailsList.forEach { details ->
                            productDetailsMap[details.productId] = details
                            val pricing = details.oneTimePurchaseOfferDetails
                            if (pricing != null) {
                                allPackages.add(
                                    PaywallPackage(
                                        planTier = PlanTier.PRO_LIFETIME,
                                        productId = details.productId,
                                        formattedPrice = pricing.formattedPrice,
                                        rawPrice = pricing.priceAmountMicros / 1_000_000.0,
                                        currencyCode = pricing.priceCurrencyCode,
                                        discountPercentBadge = "Best Value"
                                    )
                                )
                            }
                        }
                    }

                    _availablePackages.value = allPackages
                    _isPriceLoading.value = false
                    continuation.resume(allPackages)
                }
            }
        }

    actual override suspend fun launchPurchase(planTier: PlanTier): Result<Boolean> = suspendCancellableCoroutine { continuation ->
        val productId = when (planTier) {
            PlanTier.PRO_MONTHLY -> "pro_monthly_79"
            PlanTier.PRO_YEARLY -> "pro_yearly_599"
            PlanTier.PRO_LIFETIME -> "pro_lifetime_349"
            PlanTier.FREE -> {
                continuation.resume(Result.success(false))
                return@suspendCancellableCoroutine
            }
        }

        val productDetails = productDetailsMap[productId]
        if (productDetails == null) {
            continuation.resume(Result.failure(Exception("Product ($productId) not found. Make sure it's published in Google Play Console.")))
            return@suspendCancellableCoroutine
        }

        val activity = ActivityProvider.currentActivity
            ?: context.findActivity()
        if (activity == null) {
            continuation.resume(Result.failure(Exception("Google Play Billing requires an active screen context.")))
            return@suspendCancellableCoroutine
        }

        val productDetailsParamsList = if (productDetails.productType == BillingClient.ProductType.SUBS) {
            val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken ?: ""
            listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .setOfferToken(offerToken)
                    .build()
            )
        } else {
            listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .build()
            )
        }

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        pendingPurchaseContinuation = continuation
        continuation.invokeOnCancellation {
            pendingPurchaseContinuation = null
        }

        val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
        if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
            pendingPurchaseContinuation = null
            continuation.resume(Result.failure(Exception("Failed to launch Google Play billing flow: ${billingResult.debugMessage}")))
        }
    }

    actual override suspend fun restorePurchases(): Result<Boolean> =
        suspendCancellableCoroutine { continuation ->
            if (!billingClient.isReady) {
                connectAndQuery()
                continuation.resume(Result.success(false))
                return@suspendCancellableCoroutine
            }

            var foundActive = false

            billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS)
                    .build()
            ) { subResult, subList ->
                if (subResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    subList.forEach { purchase ->
                        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                            foundActive = true
                            handlePurchase(purchase)
                        }
                    }
                }

                billingClient.queryPurchasesAsync(
                    QueryPurchasesParams.newBuilder()
                        .setProductType(BillingClient.ProductType.INAPP).build()
                ) { inAppResult, inAppList ->
                    if (inAppResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        inAppList.forEach { purchase ->
                            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                                foundActive = true
                                handlePurchase(purchase)
                            }
                        }
                    }
                    if (!foundActive) {
                        scope.launch {
                            monetizationStore.setProStatus(isPro = false, tier = PlanTier.FREE)
                        }
                    }
                    continuation.resume(Result.success(foundActive))
                }
            }
        }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        val cont = pendingPurchaseContinuation
        pendingPurchaseContinuation = null

        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                if (!purchases.isNullOrEmpty()) {
                    for (purchase in purchases) {
                        handlePurchase(purchase)
                    }
                    cont?.resume(Result.success(true))
                } else {
                    cont?.resume(Result.success(false))
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                cont?.resume(Result.failure(Exception("Purchase cancelled")))
            }
            else -> {
                val msg = billingResult.debugMessage.ifBlank { "Billing error ${billingResult.responseCode}" }
                cont?.resume(Result.failure(Exception(msg)))
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val planTier = when {
                purchase.products.contains("pro_yearly_599") -> PlanTier.PRO_YEARLY
                purchase.products.contains("pro_lifetime_349") -> PlanTier.PRO_LIFETIME
                else -> PlanTier.PRO_MONTHLY
            }

            val purchaseTime = purchase.purchaseTime
            val expirationTime = when (planTier) {
                PlanTier.PRO_MONTHLY -> purchaseTime + 30L * 24 * 60 * 60 * 1000
                PlanTier.PRO_YEARLY -> purchaseTime + 365L * 24 * 60 * 60 * 1000
                PlanTier.PRO_LIFETIME -> null
                PlanTier.FREE -> null
            }

            if (!purchase.isAcknowledged) {
                val ackParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient.acknowledgePurchase(ackParams) { ackResult ->
                    if (ackResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        scope.launch {
                            monetizationStore.setProStatus(
                                isPro = true,
                                tier = planTier,
                                purchaseDateEpochMs = purchaseTime,
                                expirationDateEpochMs = expirationTime
                            )
                        }
                    }
                }
            } else {
                scope.launch {
                    monetizationStore.setProStatus(
                        isPro = true,
                        tier = planTier,
                        purchaseDateEpochMs = purchaseTime,
                        expirationDateEpochMs = expirationTime
                    )
                }
            }
        }
    }
}
