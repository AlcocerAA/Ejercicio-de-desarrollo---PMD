package com.example.metalopsclone.data.repository

import com.example.metalopsclone.data.model.Ot
import com.example.metalopsclone.data.remote.NetworkModule
import com.example.metalopsclone.data.remote.dto.PostDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OtRepository {
    private val api = NetworkModule.otApi

    suspend fun list(): Result<List<Ot>> = withContext(Dispatchers.IO) {
        try {
            val res = api.list()
            if (res.isSuccessful) {
                val posts = res.body().orEmpty()
                Result.success(posts.map { it.toOt() })
            } else Result.failure(Exception("HTTP ${res.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun create(ot: Ot): Result<Ot> = withContext(Dispatchers.IO) {
        try {
            val res = api.create(ot.toPostDto())
            if (res.isSuccessful && res.body() != null) {
                Result.success(res.body()!!.toOt())
            } else Result.failure(Exception("HTTP ${res.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun update(id: Int, ot: Ot): Result<Ot> = withContext(Dispatchers.IO) {
        try {
            val res = api.update(id, ot.copy(id = id).toPostDto())
            if (res.isSuccessful && res.body() != null) {
                Result.success(res.body()!!.toOt())
            } else Result.failure(Exception("HTTP ${res.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun delete(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val res = api.delete(id)
            if (res.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("HTTP ${res.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun PostDto.toOt(): Ot = Ot(
    id = this.id,
    titulo = this.title,
    descripcion = this.body,
    estado = "abierta",
    prioridad = "media",
    fecha = "2025-10-21"
)

private fun Ot.toPostDto(): PostDto = PostDto(
    id = this.id,
    title = this.titulo.ifBlank { "Sin título" },
    body = buildString {
        append(this@toPostDto.descripcion.ifBlank { "Sin descripción" })
        append("\n\n")
        append("Estado: ${this@toPostDto.estado} | Prioridad: ${this@toPostDto.prioridad} | Fecha: ${this@toPostDto.fecha}")
    },
    userId = 1
)
