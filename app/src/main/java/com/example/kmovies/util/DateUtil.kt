package com.example.kmovies.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatConstants {
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val YYYY = "yyyy"
}

fun formatDateToSelectedPattern(
    selectedPatern: String,
    date: Any?
):String{
    return date?.let {
        val sdf = SimpleDateFormat(selectedPatern, Locale("es", "MX"))
        sdf.format(date)
    } ?: run { "" }
}

fun formatDateToNewFormat(dateFormat: String, newFormat: String, date: String?) : String {
    return date?.let {
        try {
            val format = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            val dateCreatedFromFormat = format.parse(date)
            formatDateToSelectedPattern(
                newFormat,
                dateCreatedFromFormat
            )
        } catch (ex: ParseException){
            ""
        }
    } ?: run {
        ""
    }
}