package com.monstarlab.magicbox.ui.common

import android.view.View
import com.monstarlab.magicbox.data.model.Movie

interface RecyclerItemClickListener {

    fun onItemClick(view: View, movie: Movie, position: Int)
}