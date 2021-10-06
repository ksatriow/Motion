package com.satrio.motion.util

import java.util.*

fun isNight(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (currentHour <= 5 || currentHour >= 18)
}

fun toHours(minutes: Int) : String {
    var min = minutes
    var time : String = ""
    if (min > 0) {
        time = "${min/60} hrs"
        min = min%60
    }
    if (min > 0) {
        time = "$time $min mins"
    }
    return time
}