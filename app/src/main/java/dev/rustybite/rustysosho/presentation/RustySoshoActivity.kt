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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rustybite.rustysosho.BuildConfig
import dev.rustybite.rustysosho.presentation.ui.theme.RustySoshoTheme
import dev.rustybite.rustysosho.util.AppEvents
import dev.rustybite.rustysosho.util.Resource
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RustySoshoActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            installSplashScreen().setKeepOnScreenCondition {
                !viewModel.loading.value
            }
            val startDestination = viewModel.startDestination.collectAsState().value
            val navHostController = rememberNavController()
            val scrollState = rememberScrollState()
            val appEvents = viewModel.appEvents

            LaunchedEffect(key1 = appEvents) {
                appEvents.collectLatest { event ->
                    when(event) {
                        is AppEvents.Navigating -> {
                            navHostController.navigate(event.route) {
                                popUpTo(event.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        is AppEvents.ShowSnackBar -> Unit
                        is AppEvents.ShowToast -> Unit
                        is AppEvents.PopBackStack -> Unit
                        is AppEvents.SignInRequired -> Unit
                    }
                }
            }

            RustySoshoTheme {
                RustySoshoNavHost(
                    navHostController = navHostController,
                    startDestination = startDestination
                )
            }
        }
    }
}