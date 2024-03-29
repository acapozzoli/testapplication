package ariel.capozzoli.testapplication.ui.main.photos

import android.view.View
import androidx.lifecycle.MutableLiveData
import ariel.capozzoli.testapplication.R
import ariel.capozzoli.testapplication.base.BaseViewModel
import ariel.capozzoli.testapplication.model.Photo
import ariel.capozzoli.testapplication.restApis.AlbumsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotoListViewModel : BaseViewModel() {
    @Inject
    lateinit var albumApi: AlbumsApi

    private lateinit var subscription: Disposable
    private var albumInt: Int? = null

    val photosListAdapter: PhotoListAdapter = PhotoListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val errorClickListener = View.OnClickListener { loadPhotos(albumInt) }

    fun loadPhotos(albumId: Int?) {
        this.albumInt = albumId
        subscription = albumApi.getPhotos(albumId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePhotosListStart() }
            .doOnTerminate { onRetrievePhotosListFinish() }
            .subscribe(
                { result -> onRetrievePhotosListSuccess(result) },
                { error -> onRetrievePhotosListError(error) }
            )
    }

    private fun onRetrievePhotosListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePhotosListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePhotosListSuccess(photosList: List<Photo>) {
        photosListAdapter.updatePhotosList(photosList)
    }

    private fun onRetrievePhotosListError(error: Throwable) {
        errorMessage.value = R.string.general_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}