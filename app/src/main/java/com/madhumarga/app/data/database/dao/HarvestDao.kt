package com.madhumarga.app.data.database.dao

import androidx.room.*
import com.madhumarga.app.data.database.entities.Harvest
import kotlinx.coroutines.flow.Flow

data class YearlyHarvest(
    val year: Int,
    val totalKg: Double
)

@Dao
interface HarvestDao {

    @Query("SELECT * FROM harvests WHERE hiveId = :hiveId ORDER BY date DESC")
    fun getHarvestsForHive(hiveId: Long): Flow<List<Harvest>>

    @Query("SELECT * FROM harvests ORDER BY date DESC")
    fun getAllHarvests(): Flow<List<Harvest>>

    @Query("SELECT SUM(quantityKg) FROM harvests")
    fun getTotalHarvest(): Flow<Double?>

    @Query("""
        SELECT 
            CAST(strftime('%Y', date / 1000, 'unixepoch') AS INTEGER) as year,
            SUM(quantityKg) as totalKg
        FROM harvests 
        GROUP BY year 
        ORDER BY year DESC 
        LIMIT 2
    """)
    fun getYearlyHarvests(): Flow<List<YearlyHarvest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHarvest(harvest: Harvest): Long

    @Update
    suspend fun updateHarvest(harvest: Harvest)

    @Delete
    suspend fun deleteHarvest(harvest: Harvest)
}
