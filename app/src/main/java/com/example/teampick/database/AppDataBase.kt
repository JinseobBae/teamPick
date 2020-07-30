package com.example.teampick.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teampick.vo.Team

@Database(entities = [Team::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun teamDao(): TeamDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDataBase::class.java, "teampick.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}