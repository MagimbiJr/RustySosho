package dev.rustybite.rustysosho.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustysosho.R
import dev.rustybite.rustysosho.data.local.PreferenceManager
import dev.rustybite.rustysosho.domain.model.User
import dev.rustybite.rustysosho.domain.use_cases.RefreshTokenUseCase
import dev.rustybite.rustysosho.util.AppEvents
import dev.rustybite.rustysosho.util.BottomNavItem
import dev.rustybite.rustysosho.util.Resource
import dev.rustybite.rustysosho.util.ResourceProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val prefManager: PreferenceManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _startDestination = MutableStateFlow("verify_number_screen")
    val startDestination = _startDestination.asStateFlow()
    private val _loading = mutableStateOf(true)
    val loading: State<Boolean> = _loading
    private val home =
        BottomNavItem.Home(resourceProvider.getStringResource(R.string.home_screen_name))
    private val _appEvents = Channel<AppEvents>()
    val appEvents = _appEvents.receiveAsFlow()
    private val user = User()

    init {
        viewModelScope.launch {
            val refreshToken = mutableStateOf("")
            prefManager.getRefreshToken.collectLatest { token ->
                refreshToken.value = token
            }
            prefManager.isTokenExpired.collectLatest { isExpired ->
                if (isExpired) {
                    if (refreshToken.value.isNotBlank()) {
                        val data = JsonObject()
                        data.addProperty("refresh_token", refreshToken.value)
                        refreshTokenUseCase(data).collectLatest { response ->
                            when (response) {
                                is Resource.Success -> {
                                    if (response.data != null) {
                                        prefManager.storeRefreshToken(response.data.refreshToken)
                                        _startDestination.value = home.route
                                        _loading.value = false
                                    }
                                }

                                is Resource.Failure -> {}
                                is Resource.Loading -> {
                                    _loading.value = true
                                }
                            }
                        }
                    }
                } else {
                    _startDestination.value = "verify_number_screen"
                    _loading.value = false
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            prefManager.isTokenExpired.collectLatest { isExpired ->
                if (isExpired) {
                    _startDestination.value = "verify_number_screen"
                } else {
                    if (user.name.isBlank()) {
                        _startDestination.value = "registration_screen"
                        _appEvents.send(AppEvents.Navigating("registration_screen"))
                    } else {
                        _startDestination.value = home.route
                    }
                }
            }
        }
    }
}