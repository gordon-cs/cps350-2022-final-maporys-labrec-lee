package com.example.maporys.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KParameter

@Database(entities = [Entry::class], version = 1)
abstract class EntryDatabase: RoomDatabase() {

    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var INSTANCE: EntryDatabase? = null
//        var INSTANCE: EntryDatabase? = null


        fun getDatabase(context: Context): EntryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            // Create new instance if there is none
            synchronized(EntryDatabase::class) {
                val instanceBuilder = Room.databaseBuilder(
                    context.applicationContext,
                    EntryDatabase::class.java,
                    "entry_database"
                )
                instanceBuilder.allowMainThreadQueries()
                val instance = instanceBuilder.build()
                INSTANCE = instance
                return instance
            }
        }
    }
}