package com.karigojobs.feature.settings.backup

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GoogleDriveAuth {
    private const val SCOPE_DRIVE_APPDATA = "https://www.googleapis.com/auth/drive.appdata"

    fun getSignInIntent(context: Context): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(SCOPE_DRIVE_APPDATA))
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        return googleSignInClient.signInIntent
    }

    fun getAccountFromIntent(intent: Intent?): GoogleSignInAccount? {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        return try {
            task.getResult(com.google.android.gms.common.api.ApiException::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun signOut(context: Context, onComplete: () -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val client = GoogleSignIn.getClient(context, gso)
        client.signOut().addOnCompleteListener {
            onComplete()
        }
    }

    fun getSignedInAccount(context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    suspend fun getAccessToken(context: Context, account: GoogleSignInAccount): String? {
        return withContext(Dispatchers.IO) {
            try {
                val androidAccount = account.account ?: return@withContext null
                val scope = "oauth2:$SCOPE_DRIVE_APPDATA"
                
                // Get the token, which might be expired but still in the local cache
                val token = GoogleAuthUtil.getToken(context, androidAccount, scope)
                
                // Clear it from the cache to force a fresh fetch
                GoogleAuthUtil.clearToken(context, token)
                
                // Fetch a guaranteed fresh token
                GoogleAuthUtil.getToken(context, androidAccount, scope)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
