package com.example.finalrideapp.helpers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Year
import java.util.*

data class TimeAndDate(val date: String, val month: String, val year: String, val time: String)

fun String.replaceAndCapitalize() = this.replace("\"", "").split(" ").joinToString(" ") { it.capitalize() }.trimEnd()


fun String.getTimeAndDate(): TimeAndDate  {

    var list = this.split("T", ignoreCase= false, limit = 2)
    var timeSplit = list[1].split(".", ignoreCase = false, limit = 2)
    var date: Date = SimpleDateFormat("yyyy-MM-dd").parse(list[0])
    var time: Date = SimpleDateFormat("HH:mm:ss").parse(timeSplit[0])
    var dateList = SimpleDateFormat("dd,MMM,yyyy").format(date).split(",", ignoreCase = false, limit = 3)
    var time1: String = SimpleDateFormat("h:mm a").format(time)
    Log.d("Message",dateList.toString() + " " + time1 )
    return TimeAndDate(dateList[0], dateList[1], dateList[2], time1)
}

fun String.checkIfOlder(): Boolean {
    val isOlder: Boolean
    isOlder = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this).before(Date())
    return isOlder
}




