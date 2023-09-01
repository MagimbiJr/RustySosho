package dev.rustybite.rustysosho.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rustybite.rustysosho.util.AppEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EnterNumberScreen(
    onNavigate: (AppEvents.Navigating) -> Unit,
    modifier: Modifier = Modifier,
    //systemUiController: SystemUiController,
    scrollState: ScrollState,
    viewModel: AuthViewModel,
) {
    //systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background)

    val uiState = viewModel.uiState.collectAsState().value
    val focusManager = LocalFocusManager.current
    val appEvents = viewModel.appEvents
    val context = LocalContext.current

    LaunchedEffect(key1 = appEvents) {
        appEvents.collectLatest { event ->
            when (event) {
                is AppEvents.Navigating -> {
                    onNavigate(event)
                }

                is AppEvents.ShowSnackBar -> Unit
                is AppEvents.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
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