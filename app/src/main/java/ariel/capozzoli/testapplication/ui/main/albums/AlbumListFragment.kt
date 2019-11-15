package ariel.capozzoli.testapplication.ui.main.albums

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
import ariel.capozzoli.testapplication.databinding.FragmentAlbumsListBinding
import com.google.android.material.snackbar.Snackbar


class AlbumListFragment : Fragment() {

    private lateinit var binding: FragmentAlbumsListBinding
    private lateinit var albumListViewModel: AlbumListViewModel

    private var errorSnackbar: Snackbar? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_albums_list, container, false)
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

        albumListViewModel = ViewModelProviders.of(this).get(AlbumListViewModel::class.java)
        binding.viewModel = albumListViewModel

        albumListViewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, albumListViewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}