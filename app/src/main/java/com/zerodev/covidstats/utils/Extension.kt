package com.zerodev.covidstats.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatDate(): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val dateObj = LocalDateTime.parse(this, inputFormat)
    return dateObj.format(outputFormat)
}