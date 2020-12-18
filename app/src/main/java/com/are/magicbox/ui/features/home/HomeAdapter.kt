package com.are.magicbox.ui.features.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.are.magicbox.R
import com.are.magicbox.data.model.Movie
import com.are.magicbox.ui.common.RecyclerItemClickListener
import com.are.magicbox.utils.Constants

class HomeAdapter(private val context: Context, private val items: MutableList<Movie>,
                  private val listener: RecyclerItemClickListener, private val title: String
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val v = LayoutInflater.from(context).inflate(R.layout.home_list_item, parent, false)
                ItemViewHolder(v, listener, items)
            }
            TYPE_HEADER -> {
                val v = LayoutInflater.from(context).inflate(R.layout.home_list_header, parent, false)
                HeaderViewHolder(v)
            }
            else -> {
                ItemViewHolder(null, null, null)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.cover.transitionName = Constants.TRANSITION_NAME + System.currentTimeMillis()
            if(!items[position].poster_path.isNullOrEmpty()) {
                holder.cover.scaleType = ImageView.ScaleType.FIT_XY
                Glide.with(context)
                    .load(context.getString(R.string.images_base_url, items[position].poster_path))
                    .into(holder.cover)
            }else{
                holder.cover.scaleType = ImageView.ScaleType.CENTER_INSIDE
                holder.cover.setImageResource(R.drawable.ic_error)
            }
        }else if(holder is HeaderViewHolder) {
            holder.header.text = title
        }
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

    /**
     * Delete all items inside the adapter
     */
    fun cleanAdapter() {
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * Custom ViewHolder class for items
     * @param view View
     * @param listener Item Click listener
     * @param items List of movies
     */
    private class ItemViewHolder constructor(view: View?, listener: RecyclerItemClickListener?, items: MutableList<Movie>?) : RecyclerView.ViewHolder(view!!) {
        val cover: AppCompatImageView = view?.findViewById(R.id.movieCover)!!

        init {
            cover.setOnClickListener {
                listener?.onItemClick(cover, items?.get(adapterPosition)!!, adapterPosition)
            }
        }
    }

    /**
     * Custom ViewHolder class for Headers
     * @param view View
     */
    private class HeaderViewHolder constructor(view: View): RecyclerView.ViewHolder(view) {
        val header: AppCompatTextView = view.findViewById(R.id.home_list_header)
    }

    /**
     * Get the view type for Header or Item
     * @param position Item position
     */
    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

}