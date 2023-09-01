package dev.rustybite.rustysosho.util

sealed class AppEvents {
    data class Navigating(val route: String) : AppEvents()
    data class ShowToast(val message: String) : AppEvents()
    data class ShowSnackBar(val message: String, val action: String) : AppEvents()
    data object PopBackStack : AppEvents()
    data object SignInRequired : AppEvents()
}
