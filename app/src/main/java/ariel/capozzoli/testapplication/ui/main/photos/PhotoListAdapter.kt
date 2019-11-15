package ariel.capozzoli.testapplication.ui.main.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ariel.capozzoli.testapplication.R
import ariel.capozzoli.testapplication.databinding.PhotoItemBinding
import ariel.capozzoli.testapplication.model.Photo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.photo_item.view.*

class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {
    private lateinit var photoList: List<Photo>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PhotoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.photo_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return if (::photoList.isInitialized) photoList.size else 0
    }

    fun updatePhotosList(photoList: List<Photo>) {
        this.photoList = photoList
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PhotoViewModel()

        fun bind(photo: Photo) {
            viewModel.bind(photo)
            binding.viewModel = viewModel
            if(photo.thumbnailUrl.isNullOrEmpty()){
                binding.root.photo.setImageDrawable(null)
            }else{
                Glide.with(binding.root.photo)
                    .load(photo.thumbnailUrl)
                    .into(binding.root.photo)
            }
        }
    }
}