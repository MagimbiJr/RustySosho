package dev.rustybite.rustysosho.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustysosho.domain.use_cases.AuthenticateUseCase
import dev.rustybite.rustysosho.domain.use_cases.VerifyNumberUseCase
import dev.rustybite.rustysosho.util.AppEvents
import dev.rustybite.rustysosho.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
    private val verifyNumberUseCase: VerifyNumberUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()
    private val _appEvents = Channel<AppEvents>()
    val appEvents = _appEvents.receiveAsFlow()

    fun authenticate(phoneNumber: String) {
        viewModelScope.launch {
            val data = JsonObject()
            data.addProperty("phone", phoneNumber)
            authenticateUseCase(data).collectLatest { response ->
                when(response) {
                    is Resource.Success -> {
                        if (response.data != null) {
                            if (response.data.messageId.isNotBlank()) {
                                _appEvents.send(AppEvents.Navigating("verify_number_screen"))
                            }
                        }
                    }
                    is Resource.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = response.message ?: ""
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            loading = true
                        )
                    }
                }
            }
        }
    }

    fun verifyNumber(otp: String, phoneNumber: String) {
        viewModelScope.launch {
            val data = JsonObject()
            data.addProperty("type", "sms")
            data.addProperty("phone", phoneNumber)
            data.addProperty("token", otp)

            verifyNumberUseCase(data).collectLatest { response ->
                when(response) {
                    is Resource.Success -> {

                    }
                    is Resource.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = response.message ?: ""
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            loading = true
                        )
                    }
                }
            }
        }
    }
}