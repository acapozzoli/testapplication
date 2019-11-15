package ariel.capozzoli.testapplication.ui.main.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ariel.capozzoli.testapplication.R
import ariel.capozzoli.testapplication.databinding.AlbumItemBinding
import ariel.capozzoli.testapplication.model.Album
import ariel.capozzoli.testapplication.ui.main.photos.PhotosListFragment
import ariel.capozzoli.testapplication.utils.extensions.getParentActivity

class AlbumListAdapter : RecyclerView.Adapter<AlbumListAdapter.ViewHolder>() {
    private lateinit var albumList: List<Album>
    private lateinit var fullAlbumList: List<Album>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.album_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])

    }

    override fun getItemCount(): Int {
        return if (::albumList.isInitialized) albumList.size else 0
    }

    fun updateAlbumList(albumList: List<Album>) {
        this.albumList = albumList
        this.fullAlbumList = albumList
        notifyDataSetChanged()
    }

    fun filterData(filter: String?) {
        if (!filter.isNullOrEmpty()) {
            albumList = fullAlbumList.filter { it.title.contains(filter) }
        } else {
            albumList = fullAlbumList
        }
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = AlbumViewModel()

        fun bind(album: Album) {
            viewModel.bind(album)
            binding.viewModel = viewModel
            binding.root.setOnClickListener{
                binding.root.getParentActivity()!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, PhotosListFragment(album.id))
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}