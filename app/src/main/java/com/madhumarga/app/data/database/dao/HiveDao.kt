package com.madhumarga.app.data.database.dao

import androidx.room.*
import com.madhumarga.app.data.database.entities.Hive
import kotlinx.coroutines.flow.Flow

@Dao
interface HiveDao {

    @Query("SELECT * FROM hives ORDER BY dateAdded DESC")
    fun getAllHives(): Flow<List<Hive>>

    @Query("SELECT * FROM hives WHERE id = :hiveId")
    suspend fun getHiveById(hiveId: Long): Hive?

    @Query("SELECT COUNT(*) FROM hives")
    fun getHiveCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHive(hive: Hive): Long

    @Update
    suspend fun updateHive(hive: Hive)

    @Delete
    suspend fun deleteHive(hive: Hive)
}
