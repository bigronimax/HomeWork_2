package com.example.homework_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class GifsAdapter(val context: Context, val gifs: List<DataObject>) : RecyclerView.Adapter<GifsAdapter.GifsHolder>() {


    class GifsHolder(item : View): RecyclerView.ViewHolder(item) {

        val imageView = item.findViewById<ImageView>(R.id.img)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsHolder {
        return GifsHolder(LayoutInflater.from(context).inflate(R.layout.elem_item, parent, false))
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    override fun onBindViewHolder(holder: GifsHolder, position: Int) {
        val data = gifs[position]

        Glide.with(context).load(data.images.ogImage.url)
            .into(holder.imageView)
    }
}
