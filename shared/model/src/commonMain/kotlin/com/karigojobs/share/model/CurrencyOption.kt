package com.karigojobs.share.model

/**
 * Currency model representing supported currencies for Karigo.
 * Focused on Asia, Africa, USD, EUR, GBP, and major global markets.
 */
data class CurrencyOption(
    val code: String,
    val symbol: String,
    val name: String,
    val flagEmoji: String,
    val isPopular: Boolean = false
) {
    val displayName: String get() = "$flagEmoji  $code ($symbol) · $name"
}

object CurrencyOptions {
    val POPULAR_CURRENCIES = listOf(
        CurrencyOption("INR", "₹", "Indian Rupee", "🇮🇳", isPopular = true),
        CurrencyOption("USD", "$", "US Dollar", "🇺🇸", isPopular = true),
        CurrencyOption("AED", "AED", "UAE Dirham", "🇦🇪", isPopular = true),
        CurrencyOption("SAR", "SAR", "Saudi Riyal", "🇸🇦", isPopular = true),
        CurrencyOption("EUR", "€", "Euro", "🇪🇺", isPopular = true),
        CurrencyOption("GBP", "£", "British Pound", "🇬🇧", isPopular = true),
        CurrencyOption("BDT", "৳", "Bangladeshi Taka", "🇧🇩", isPopular = true),
        CurrencyOption("PKR", "₨", "Pakistani Rupee", "🇵🇰", isPopular = true)
    )

    val ALL_CURRENCIES = listOf(
        // Popular First
        CurrencyOption("INR", "₹", "Indian Rupee", "🇮🇳", isPopular = true),
        CurrencyOption("USD", "$", "US Dollar", "🇺🇸", isPopular = true),
        CurrencyOption("AED", "AED", "UAE Dirham", "🇦🇪", isPopular = true),
        CurrencyOption("SAR", "SAR", "Saudi Riyal", "🇸🇦", isPopular = true),
        CurrencyOption("EUR", "€", "Euro", "🇪🇺", isPopular = true),
        CurrencyOption("GBP", "£", "British Pound", "🇬🇧", isPopular = true),
        CurrencyOption("BDT", "৳", "Bangladeshi Taka", "🇧🇩", isPopular = true),
        CurrencyOption("PKR", "₨", "Pakistani Rupee", "🇵🇰", isPopular = true),

        // Asia Region
        CurrencyOption("QAR", "QAR", "Qatari Riyal", "🇶🇦"),
        CurrencyOption("OMR", "OMR", "Omani Rial", "🇴🇲"),
        CurrencyOption("KWD", "KWD", "Kuwaiti Dinar", "🇰🇼"),
        CurrencyOption("BHD", "BHD", "Bahraini Dinar", "🇧🇭"),
        CurrencyOption("NPR", "Rs", "Nepalese Rupee", "🇳🇵"),
        CurrencyOption("LKR", "Rs", "Sri Lankan Rupee", "🇱🇰"),
        CurrencyOption("SGD", "S$", "Singapore Dollar", "🇸🇬"),
        CurrencyOption("MYR", "RM", "Malaysian Ringgit", "🇲🇾"),
        CurrencyOption("IDR", "Rp", "Indonesian Rupiah", "🇮🇩"),
        CurrencyOption("PHP", "₱", "Philippine Peso", "🇵🇭"),
        CurrencyOption("THB", "฿", "Thai Baht", "🇹🇭"),
        CurrencyOption("VND", "₫", "Vietnamese Dong", "🇻🇳"),
        CurrencyOption("JPY", "¥", "Japanese Yen", "🇯🇵"),
        CurrencyOption("KRW", "₩", "South Korean Won", "🇰🇷"),

        // Africa Region
        CurrencyOption("NGN", "₦", "Nigerian Naira", "🇳🇬"),
        CurrencyOption("KES", "KSh", "Kenyan Shilling", "🇰🇪"),
        CurrencyOption("EGP", "E£", "Egyptian Pound", "🇪🇬"),
        CurrencyOption("ZAR", "R", "South African Rand", "🇿🇦"),
        CurrencyOption("GHS", "GH₵", "Ghanaian Cedi", "🇬🇭"),
        CurrencyOption("TZS", "TSh", "Tanzanian Shilling", "🇹🇿"),
        CurrencyOption("UGX", "USh", "Ugandan Shilling", "🇺🇬"),
        CurrencyOption("ETB", "Br", "Ethiopian Birr", "🇪🇹"),
        CurrencyOption("MAD", "MAD", "Moroccan Dirham", "🇲🇦"),

        // Americas & Oceania
        CurrencyOption("CAD", "C$", "Canadian Dollar", "🇨🇦"),
        CurrencyOption("AUD", "A$", "Australian Dollar", "🇦🇺")
    )

    fun findBySymbolOrCode(query: String): CurrencyOption? {
        val clean = query.trim().uppercase()
        return ALL_CURRENCIES.firstOrNull { it.code == clean || it.symbol == query.trim() }
    }
}
