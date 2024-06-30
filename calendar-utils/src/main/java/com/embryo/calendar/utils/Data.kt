package com.embryo.calendar.utils

import java.util.TimeZone

data class Data(
    val title: String,
    val description: String?,
    val timeZone: TimeZone,
    val alarm: Boolean = false,
    val startTime: Long,
    val endTime: Long,
)

data class Calendar(
    val id: Long,
    val displayName: String,
)

data class Event(
    val eventId: Long,
    val calendarId: Long,
    val title: String,
)
