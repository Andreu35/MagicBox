package com.are.magicbox.data.model

import java.io.Serializable

data class HomeResponse(

    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: MutableList<Movie>

): Serializable