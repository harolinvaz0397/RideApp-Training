package com.example.rideapp.helpers

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.EditText
import com.example.finalrideapp.R
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

class DateTimePicker {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH)
    val hour = calendar.get(Calendar.HOUR)
    val minute= calendar.get(Calendar.MINUTE)

    fun displayDateAndTimePicker(context: Context, editText: EditText) {
        var dateString: String
       // Log.d("Function Name:", functionName)
        val datePickerDialog = DatePickerDialog(context, R.style.MyDatePickerDialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
           /* if (functionName.equals("buttonCheckSlot")) {
                    displayClock(context, functionName)
                    Log.d(DateTime.timeString)
            }
            else {
                    //dont show timePicker
                 }*/
            //val chosenSlotDate = Date(year, month, dayOfMonth)
            calendar.set(year,month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            //DateTime.dateString = year.toString() + "/" + (month + 1).toString() + "/" + dayOfMonth.toString()
            dateString = dateFormat.format(calendar.getTime())
            Log.d("Datetime",dateString)
            editText.setText(dateString)
            }, year, month, day)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show()

        }

    fun displayClock(context: Context, editText: EditText){
        var timeString : String
        val timePickerDialog = TimePickerDialog(context, R.style.MyDatePickerDialogTheme,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE,minute)
            val dateFormat = SimpleDateFormat("h:mm a")
            timeString = dateFormat.format(calendar.time)
                editText.setText(timeString)
            //Timber.d(DateTime.timeString)
           /* if (functionName.equals("buttonCheckSlot")) {
                val intentBookingDetails = Intent(context, BookingDetailsActivity::class.java)
                context.startActivity(intentBookingDetails)
            }*/
            }, hour, minute, false)
        timePickerDialog.show()

    }

    fun findDaysBetweenDates(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val upcomingDate: Date = dateFormat.parse(dateTime)
        val currentDateAndTime: Date = dateFormat.parse(dateFormat.format(Date()).toString())
        val duration: Long = upcomingDate.time - currentDateAndTime.time
        val difference = TimeUnit.MILLISECONDS.toDays(duration)
        return difference.toString()
        //2020-08-29T06:15:00.000Z
    }



    }
  