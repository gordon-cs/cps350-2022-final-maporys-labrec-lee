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
}