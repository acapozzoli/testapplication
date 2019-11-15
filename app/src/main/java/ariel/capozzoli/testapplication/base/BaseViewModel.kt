package ariel.capozzoli.testapplication.base

import androidx.lifecycle.ViewModel
import ariel.capozzoli.testapplication.dagger.component.DaggerViewModelInjector
import ariel.capozzoli.testapplication.dagger.component.ViewModelInjector
import ariel.capozzoli.testapplication.dagger.module.NetworkModule
import ariel.capozzoli.testapplication.ui.main.albums.AlbumListViewModel
import ariel.capozzoli.testapplication.ui.main.albums.AlbumViewModel
import ariel.capozzoli.testapplication.ui.main.photos.PhotoListViewModel
import ariel.capozzoli.testapplication.ui.main.photos.PhotoViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is AlbumListViewModel -> injector.inject(this)
            is AlbumViewModel -> injector.inject(this)
            is PhotoViewModel -> injector.inject(this)
            is PhotoListViewModel -> injector.inject(this)
        }
    }
}