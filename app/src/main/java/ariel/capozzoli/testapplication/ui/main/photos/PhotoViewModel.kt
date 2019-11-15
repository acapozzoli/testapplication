package ariel.capozzoli.testapplication.ui.main.photos

import androidx.lifecycle.MutableLiveData
import ariel.capozzoli.testapplication.base.BaseViewModel
import ariel.capozzoli.testapplication.model.Photo

class PhotoViewModel : BaseViewModel() {
    private val photoTitle = MutableLiveData<String>()
    private val photoId = MutableLiveData<String>()
    private var imageUrl: String? = null

    fun bind(photo: Photo) {
        photoTitle.value = photo.title
        photoId.value = photo.id.toString()
        imageUrl = photo.thumbnailUrl
    }

    fun getPhotoTitle(): MutableLiveData<String> {
        return photoTitle
    }

    fun getPhotoId(): MutableLiveData<String> {
        return photoId
    }

    fun getImageUrl(): String? {
        return imageUrl
    }
}