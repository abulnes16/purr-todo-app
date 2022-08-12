package com.abulnes16.purrtodo.utils

import java.text.SimpleDateFormat
import java.util.*

object DataTransformationUtil {

    fun getMonthFromString(date: String): Int {
        val parsedDate = SimpleDateFormat("MMM", Locale.getDefault()).parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = parsedDate!!
        return calendar.get(Calendar.MONTH)
    }

}