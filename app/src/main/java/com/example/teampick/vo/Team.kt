package com.example.teampick.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Team")
data class Team(@PrimaryKey(autoGenerate = true) var teamId: Long?,
           @ColumnInfo(name = "teamName") var teamName: String,
           @ColumnInfo(name = "level") var level: String,
           @ColumnInfo(name = "rate") var rate: Int?) {


}
