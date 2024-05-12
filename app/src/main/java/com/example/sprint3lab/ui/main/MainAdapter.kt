package com.example.sprint3lab.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sprint3lab.R
import com.example.sprint3lab.data.Constant
import com.example.sprint3lab.data.model.MoviesModel

class MainAdapter(
    private val context: Context,
    private var listMovies: List<MoviesModel>,
    var clickAction: (String) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvTitleMovie)
        val ivPoster: ImageView = v.findViewById(R.id.ivPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_movies, parent, false)
        return MainViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        val movie = listMovies[position]
        Glide
            .with(context)
            .load("${Constant.URL_IMAGE}${movie.poster}")
            .apply(RequestOptions().override(Constant.IMG_WIDTH, Constant.IMG_HEIGHT))
            .into(holder.ivPoster)
        holder.tvTitle.text = movie.title
        holder.itemView.setOnClickListener {
            clickAction(movie.id)
        }
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    fun updateData(newList: List<MoviesModel>) {
        listMovies = newList
        notifyDataSetChanged()
    }
}