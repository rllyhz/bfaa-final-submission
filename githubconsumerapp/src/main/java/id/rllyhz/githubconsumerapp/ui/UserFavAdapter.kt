package id.rllyhz.githubconsumerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import id.rllyhz.githubconsumerapp.R
import id.rllyhz.githubconsumerapp.data.model.UserFav
import id.rllyhz.githubconsumerapp.databinding.ItemUserFavBinding
import id.rllyhz.githubconsumerapp.util.toColorDrawable

class UserFavAdapter : ListAdapter<UserFav, UserFavAdapter.UserFavViewHolder>(UserFavComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavViewHolder {
        val binding = ItemUserFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFavViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    // viewholder
    inner class UserFavViewHolder(private val binding: ItemUserFavBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserFav) {
            binding.apply {
                val colorDrawable =
                    ContextCompat.getColor(itemView.context, R.color.redish_500).toColorDrawable()

                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions.placeholderOf(colorDrawable))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(sivItemUserAvatar)

                tvItemUserUsername.text = user.username
            }
        }

    }

    // diffutil comparator
    class UserFavComparator : DiffUtil.ItemCallback<UserFav>() {
        override fun areItemsTheSame(oldItem: UserFav, newItem: UserFav): Boolean =
            oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: UserFav, newItem: UserFav): Boolean =
            oldItem == newItem
    }
}