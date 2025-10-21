package com.example.metalopsclone.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.metalopsclone.core.ui.components.BottomBar
import com.example.metalopsclone.ui.ots.OtFormScreen
import com.example.metalopsclone.ui.ots.OtsScreen

object Destinations {
    const val OTS = "ots"
    const val OT_FORM =
        "ot/form?id={id}&titulo={titulo}&descripcion={descripcion}&estado={estado}&prioridad={prioridad}&fecha={fecha}"
}

@Composable
fun NavGraph(navController: NavHostController) {
    Scaffold(bottomBar = { BottomBar(navController) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.OTS,
            modifier = Modifier.padding(padding)
        ) {
            composable(Destinations.OTS) {
                OtsScreen(navController)
            }

            composable(
                route = Destinations.OT_FORM,
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType; defaultValue = 0 },
                    navArgument("titulo") { type = NavType.StringType; defaultValue = "" },
                    navArgument("descripcion") { type = NavType.StringType; defaultValue = "" },
                    navArgument("estado") { type = NavType.StringType; defaultValue = "abierta" },
                    navArgument("prioridad") { type = NavType.StringType; defaultValue = "media" },
                    navArgument("fecha") { type = NavType.StringType; defaultValue = "" }
                )
            ) {
                OtFormScreen(navController)
            }
        }
    }
}
