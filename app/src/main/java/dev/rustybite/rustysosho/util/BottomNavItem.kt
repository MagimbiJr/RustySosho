package dev.rustybite.rustysosho.util

import dev.rustybite.rustysosho.R

sealed class BottomNavItem(val route: String, val name: String, val icon: Int) {
    class Home(name: String) : BottomNavItem(route = "feeds_screen", name = name, icon = R.drawable.home_icon)
    class Charts(name: String) : BottomNavItem(route = "charts_screen", name = name, icon = R.drawable.messages_icon)
    class Discover(name: String) : BottomNavItem(route = "discover_screen", name = name, icon = R.drawable.search_icon)
    class Profile(name: String) : BottomNavItem(route = "profile_screen", name = name, icon = R.drawable.person_icon)
}
