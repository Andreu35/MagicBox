package com.monstarlab.magicbox.ui.features.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.monstarlab.magicbox.R
import com.monstarlab.magicbox.data.model.Movie

class HomeAdapter(private val context: Context, private val items: MutableList<Movie>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    /**
     * Get current total items in the list
     */
    override fun getItemCount()= items.count()

    /**
     * Get the current Item ID from position
     * @param position Item position
     *
     * Return a unique ID to prevent repeat items
     */
    override fun getItemId(position: Int): Long = items[position].id.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.home_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(context.getString(R.string.images_base_url, items[position].poster_path)).into(holder.cover)
    }

    /**
     * Insert New items ( this list comes from server )
     * @param movies List of new items to insert
     */
    fun insertMoreMovies(movies: MutableList<Movie>) {
        val position = itemCount
        items.addAll(movies)
        notifyItemInserted(position)
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val cover: AppCompatImageView = view.findViewById(R.id.movieCover)
    }

}