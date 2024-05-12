package com.example.sprint3lab.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sprint3lab.R
import com.example.sprint3lab.data.model.GenresModel

class DetailsAdapter(
    private var listGenres: List<GenresModel>?
) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {
    inner class DetailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvGenre: TextView = v.findViewById(R.id.tvGenre)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsAdapter.DetailsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_genres, parent, false)
        return DetailsViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listGenres?.size ?: 0
    }

    override fun onBindViewHolder(holder: DetailsAdapter.DetailsViewHolder, position: Int) {
        val genre = listGenres?.get(position)?.name
        holder.tvGenre.text = genre
    }

}