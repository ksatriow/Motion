package com.satrio.motion.data.entity

import org.json.JSONObject

data class Actor (
    val id: Int,
    val popularity: Number,
    val name: String,
    val birthday: String,
    val also_known_as: ArrayList<String>,
    val biography: String,
    val place_of_birth: String,
    val profile_path: String,
    val known_for_department: String,
    val movie_credits: JSONObject
)