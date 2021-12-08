package com.udacity.asteroidradar

import android.annotation.SuppressLint
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.udacity.asteroidradar", appContext.packageName)
    }

    @Test
    fun CorrectgetToday(){
        val abc = getSeventhDay()
        assertEquals("2021-12-14", getSeventhDay())

    }


    fun getToday(): String {
        val calendar = Calendar.getInstance()
        return formatDate(calendar.time)
    }

    fun getSeventhDay(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        return formatDate(calendar.time)
    }

    @SuppressLint("NewApi", "WeekBasedYear")
    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }
}
