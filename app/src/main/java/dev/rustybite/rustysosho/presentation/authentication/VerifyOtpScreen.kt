package dev.rustybite.rustysosho.presentation.authentication

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import dev.rustybite.rustysosho.util.AppEvents
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyOtpScreen(
    onNavigate: (AppEvents.Navigating) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    //systemUiController: SystemUiController,
    scrollState: ScrollState,
    route: String
) {
    //systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)

    val uiState = viewModel.uiState.collectAsState().value
    val focusManager = LocalFocusManager.current
    val appEvents = viewModel.appEvents

    LaunchedEffect(key1 = appEvents) {
        appEvents.collectLatest { event ->
            when (event) {
                is AppEvents.Navigating -> {
                    onNavigate(event)
                }

                is AppEvents.ShowSnackBar -> Unit
                is AppEvents.ShowToast -> Unit
                is AppEvents.PopBackStack -> Unit
                is AppEvents.SignInRequired -> Unit
            }
        }
    }

    Scaffold { paddingValues ->
        VerifyOtpContent(
            modifier = modifier
                .padding(paddingValues),
            onOtpChange = viewModel::onOtpChange,
            onVerifyOtp = {
                focusManager.clearFocus()
                viewModel.verifyNumber(uiState.otp, route)
            },
            uiState = uiState,
            scrollState = scrollState,
        )
    }
}