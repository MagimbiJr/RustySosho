package dev.rustybite.rustysosho.presentation.authentication

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustysosho.util.AppEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EnterNumberScreen(
    onNavigate: (AppEvents.Navigating) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    //systemUiController: SystemUiController,
    scrollState: ScrollState
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

    EnterNumberContent(
        modifier = modifier,
        onVerifyNumber = {
            focusManager.clearFocus()
            val phoneNumber = if (uiState.phoneNumber.startsWith("0")) {
                uiState.phoneNumber.drop(1)
            } else {
                uiState.phoneNumber
            }
            viewModel.authenticate("${uiState.countryCode}$phoneNumber")
            viewModel.onPhoneChange("")
        },
        onPhoneNumberChange = viewModel::onPhoneChange,
        onNavigateToCodeSelection = { viewModel.onNavigateToCodeSelection() },
        uiState = uiState,
        scrollState = scrollState
    )
}