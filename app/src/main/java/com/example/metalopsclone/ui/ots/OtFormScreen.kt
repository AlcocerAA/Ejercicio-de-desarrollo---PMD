package com.example.metalopsclone.ui.ots

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun OtFormScreen(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry("ots")
    }
    val vm: OtViewModel = viewModel(parentEntry)

    val args = backStackEntry?.arguments
    val id = args?.getString("id")?.toIntOrNull()
    var titulo by remember { mutableStateOf(TextFieldValue(args?.getString("titulo") ?: "")) }
    var descripcion by remember { mutableStateOf(TextFieldValue(args?.getString("descripcion") ?: "")) }
    var estado by remember { mutableStateOf(TextFieldValue(args?.getString("estado") ?: "abierta")) }
    var prioridad by remember { mutableStateOf(TextFieldValue(args?.getString("prioridad") ?: "media")) }
    var fecha by remember { mutableStateOf(TextFieldValue(args?.getString("fecha") ?: "")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = if (id == null || id == 0) "Crear OT" else "Editar OT",
            style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = titulo, onValueChange = { titulo = it },
            label = { Text("Título") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion, onValueChange = { descripcion = it },
            label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(), minLines = 3
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = estado, onValueChange = { estado = it },
            label = { Text("Estado") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = prioridad, onValueChange = { prioridad = it },
            label = { Text("Prioridad") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = fecha, onValueChange = { fecha = it },
            label = { Text("Fecha (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        Row {
            Button(onClick = {
                if (id == null || id == 0) {
                    vm.create(titulo.text, descripcion.text, estado.text, prioridad.text, fecha.text)
                } else {
                    vm.update(id, titulo.text, descripcion.text, estado.text, prioridad.text, fecha.text)
                }
                navController.popBackStack()
            }) {
                Text(if (id == null || id == 0) "Crear" else "Actualizar")
            }
            Spacer(Modifier.width(12.dp))
            OutlinedButton(onClick = { navController.popBackStack() }) { Text("Cancelar") }
        }
    }
}
