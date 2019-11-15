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

    val errorClickListener = View.OnClickListener { loadPosts() }

    init {
        loadPosts()
    }

    private fun loadPosts() {
        subscription = albumApi.getAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { error -> onRetrievePostListError(error) }
            )
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(albumsList: List<Album>) {
        albumListAdapter.updateAlbumList(albumsList)
    }

    private fun onRetrievePostListError(error: Throwable) {
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