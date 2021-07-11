package com.example.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.project.databinding.ItemTodoBinding
class AlbumAdapt (var album: ArrayList<Albumdataformat>): RecyclerView.Adapter<AlbumAdapt.UserViewHolder>() {
        inner class UserViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

        override fun getItemCount() = album.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            return UserViewHolder(ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            holder.binding.apply {
                val album = album[position]
                tvid.text = "id: " + album.id
                tvtitle.text = "title: " + album.title
            }
        }

    }
