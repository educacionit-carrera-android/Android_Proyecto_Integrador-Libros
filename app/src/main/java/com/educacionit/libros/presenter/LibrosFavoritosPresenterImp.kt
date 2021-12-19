package com.educacionit.libros.presenter

import com.educacionit.libros.io.ILibrosRepository
import com.educacionit.libros.view.LibrosFavoritosView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LibrosFavoritosPresenterImp(
    private var librosFavoritosView: LibrosFavoritosView?,
    private val librosRepository: ILibrosRepository,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : LibrosFavoritosPresenter, CoroutineScope {

    private var job: Job = Job()

    // Al implementar CoroutineScope se sobreeescribe esta variable que permitirá que las ejecuciones
    // corran sobre este Scope.
    override val coroutineContext: CoroutineContext
        get() = uiContext + job

    override fun doGetLibrosFavoritos() {
        librosFavoritosView?.mostrarProgressBar()
        launch(Dispatchers.IO) {
            librosRepository.getLibrosFavoritos(
                {
                    launch(Dispatchers.Main) {
                        librosFavoritosView?.actualizarLibros(it)
                        librosFavoritosView?.ocultarProgressBar()
                    }
                },
                {
                    launch(Dispatchers.Main) {
                        librosFavoritosView?.mostrarMensaje(it)
                        librosFavoritosView?.ocultarProgressBar()
                    }
                },
            )
        }
    }

    override fun cleanPresenter() {
        job.cancel()
        librosFavoritosView = null
    }
}