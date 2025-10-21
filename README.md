# MetalOpsCrudTemplate (Compose + Retrofit CRUD)

Proyecto base similar a tu estructura, con CRUD vía API (GET/POST/PUT/DELETE) usando Retrofit sobre `Movie`.
Por defecto usa `https://jsonplaceholder.typicode.com/` (`/posts`) para que funcione sin backend propio.

## Cómo abrir
1. Abre Android Studio → **Open** → selecciona la carpeta `MetalOpsCrudTemplate`.
2. Espera el sync de Gradle y ejecuta en un emulador o dispositivo.

## Rutas principales
- Lista: `MoviesScreen` (rutas de navegación `"movies"`).
- Formulario: `MovieFormScreen` (crear/editar).

## Cambiar a tu API real
- Edita `data/remote/NetworkModule.kt` → `BASE_URL` con tu dominio (ej. `https://tu-servidor/api/`).
- Edita `data/remote/MovieApi.kt` → cambia endpoints y campos según tu backend.
- Edita `data/model/Movie.kt` con los atributos reales.

## Estructura
- `data/model` → modelos
- `data/remote` → Retrofit + OkHttp
- `data/repository` → capa repositorio
- `ui/movies` → ViewModel + pantallas CRUD
- `ui/navigation` → NavHost + BottomBar

## Verbos HTTP
- **Listar**: GET `/posts`
- **Crear**: POST `/posts`
- **Actualizar**: PUT `/posts/{id}`
- **Eliminar**: DELETE `/posts/{id}`
