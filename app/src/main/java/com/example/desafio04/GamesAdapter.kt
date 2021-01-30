package com.example.desafio04

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream


class GamesAdapter(
    private val listGames: ArrayList<Games>,
    private val click: onClickListener
) : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    lateinit var storageRef : StorageReference

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


        if (listGames[position].imgRef.isNotEmpty()){
            storageRef = FirebaseStorage.getInstance().getReference(listGames[position].imgRef)

            GlideApp.with(holder.itemView).asBitmap()
                .load(storageRef)
                .into(holder.gameImg)
        }
        else {
            holder.gameImg.setImageResource(R.drawable.noimage)
        }

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