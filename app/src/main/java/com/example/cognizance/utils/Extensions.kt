package com.example.cognizance.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun String.toDateString(): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
    val localDate = LocalDate.parse(this)
    return dateFormat.format(localDate)
}
