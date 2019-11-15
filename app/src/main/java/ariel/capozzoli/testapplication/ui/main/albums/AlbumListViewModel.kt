package ariel.capozzoli.testapplication.ui.main.albums

import android.view.View
import androidx.lifecycle.MutableLiveData
import ariel.capozzoli.testapplication.R
import ariel.capozzoli.testapplication.base.BaseViewModel
import ariel.capozzoli.testapplication.model.Album
import ariel.capozzoli.testapplication.restApis.AlbumsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumListViewModel : BaseViewModel() {
    @Inject
    lateinit var albumApi: AlbumsApi

    private lateinit var subscription: Disposable

    val albumListAdapter: AlbumListAdapter = AlbumListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val errorClickListener = View.OnClickListener { loadAlbums() }

    init {
        loadAlbums()
    }

    private fun loadAlbums() {
        subscription = albumApi.getAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveAlbumListStart() }
            .doOnTerminate { onRetrieveAlbumListFinish() }
            .subscribe(
                { result -> onRetrieveAlbumListSuccess(result) },
                { error -> onRetrieveAlbumListError(error) }
            )
    }

    private fun onRetrieveAlbumListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveAlbumListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveAlbumListSuccess(albumsList: List<Album>) {
        albumListAdapter.updateAlbumList(albumsList)
    }

    private fun onRetrieveAlbumListError(error: Throwable) {
        errorMessage.value = R.string.general_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun onFilterTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        albumListAdapter.filterData(s.toString())
    }
}