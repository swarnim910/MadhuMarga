package com.madhumarga.app.data.database.dao

import androidx.room.*
import com.madhumarga.app.data.database.entities.Inspection
import kotlinx.coroutines.flow.Flow

@Dao
interface InspectionDao {

    @Query("SELECT * FROM inspections WHERE hiveId = :hiveId ORDER BY date DESC")
    fun getInspectionsForHive(hiveId: Long): Flow<List<Inspection>>

    @Query("SELECT * FROM inspections ORDER BY date DESC")
    fun getAllInspections(): Flow<List<Inspection>>

    @Query("SELECT * FROM inspections ORDER BY date DESC LIMIT 5")
    fun getRecentInspections(): Flow<List<Inspection>>

    @Query("SELECT * FROM inspections WHERE interventionAlert = 1 ORDER BY date DESC")
    fun getAlertInspections(): Flow<List<Inspection>>

    @Query("SELECT COUNT(*) FROM inspections WHERE interventionAlert = 1")
    fun getAlertCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(inspection: Inspection): Long

    @Update
    suspend fun updateInspection(inspection: Inspection)

    @Delete
    suspend fun deleteInspection(inspection: Inspection)
}
