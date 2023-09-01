package dev.rustybite.rustysosho.presentation.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustysosho.domain.model.User
import dev.rustybite.rustysosho.domain.use_cases.AuthenticateUseCase
import dev.rustybite.rustysosho.domain.use_cases.VerifyNumberUseCase
import dev.rustybite.rustysosho.util.AppEvents
import dev.rustybite.rustysosho.util.Resource
import dev.rustybite.rustysosho.util.RustyConstants
import dev.rustybite.rustysosho.util.codes
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
    private var user = User()


    fun authenticate(phoneNumber: String) {
        viewModelScope.launch {
            val data = JsonObject()
            data.addProperty("phone", phoneNumber)
            authenticateUseCase(data).collectLatest { response ->
                when (response) {
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

    fun verifyNumber(otp: String, phoneNumber: String, homeRoute: String) {
        viewModelScope.launch {
            val data = JsonObject()
            data.addProperty("type", "sms")
            data.addProperty("phone", phoneNumber)
            data.addProperty("token", otp)

            verifyNumberUseCase(data).collectLatest { response ->
                when (response) {
                    is Resource.Success -> {
                        if (response.data != null) {
                            user = User(
                                userId = response.data.user.id,
                                phoneNumber = response.data.user.phone,
                                createdAt = response.data.user.createdAt,
                                lastSignInAt = response.data.user.lastSignInAt,
                                updatedAt = response.data.user.updatedAt,
                            )
                            if (user.name.isNotBlank()) {
                                _appEvents.send(AppEvents.Navigating(homeRoute))
                                _uiState.value = _uiState.value.copy(
                                    isUserStored = true
                                )
                            } else {
                                _appEvents.send(AppEvents.Navigating("registration_screen"))
                                _uiState.value = _uiState.value.copy(
                                    isUserStored = false
                                )
                                //response.data.
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

    fun onPhoneChange(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(
            phoneNumber = phoneNumber
        )
    }

    fun onOtpChange(otp: String) {
        _uiState.value = _uiState.value.copy(
            otp = otp
        )
    }

    fun onQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(
            query = query
        )
        val result = codes.filter { code ->
            code.name.lowercase().contains(query)
        }
        _uiState.value = _uiState.value.copy(searchResult = result)
    }

    fun onCountryCodeChange(code: String) {
        _uiState.value = _uiState.value.copy(
            countryCode = code
        )
    }

    fun onNavigateToCodeSelection() {
        viewModelScope.launch {
            _appEvents.send(AppEvents.Navigating("code_selection_screen"))
        }
    }

    fun onPopBackFromSelectCode() {
        viewModelScope.launch {
            _appEvents.send(AppEvents.PopBackStack)
        }
    }

    fun onNavigateToSearchCode() {
        viewModelScope.launch {
            _appEvents.send(AppEvents.Navigating("search_code_screen"))
        }
    }

    fun onPopBackFromSearchCode() {
        viewModelScope.launch {
            _appEvents.send(AppEvents.PopBackStack)
        }
    }

    fun onNavigateToVerifyNumber() {
        viewModelScope.launch {
            _appEvents.send(AppEvents.Navigating("verify_number_screen"))
            _uiState.value = _uiState.value.copy(query = "")
        }
    }
}