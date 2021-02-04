package com.example.anotherclicker.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clicker_table")
data class Clicker (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name="name")
    var name : String = "",

    @ColumnInfo(name="cost")
    var cost: Long = 0L,

    @ColumnInfo(name="amount")
    var amount: Int = 0,

    @ColumnInfo(name="proc_time")
    var procTime: Double = 0.0,

    @ColumnInfo(name="proc_value")
    var procValue: Long = 0L
)