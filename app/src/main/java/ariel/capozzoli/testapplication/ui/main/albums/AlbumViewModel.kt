package ariel.capozzoli.testapplication.ui.main.albums

import androidx.lifecycle.MutableLiveData
import ariel.capozzoli.testapplication.base.BaseViewModel
import ariel.capozzoli.testapplication.model.Album

class AlbumViewModel:BaseViewModel() {
    private val albumTitle = MutableLiveData<String>()
    private val albumId = MutableLiveData<String>()

    fun bind(album: Album){
        albumTitle.value = album.title
        albumId.value = album.id.toString()
    }

    fun getAlbumTitle():MutableLiveData<String>{
        return albumTitle
    }

    fun getAlbumId():MutableLiveData<String>{
        return albumId
    }
}