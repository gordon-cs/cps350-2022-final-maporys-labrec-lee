package com.example.maporys.data

import androidx.lifecycle.LiveData

class EntryRepository(private val entryDao: EntryDao) {
//    val readAllData: LiveData<List<Entry>> = entryDao.readAllData()
    val readAllData: List<Entry> = entryDao.getEntries()

    fun addEntry(entry: Entry) {
        entryDao.addEntry(entry)
    }

    fun getEntriesAtLocation(latQuery : String, lngQuery : String) : List<Entry> {
        return entryDao.getEntriesAtLocation(latQuery, lngQuery)
    }
}