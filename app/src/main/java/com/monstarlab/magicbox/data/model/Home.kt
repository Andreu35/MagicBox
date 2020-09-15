package com.monstarlab.magicbox.data.model

data class Home(

    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: ArrayList<Movie>

) {
    override fun toString(): String {
        return "Home(page=$page, " +
                "total_results=$total_results, " +
                "total_pages=$total_pages, " +
                "results=$results)"
    }
}