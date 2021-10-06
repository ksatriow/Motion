package com.satrio.motion.data.entity

data class MovieResponse  (
    val page : Int,
    val results : List<Movie>,
    val total_results: Int,
    val total_pages: Int
)