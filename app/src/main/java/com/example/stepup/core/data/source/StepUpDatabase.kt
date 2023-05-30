package com.example.stepup.core.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stepup.core.data.source.util.Converters
import com.example.stepup.core.domain.model.Day

@Database(entities = [Day::class], version = 1)
@TypeConverters(Converters::class)
abstract class StepUpDatabase: RoomDatabase() {

    abstract val dayDao: DayDao

    // TODO: Change initialization to meet best practices
    companion object {
        const val DATABASE_NAME = "stepup_database"
    }
}