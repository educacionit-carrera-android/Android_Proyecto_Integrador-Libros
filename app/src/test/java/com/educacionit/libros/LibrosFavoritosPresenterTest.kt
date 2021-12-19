package com.educacionit.libros

import com.educacionit.libros.io.ILibrosRepository
import com.educacionit.libros.presenter.LibrosFavoritosPresenter
import com.educacionit.libros.presenter.LibrosFavoritosPresenterImp
import com.educacionit.libros.view.LibrosFavoritosView
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LibrosFavoritosPresenterTest {

    @Mock
    private lateinit var view: LibrosFavoritosView

    @Mock
    private lateinit var repository: ILibrosRepository

    @Mock
    private lateinit var presenter: LibrosFavoritosPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        presenter = LibrosFavoritosPresenterImp(
            view, repository, Dispatchers.Unconfined
        )
    }

    @Test
    fun shouldMostrarProgressBarWhenGetLibrosFavoritos() {
        presenter.doGetLibrosFavoritos()

        Mockito.verify(view).mostrarProgressBar()
    }
}