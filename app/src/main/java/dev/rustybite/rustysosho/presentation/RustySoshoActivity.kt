package dev.rustybite.rustysosho.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rustybite.rustysosho.BuildConfig
import dev.rustybite.rustysosho.presentation.ui.theme.RustySoshoTheme

@AndroidEntryPoint
class RustySoshoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val scrollState = rememberScrollState()
            RustySoshoTheme {
                RustySoshoNavHost(
                    navHostController = navHostController,
                    scrollState = scrollState
                )
            }
        }
    }
}