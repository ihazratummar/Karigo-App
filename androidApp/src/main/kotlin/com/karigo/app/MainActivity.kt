package com.karigo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.Modifier
import com.karigo.app.navigation.AppNavigation
import com.karigo.ui.theme.KarigoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val windowsSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            KarigoTheme(
                windowSizeClass = windowsSizeClass,
            ) {
                AppNavigation(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}