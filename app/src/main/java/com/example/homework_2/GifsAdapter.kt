package com.example.homework_2

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.homework_2.databinding.ElemItemBinding


class GifsAdapter(val context: Context, val gifs: List<DataObject>, val listener: GifsAdapter.IListener) : RecyclerView.Adapter<GifsAdapter.GifsHolder>() {
    interface IListener : GifsHolder.IListener

    class GifsHolder(item : View, val listener: IListener): RecyclerView.ViewHolder(item) {
        interface IListener {
            fun open(item: DataObject)
        }

        val imageView = item.findViewById<ImageView>(R.id.img)
        val load = item.findViewById<ProgressBar>(R.id.pgItem)


        val binding = ElemItemBinding.bind(item)
        fun bind(elem: DataObject) = with(binding) {

            cv.setOnClickListener() {
                listener.open(elem)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsHolder {
        return GifsHolder(LayoutInflater.from(context).inflate(R.layout.elem_item, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    override fun onBindViewHolder(holder: GifsHolder, position: Int) {
        val data = gifs[position]
        holder.bind(gifs[position])

        Glide.with(context)
            .load(data.images.ogImage.url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    holder.load.visibility = View.GONE
                    return false
                }
            })
            .into(holder.imageView)

    }
}
