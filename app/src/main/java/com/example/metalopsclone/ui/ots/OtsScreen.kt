package com.example.metalopsclone.ui.ots

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtsScreen(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry("ots")
    }
    val vm: OtViewModel = viewModel(parentEntry)

    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.refresh() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        "ot/form?id=0&titulo=&descripcion=&estado=abierta&prioridad=media&fecha="
                    )
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva OT")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (state.loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            state.error?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }

            LazyColumn(Modifier.fillMaxSize()) {
                items(state.items) { ot ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        onClick = {
                            navController.navigate(
                                "ot/form?id=${ot.id ?: 0}&titulo=${ot.titulo}" +
                                        "&descripcion=${ot.descripcion}&estado=${ot.estado}" +
                                        "&prioridad=${ot.prioridad}&fecha=${ot.fecha}"
                            )
                        }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(ot.titulo, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Estado: ${ot.estado}  •  Prioridad: ${ot.prioridad}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                ot.descripcion,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 2
                            )
                            Spacer(Modifier.height(6.dp))
                            if (ot.id != null) {
                                Button(
                                    onClick = { vm.delete(ot.id) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error
                                    )
                                ) { Text("Eliminar") }
                            }
                        }
                    }
                }
            }
        }
    }
}
