package com.example.cognizance.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun String.toDateString(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
    val localDate = LocalDate.parse(this)
    return dateFormat.format(localDate)
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
