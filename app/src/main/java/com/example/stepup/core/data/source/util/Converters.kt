package com.example.stepup.core.data.source.util

import androidx.room.TypeConverter
import java.time.LocalDate

@Suppress("unused")
class Converters {
    @TypeConverter
    fun localDateToTimestamp(date: LocalDate): Long = date.toEpochDay()

    @TypeConverter
    fun timestampToLocalDate(timestamp: Long): LocalDate = LocalDate.ofEpochDay(timestamp)
}