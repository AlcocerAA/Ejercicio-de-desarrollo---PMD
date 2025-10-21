package com.example.metalopsclone.ui.ots

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.metalopsclone.ui.navigation.Destinations

@Composable
fun sharedOtViewModel(navController: NavController): OtViewModel {
    val parentEntry = remember(navController) {
        navController.getBackStackEntry(Destinations.OTS)
    }
    return viewModel(parentEntry)
}
