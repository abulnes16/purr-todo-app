package com.abulnes16.purrtodo.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import java.text.SimpleDateFormat
import java.util.*

object DataTransformationUtil {

    fun getMonthFromString(date: String): Int {
        val parsedDate = SimpleDateFormat("MMM", Locale.getDefault()).parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = parsedDate!!
        return calendar.get(Calendar.MONTH)
    }

    fun hideKeyboard(activity: FragmentActivity?){
        activity?.currentFocus?.let { view ->
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}