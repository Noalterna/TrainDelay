package com.example.traindelay.adapter

import junit.framework.TestCase
import org.junit.Test
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME

class RouteAdapterTest : TestCase() {

    @Test
    fun testFormatDatetoHour() {
        assertEquals(
            "11:18",
            formatDatetoHour("Mon, 15 Feb 2021 11:18:00 GMT")
        )
    }
    private fun formatDatetoHour(dateFromJson: String): String? {
        val date = LocalTime.parse(dateFromJson, RFC_1123_DATE_TIME)
        print(date.toString())
        return date.toString()
    }
}