package com.example.metalopsclone.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.metalopsclone.ui.navigation.Destinations

data class NavigationItem(val route: String, val label: String)

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem(Destinations.OTS, "OTs")
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            val selected = currentRoute?.startsWith(item.route) == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = { Icon(Icons.Filled.List, contentDescription = item.label, tint = if (selected) Color(0xFF295FAB) else Color.Gray) },
                label = { Text(item.label) }
            )
        }
    }
}
