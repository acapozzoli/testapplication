package ariel.capozzoli.testapplication.ui.main.photos

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ariel.capozzoli.testapplication.R
import ariel.capozzoli.testapplication.databinding.FragmentPhotosListBinding
import com.google.android.material.snackbar.Snackbar


class PhotosListFragment(albumId: Int) : Fragment() {

    private lateinit var binding: FragmentPhotosListBinding
    private lateinit var photoListViewModel: PhotoListViewModel

    private var errorSnackbar: Snackbar? = null

    init {
        val bundle = Bundle()
        bundle.putInt("albumId", albumId)
        arguments = bundle
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos_list, container, false)
        binding.albumsList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.albumsList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 30
            }
        })

        photoListViewModel = ViewModelProviders.of(this).get(PhotoListViewModel::class.java)
        binding.viewModel = photoListViewModel

        photoListViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        if (arguments != null) {
            photoListViewModel.loadPhotos(arguments!!.getInt("albumId", -1))
        }

        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, photoListViewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}