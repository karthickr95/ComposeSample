package com.embryo.calendar.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log

@Suppress("RedundantSuspendModifier")
suspend fun ContentResolver.getAllCalendarIds(): List<Calendar> {
    val projection =
        arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

    val selection = CalendarContract.Calendars.IS_PRIMARY + " = ?"
    val selectionArgs = arrayOf("1")

    val cursor: Cursor = query(
        CalendarContract.Calendars.CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        null,
    ) ?: throw NullPointerException("cursor is null")

    val idColumnIndex = cursor.getColumnIndexOrThrow(CalendarContract.Calendars._ID)
    val nameColumnIndex = cursor
        .getColumnIndexOrThrow(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

    return buildList {
        cursor.use {
            it.moveToFirst()
            while (it.moveToNext()) {
                val calendar = Calendar(
                    id = it.getLong(idColumnIndex),
                    displayName = it.getString(nameColumnIndex)
                )
                add(calendar)
            }
        }
    }
}

/**
 * This method will return a default calendar_id.
 *
 * @return default calendar_id or -1 if not found. Caller have to check that value is not -1
 */
@Suppress("RedundantSuspendModifier")
suspend fun ContentResolver.getDefaultCalendarId(): Long {
    val projection =
        arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

    val selection = CalendarContract.Calendars.IS_PRIMARY + " = ?"
    val selectionArgs = arrayOf("1")

    val cursor: Cursor = query(
        CalendarContract.Calendars.CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        null,
    ) ?: throw NullPointerException("cursor is null")

    return cursor.use {
        if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Calendars._ID))
        } else {
            -1L
        }
    }
}

@Suppress("RedundantSuspendModifier")
suspend fun ContentResolver.insertCalendarEvent(
    calendarId: Long,
    event: Data,
): Uri? {
    val contentValues = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, event.startTime)
        put(CalendarContract.Events.DTEND, event.endTime)
        put(CalendarContract.Events.TITLE, event.title)
        put(CalendarContract.Events.DESCRIPTION, event.description)
        put(CalendarContract.Events.CALENDAR_ID, calendarId)
        put(CalendarContract.Events.EVENT_TIMEZONE, event.timeZone.id)
    }
    return insert(CalendarContract.Events.CONTENT_URI, contentValues)
}

@Suppress("RedundantSuspendModifier")
suspend fun ContentResolver.isEventExists(eventId: Long): Boolean {
    val projection = arrayOf(
        CalendarContract.Events._ID,
        CalendarContract.Events.TITLE,
        CalendarContract.Events.DESCRIPTION,
    )
    val selection = CalendarContract.Events._ID + " = ?"
    val selectionArgs = arrayOf("$eventId")

    return query(
        CalendarContract.Events.CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        null,
    ).use {
        it != null && it.count > 0
    }
}

@Suppress("RedundantSuspendModifier")
suspend fun ContentResolver.queryEventLike(calendarId: Long, eventLike: String) {
    val projection = arrayOf(
        CalendarContract.Events._ID,
        CalendarContract.Events.TITLE,
        CalendarContract.Events.DESCRIPTION,
    )
    val selection =
        CalendarContract.Events.CALENDAR_ID + " = ? AND " + CalendarContract.Events.TITLE + " like ? AND "
    val selectionArgs = arrayOf("$calendarId", eventLike)

    val rows = delete(
        CalendarContract.Events.CONTENT_URI,
        //projection,
        selection,
        selectionArgs,
    )/*.use {
        Log.d("calendar", "Calendar event listing - start")
        if (it != null) {
            val array = LongArray(it.count) { 0 }
            val idColumnIndex = it.getColumnIndexOrThrow(CalendarContract.Events._ID)
            val nameColumnIndex = it
                .getColumnIndexOrThrow(CalendarContract.Events.TITLE)
            it.moveToFirst()
            do {
                array[it.position] = it.getLong(idColumnIndex)
                *//* Log.d(
                   "calendar",
                   "Event(Id ${it.getLong(idColumnIndex)}, Title ${it.getString(nameColumnIndex)})"
               )*//*
            } while (it.moveToNext())
            return array
        } else {
            Log.d("calendar", "event cursor null")
        }
        return longArrayOf()
        Log.d("calendar", "Calendar event listing - end")
    }*/
    notifyChange(CalendarContract.Events.CONTENT_URI, null)
    Log.d("calendar", "Calendar event deleted rows - $rows")
}

@Suppress("RedundantSuspendModifier")
suspend fun ContentResolver.deleteEvent(eventId: Long): Boolean {
    val deleteUri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId)
    val rows: Int = delete(deleteUri, null, null)
    return rows > 0
}

@Suppress("RedundantSuspendModifier")
suspend fun ContentResolver.updateEvent(eventId: Long, event: Data): Boolean {
    val contentValues = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, event.startTime)
        put(CalendarContract.Events.DTEND, event.endTime)
        put(CalendarContract.Events.TITLE, event.title)
        put(CalendarContract.Events.DESCRIPTION, event.description)
        put(CalendarContract.Events.EVENT_TIMEZONE, event.timeZone.id)
    }
    val eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId)
    val rowsUpdated: Int = update(eventUri, contentValues, null, null)
    return rowsUpdated > 1
}
