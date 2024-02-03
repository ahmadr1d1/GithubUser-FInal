package com.ahmadrd.githubuser.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.githubuser.data.response.DetailUserResponse
import com.ahmadrd.githubuser.databinding.ItemUserBinding
import com.ahmadrd.githubuser.ui.main.DetailUserActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavUserAdapter(private val listUser: ArrayList<DetailUserResponse>) :
    RecyclerView.Adapter<FavUserAdapter.ListViewHolder>() {

    class UserDiffCallback(
        private val oldItem: List<DetailUserResponse>,
        private val newItem: List<DetailUserResponse>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldItem.size

        override fun getNewListSize() = newItem.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItem[oldItemPosition].login == newItem[newItemPosition].login

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItem[oldItemPosition] == newItem[newItemPosition]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listUser[position]
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailUserResponse) {
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailUserActivity::class.java)
                intentDetail.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                binding.root.context.startActivity(intentDetail)
            }

            binding.tvName.text = data.login
            Glide.with(itemView.context)
                .load(data.avatarUrl)
                .apply(RequestOptions().override(800, 600))
                .into(binding.imgItemPhoto)
        }
    }

    fun setListUser(newListUser: List<DetailUserResponse>) {
        val diffCallback = UserDiffCallback(listUser, newListUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listUser.clear()
        listUser.addAll(newListUser)
        diffResult.dispatchUpdatesTo(this)
    }
}