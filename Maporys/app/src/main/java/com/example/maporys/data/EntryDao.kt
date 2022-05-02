package com.example.maporys.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EntryDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEntry(entry: Entry)

    @Update
    fun updateEntry(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)

    @Query("SELECT * FROM entry_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table ORDER BY id ASC")
    fun getEntries(): List<Entry>

    // Probably would be good to make this order by date
    // Would probably need to change the date type from string
    @Query("SELECT * FROM entry_table WHERE lat = :latQuery AND lng = :lngQuery ORDER BY id DESC")
    fun getEntriesAtLocation(latQuery : String, lngQuery : String): List<Entry>
}