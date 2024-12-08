package com.example.cognizance.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun String.toDateString(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
    return try {
        val localDate = LocalDate.parse(this)
        dateFormat.format(localDate)
    } catch (e: Exception) {
        "-"
    }
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
