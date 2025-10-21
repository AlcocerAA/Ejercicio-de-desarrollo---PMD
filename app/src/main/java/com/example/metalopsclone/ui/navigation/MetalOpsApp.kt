package com.example.metalopsclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MetalOpsApp() {
    val nav = rememberNavController()
    NavGraph(nav)
}
