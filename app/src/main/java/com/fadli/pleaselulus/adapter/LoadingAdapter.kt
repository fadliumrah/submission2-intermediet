package com.fadli.pleaselulus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fadli.pleaselulus.databinding.LoadingStateBinding

class LoadingAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingAdapter.LoadingStateViewHolder>() {

    inner class LoadingStateViewHolder(var binding: LoadingStateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = LoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.apply {
            itemView.apply {
                binding.apply {
                    if (loadState is LoadState.Error){
                        errorText.text = loadState.error.localizedMessage
                    }
                    btnReload.setOnClickListener {
                        retry.invoke()
                    }

                    btnReload.isVisible = loadState is LoadState.Error
                    errorText.isVisible = loadState is LoadState.Error
                    progressBar.isVisible = loadState is LoadState.Loading
                }
            }
        }
    }

}