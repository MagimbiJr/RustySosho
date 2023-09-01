package dev.rustybite.rustysosho.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.rustybite.rustysosho.R
import dev.rustybite.rustysosho.presentation.authentication.AuthViewModel
import dev.rustybite.rustysosho.presentation.authentication.EnterNumberScreen
import dev.rustybite.rustysosho.presentation.authentication.SearchCountryCodeScreen
import dev.rustybite.rustysosho.presentation.authentication.SelectCountryCodeScreen
import dev.rustybite.rustysosho.presentation.authentication.VerifyOtpScreen
import dev.rustybite.rustysosho.presentation.ui.components.RSBottomNavBar
import dev.rustybite.rustysosho.util.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RustySoshoNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    scrollState: ScrollState = rememberScrollState(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val home = BottomNavItem.Home(stringResource(id = R.string.home_screen_name))
    val charts = BottomNavItem.Charts(stringResource(id = R.string.charts_screen_name))
    val discover = BottomNavItem.Discover(stringResource(id = R.string.discover))
    val profile = BottomNavItem.Profile(stringResource(id = R.string.profile_screen_name))
    val items = listOf(
        home,
        charts,
        discover,
        profile
    )
    val curBackStackEntry = navHostController.currentBackStackEntryAsState().value
    val currentRoute = curBackStackEntry?.destination?.route
    //val startActivity = if ()
    Scaffold(
        bottomBar = {
            RSBottomNavBar(
                items = items,
                onNavItemClicked = { route ->
                    navHostController.navigate(route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                currentRoute = currentRoute
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navHostController,
            startDestination = "verify_number_screen",
            modifier = modifier
                .padding(paddingValues)
        ) {
            composable(home.route) {

            }
            composable(charts.route) {

            }
            composable(discover.route) {

            }
            composable(profile.route) {

            }
            composable("verify_number_screen") {
                EnterNumberScreen(
                    onNavigate = { event ->
                        navHostController.navigate(event.route) {
                            popUpTo(event.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    scrollState = scrollState,
                    viewModel = authViewModel
                )
            }
            composable("code_selection_screen") {
                SelectCountryCodeScreen(
                    onNavigate = { event ->
                        navHostController.navigate(event.route) {
                            popUpTo(event.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onPopBackClicked = { navHostController.popBackStack() },
                    viewModel = authViewModel
                )
            }
            composable("search_code_screen") {
                SearchCountryCodeScreen(
                    onNavigate = { event ->
                        navHostController.navigate(event.route) {
                            popUpTo(navHostController.graph.findStartDestination().id)
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onPopBackClicked = {
                        navHostController.popBackStack()
                    },
                    viewModel = authViewModel
                )
            }
            composable("verify_otp_screen") {
                VerifyOtpScreen(
                    onNavigate = { event ->
                        navHostController.navigate(event.route) {
                            navHostController.navigate(event.route) {
                                popUpTo(event.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    viewModel = authViewModel,
                    scrollState = scrollState,
                    route = home.route
                )
            } 
            composable("registration_screen") {
                Column {
                    Text(text = "Register user")
                }
            }
        }

    }
}