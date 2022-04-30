package com.example.maporys.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EntryDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEntry(entry: Entry)

    @Update
    fun updateGender(entry: Entry)

    @Delete
    fun deleteGender(entry: Entry)

    @Query("SELECT * FROM entry_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Entry>>
}