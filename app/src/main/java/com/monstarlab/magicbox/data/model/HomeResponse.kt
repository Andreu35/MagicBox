package com.monstarlab.magicbox.data.model

import java.io.Serializable

data class HomeResponse(

    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: MutableList<Movie>

): Serializable {
    override fun toString(): String {
        return "HomeResponse(page=$page, total_results=$total_results, total_pages=$total_pages, results=$results)"
    }
}