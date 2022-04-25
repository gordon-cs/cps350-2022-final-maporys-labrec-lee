package com.example.maporys.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "entry_table")
data class Entry (
    // @PrimaryKey(autoGenerate = true)
    val date: String,
    val text: String,
    val latLng: LatLng
)