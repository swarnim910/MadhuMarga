package com.madhumarga.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.madhumarga.app.data.database.dao.HarvestDao
import com.madhumarga.app.data.database.dao.HiveDao
import com.madhumarga.app.data.database.dao.InspectionDao
import com.madhumarga.app.data.database.entities.Harvest
import com.madhumarga.app.data.database.entities.Hive
import com.madhumarga.app.data.database.entities.Inspection

@Database(
    entities = [Hive::class, Inspection::class, Harvest::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hiveDao(): HiveDao
    abstract fun inspectionDao(): InspectionDao
    abstract fun harvestDao(): HarvestDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "madhu_marga_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
