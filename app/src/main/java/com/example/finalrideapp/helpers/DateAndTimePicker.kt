package com.example.rideapp.helpers

import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.finalrideapp.R
import com.example.finalrideapp.model.DataModels.DateTime
import com.example.finalrideapp.view.home.BookingDetailsActivity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class DateAndTimePicker {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH)
    val hour = calendar.get(Calendar.HOUR)
    val minute= calendar.get(Calendar.MINUTE)

    fun displayDateAndTimePicker(context: Context, functionName: String): DateTime {
        Log.d("Function Name:", functionName)
        val datePickerDialog = DatePickerDialog(context, R.style.MyDatePickerDialogTheme, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            if (functionName == "buttonCheckSlot") {
                displayClock(context, functionName)
                Timber.d(DateTime.timeString)
            }
            else {
                //dont show timePicker
            }
            //val chosenSlotDate = Date(year, month, dayOfMonth)
            calendar.set(year,month, dayOfMonth)
            val dateFormat1 = SimpleDateFormat("dd/MM/yyyy")
            val dateFormat2 = SimpleDateFormat("dd MMM, yyyy")
            //DateTime.dateString = year.toString() + "/" + (month + 1).toString() + "/" + dayOfMonth.toString()
            DateTime.dateString = dateFormat1.format(calendar.time)
            DateTime.dateString2 = dateFormat2.format(calendar.time)
            Timber.d(DateTime.dateString)
        }, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000;
        datePickerDialog.show()
        return DateTime
    }

    fun displayClock(context: Context, functionName: String){
        val timePickerDialog = TimePickerDialog(context, R.style.MyDatePickerDialogTheme, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val c = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE,minute)
            if (calendar.timeInMillis >= c.timeInMillis ) {
                val dateFormat = SimpleDateFormat("h:mm a")
                DateTime.timeString = dateFormat.format(calendar.time)
                Timber.d(DateTime.timeString)
                if (functionName == "buttonCheckSlot") {
                    val intentBookingDetails = Intent(context, BookingDetailsActivity::class.java)
                    context.startActivity(intentBookingDetails)
                }
            }
            else {
                Toast.makeText(context, "Invalid Time", Toast.LENGTH_LONG).show()
            }

        }, hour, minute, false)
        timePickerDialog.show()

    }

}
  