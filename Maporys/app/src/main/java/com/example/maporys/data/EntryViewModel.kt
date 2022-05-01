package com.example.maporys.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Entry>>
    private val repository: EntryRepository

    init {
        val entryDao = EntryDatabase.getDatabase(application)!!.entryDao()
        repository = EntryRepository(entryDao)
        readAllData = repository.readAllData
    }

    fun addEntry(entry: Entry) {
        // Launch database job from background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEntry(entry)
        }
    }
}