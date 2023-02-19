package com.zerodev.covidstats.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

@RequiresApi(Build.VERSION_CODES.M)
fun Context.getConnectivity(): NetworkCapabilities? {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.getNetworkCapabilities(cm.activeNetwork)
}