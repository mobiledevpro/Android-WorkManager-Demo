package com.mobiledevpro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobiledevpro.database.dao.StockAlertDao
import com.mobiledevpro.database.entity.StockAlertEntity

/**
 * Room Database
 */

@Database(
    entities = [
        StockAlertEntity::class
    ],
    version = BuildConfig.DB_VERSION,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract val stockAlertDao: StockAlertDao


    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                BuildConfig.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}

