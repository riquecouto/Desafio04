package com.example.desafio04

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GamesAdapter(
    private val listGames: ArrayList<Games>,
    private val click: onClickListener
) : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_card, parent, false)
        return GamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {

        holder.gameName.text = listGames[position].title
        holder.gameYear.text = listGames[position].createDate.toString()

        Glide.with(holder.itemView).asBitmap()
            .load(listGames[position].img)
            .into(holder.gameImg)

        holder.itemView.setOnClickListener {
            click.gameClick(listGames[position])
        }
    }

    override fun getItemCount(): Int {
        return listGames.size
    }

    inner class GamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameImg: ImageView = itemView.findViewById(R.id.imgGame)
        val gameName: TextView = itemView.findViewById(R.id.txtRecyclerGameName)
        val gameYear: TextView = itemView.findViewById(R.id.txtRecyclerGameYear)
    }
}