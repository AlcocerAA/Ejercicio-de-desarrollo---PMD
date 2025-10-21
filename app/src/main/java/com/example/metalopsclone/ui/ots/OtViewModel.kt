package com.example.metalopsclone.ui.ots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metalopsclone.data.model.Ot
import com.example.metalopsclone.data.repository.OtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class OtUiState(
    val loading: Boolean = false,
    val items: List<Ot> = emptyList(),
    val error: String? = null
)

class OtViewModel(
    private val repo: OtRepository = OtRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(OtUiState())
    val state: StateFlow<OtUiState> = _state

    fun refresh() {
        _state.value = _state.value.copy(loading = true, error = null)
        viewModelScope.launch {
            val res = repo.list()
            _state.value = res.fold(
                onSuccess = { serverItems ->
                    val current = _state.value.items.associateBy { it.id }
                    val merged = serverItems.map { current[it.id] ?: it }
                    _state.value.copy(loading = false, items = merged)
                },
                onFailure = { _state.value.copy(loading = false, error = it.message) }
            )
        }
    }

    fun create(titulo: String, descripcion: String, estado: String, prioridad: String, fecha: String) {
        val tempId = (_state.value.items.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        val nuevo = Ot(id = tempId, titulo, descripcion, estado, prioridad, fecha)
        _state.value = _state.value.copy(items = _state.value.items + nuevo)

        viewModelScope.launch {
            val res = repo.create(nuevo)
            res.onSuccess { real ->
                val lista = _state.value.items.map { if (it.id == tempId) real.copy(id = real.id ?: tempId) else it }
                _state.value = _state.value.copy(items = lista)
            }.onFailure { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun update(id: Int, titulo: String, descripcion: String, estado: String, prioridad: String, fecha: String) {
        val editada = Ot(id, titulo, descripcion, estado, prioridad, fecha)
        _state.value = _state.value.copy(items = _state.value.items.map { if (it.id == id) editada else it })

        viewModelScope.launch {
            val res = repo.update(id, editada)
            res.onFailure { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun delete(id: Int) {
        val anterior = _state.value.items
        _state.value = _state.value.copy(items = anterior.filterNot { it.id == id })

        viewModelScope.launch {
            val res = repo.delete(id)
            res.onFailure { e ->
                _state.value = _state.value.copy(items = anterior, error = e.message)
            }
        }
    }
}
