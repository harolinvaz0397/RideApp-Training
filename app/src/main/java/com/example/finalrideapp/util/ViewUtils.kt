package com.example.finalrideapp.util

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.coroutines.coroutineContext

fun Context.toast(message:String){
    Toast.makeText(this, message , Toast.LENGTH_LONG).show()
}

fun isPhoneValid(phone: String): Boolean {
    if (phone.length == 10) {
        return true
    }
    return false
}

fun isEmailValid(email: String): Boolean {
    /*
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

     */
    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return true
    }
    return false
}

fun isPasswordValid(password: String): Boolean {
    val PASSWORD_PATTERN: Pattern = Pattern.compile(
        //((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})
        "[a-zA-Z0-9].{8,40}"
    )
    if (PASSWORD_PATTERN.matcher(password).matches()) {
        return true
    }
    return false
}

fun isPasswordMatching(newPassword: String, confirmPassword: String): Boolean {
    if (newPassword.equals(confirmPassword)) {
        return true
    }
    return false
}

fun isTokenExpire(start: String, stop: String): Boolean {

    val MIN_TOKEN_AGE = 6

    val day1: List<String> = start.split(" ")
    val day2: List<String> = stop.split(" ")
    val date1: List<String> = day1[0].split("-")
    val date2: List<String> = day2[0].split("-")

    val startTime: List<String> = day1[1].split(":")
    val currentTime: List<String> = day2[1].split(":")

    val d1: Int = date1[0].toInt()
    val d2: Int = date2[0].toInt()
    val mm1: Int = date1[1].toInt()
    val mm2: Int = date2[1].toInt()
    val y1: Int = date1[2].toInt()
    val y2: Int = date2[2].toInt()

    var h1:Int = currentTime[0].toInt()
    var m1:Int = currentTime[1].toInt()
    var s1:Int = currentTime[2].toInt()
    var h2:Int = startTime[0].toInt()
    var m2:Int = startTime[1].toInt()
    var s2:Int = startTime[2].toInt()

    if (s2 > s1) {
        --m1
        s1 += 60
    }

    val s3 = s1 - s2
    if (m2 > m1) {
        --h1
        m1 += 60
    }

    val m3 = m1 - m2
    val h3 = h1 - h2

    if (h3 >= MIN_TOKEN_AGE || d2 > d1 || mm2 > mm1 || y2 > y1) {
        return true
    }

    return false
}