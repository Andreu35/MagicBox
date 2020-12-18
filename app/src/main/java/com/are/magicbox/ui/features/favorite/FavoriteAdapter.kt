package com.are.magicbox.ui.features.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.are.magicbox.R
import com.are.magicbox.data.model.Movie
import com.are.magicbox.ui.common.RecyclerItemClickListener
import com.are.magicbox.utils.Constants

class FavoriteAdapter(private val context: Context, private val items: MutableList<Movie>,
                      private val listener: RecyclerItemClickListener
): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

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
        val v = LayoutInflater.from(context).inflate(R.layout.favorite_list_item, parent, false)
        return ViewHolder(v, listener, items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
    }

    /**
     * Delete all items inside the adapter
     */
    fun cleanAdapter() {
        items.clear()
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int, movie: Movie) {
        items.remove(movie)
        notifyItemRemoved(position)
    }

    /**
     * Custom ViewHolder class for items
     * @param view View
     * @param listener Item Click listener
     * @param items List of movies
     */
    class ViewHolder constructor(view: View?, listener: RecyclerItemClickListener?, items: MutableList<Movie>?) : RecyclerView.ViewHolder(view!!) {
        val cover: AppCompatImageView = view?.findViewById(R.id.movieCover)!!
        private val delete: AppCompatImageView = view?.findViewById(R.id.delete)!!

        init {
            cover.setOnClickListener {
                cover.tag = COVER
                listener?.onItemClick(cover, items?.get(adapterPosition)!!, adapterPosition)
            }

            delete.setOnClickListener {
                delete.tag = DELETE
                listener?.onItemClick(delete, items?.get(adapterPosition)!!, adapterPosition)
            }
        }

        companion object {
            const val COVER = "Cover"
            const val DELETE = "Delete"
        }
    }

}