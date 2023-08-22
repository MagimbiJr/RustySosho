package dev.rustybite.rustysosho.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.rustybite.rustysosho.R
import dev.rustybite.rustysosho.presentation.ui.components.RSBottomNavBar
import dev.rustybite.rustysosho.util.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RustySoshoNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
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
            startDestination = home.route,
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
        }

    }
}