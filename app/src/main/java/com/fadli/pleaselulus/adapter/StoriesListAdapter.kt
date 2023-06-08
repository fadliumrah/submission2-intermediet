package com.fadli.pleaselulus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fadli.pleaselulus.data.DataStory
import com.fadli.pleaselulus.databinding.ItemstoryBinding
import javax.inject.Inject

class StoriesAdapter @Inject constructor() : PagingDataAdapter<DataStory, StoriesAdapter.StoriesViewHolder> (
    DIFF_CALLBACK
){


    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        return StoriesViewHolder(ItemstoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind(getItem(position) ?: DataStory())
    }

    inner class StoriesViewHolder (private val view: ItemstoryBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(stories: DataStory){
            with(view){
                idUser.text = stories.name
                desctiption.text = stories.description

                Glide.with(this.root)
                    .load(stories.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(imagestory)

                itemStory.setOnClickListener{
                    onItemClickCallback?.onItemClicked(stories.id as String)
                }
            }
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataStory>(){
            override fun areItemsTheSame(oldItem: DataStory, newItem: DataStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataStory, newItem: DataStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
interface OnItemClickCallback{
    fun onItemClicked(id: String)
}