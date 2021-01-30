package com.example.anotherclicker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoneyAmount::class], version = 1,  exportSchema = false)
abstract class MoneyDatabase : RoomDatabase() {
    abstract val moneyDatabaseDao: MoneyDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MoneyDatabase? = null

        fun getInstance(context: Context): MoneyDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            MoneyDatabase::class.java,
                            "money_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}