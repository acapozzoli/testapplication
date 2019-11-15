package ariel.capozzoli.testapplication.dagger.component

import ariel.capozzoli.testapplication.dagger.module.NetworkModule
import ariel.capozzoli.testapplication.ui.main.albums.AlbumListViewModel
import ariel.capozzoli.testapplication.ui.main.albums.AlbumViewModel
import ariel.capozzoli.testapplication.ui.main.photos.PhotoListViewModel
import ariel.capozzoli.testapplication.ui.main.photos.PhotoViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject(albumListViewModel: AlbumListViewModel)

    fun inject(albumViewModel: AlbumViewModel)

    fun inject(photoViewModel: PhotoViewModel)

    fun inject(photoListViewModel: PhotoListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}