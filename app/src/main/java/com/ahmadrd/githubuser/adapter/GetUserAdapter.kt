package com.ahmadrd.githubuser.adapter

import android.content.Intent
import android.view.*
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ahmadrd.githubuser.data.response.ItemsItem
import com.ahmadrd.githubuser.databinding.ItemUserBinding
import com.ahmadrd.githubuser.ui.DetailUserActivity

class GetUserAdapter : ListAdapter<ItemsItem, GetUserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class UserViewHolder(private var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailUserActivity::class.java)
                intentDetail.putExtra(DetailUserActivity.EXTRA_USER, user.login)
                binding.root.context.startActivity(intentDetail)
            }
            binding.tvName.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .apply(RequestOptions().override(800, 600))
                .into(binding.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}