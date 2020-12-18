package com.are.magicbox.ui.common

import android.view.View
import com.are.magicbox.data.model.Movie

interface RecyclerItemClickListener {

    fun onItemClick(view: View, movie: Movie, position: Int)
}