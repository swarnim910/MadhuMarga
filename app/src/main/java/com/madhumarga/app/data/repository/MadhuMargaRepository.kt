package com.madhumarga.app.data.repository

import com.madhumarga.app.data.database.dao.HarvestDao
import com.madhumarga.app.data.database.dao.HiveDao
import com.madhumarga.app.data.database.dao.InspectionDao
import com.madhumarga.app.data.database.dao.YearlyHarvest
import com.madhumarga.app.data.database.entities.Harvest
import com.madhumarga.app.data.database.entities.Hive
import com.madhumarga.app.data.database.entities.Inspection
import kotlinx.coroutines.flow.Flow

class MadhuMargaRepository(
    private val hiveDao: HiveDao,
    private val inspectionDao: InspectionDao,
    private val harvestDao: HarvestDao
) {
    // ── Hive Operations ──
    val allHives: Flow<List<Hive>> = hiveDao.getAllHives()
    val hiveCount: Flow<Int> = hiveDao.getHiveCount()

    suspend fun getHiveById(id: Long): Hive? = hiveDao.getHiveById(id)
    suspend fun insertHive(hive: Hive): Long = hiveDao.insertHive(hive)
    suspend fun updateHive(hive: Hive) = hiveDao.updateHive(hive)
    suspend fun deleteHive(hive: Hive) = hiveDao.deleteHive(hive)

    // ── Inspection Operations ──
    val allInspections: Flow<List<Inspection>> = inspectionDao.getAllInspections()
    val recentInspections: Flow<List<Inspection>> = inspectionDao.getRecentInspections()
    val alertInspections: Flow<List<Inspection>> = inspectionDao.getAlertInspections()
    val alertCount: Flow<Int> = inspectionDao.getAlertCount()

    fun getInspectionsForHive(hiveId: Long): Flow<List<Inspection>> =
        inspectionDao.getInspectionsForHive(hiveId)

    /**
     * Decision Matrix: Automatically flags intervention alert when activity is "Low".
     * This simulates GenAI-guided suggestions based on user observations.
     *
     * Rules:
     * - Activity "Low" → interventionAlert = true (immediate attention needed)
     * - Activity "Low" + Pests Seen → interventionAlert = true (critical)
     * - Activity "Low" + No Queen → interventionAlert = true (colony may be failing)
     * - Otherwise → interventionAlert = false
     */
    suspend fun insertInspection(inspection: Inspection): Long {
        val shouldAlert = inspection.activityLevel == "Low" ||
                (inspection.pestsSeen && inspection.activityLevel != "High") ||
                (!inspection.queenSeen && inspection.activityLevel == "Low")

        val processedInspection = inspection.copy(interventionAlert = shouldAlert)
        return inspectionDao.insertInspection(processedInspection)
    }

    suspend fun updateInspection(inspection: Inspection) =
        inspectionDao.updateInspection(inspection)

    suspend fun deleteInspection(inspection: Inspection) =
        inspectionDao.deleteInspection(inspection)

    // ── Harvest Operations ──
    val allHarvests: Flow<List<Harvest>> = harvestDao.getAllHarvests()
    val totalHarvest: Flow<Double?> = harvestDao.getTotalHarvest()
    val yearlyHarvests: Flow<List<YearlyHarvest>> = harvestDao.getYearlyHarvests()

    fun getHarvestsForHive(hiveId: Long): Flow<List<Harvest>> =
        harvestDao.getHarvestsForHive(hiveId)

    suspend fun insertHarvest(harvest: Harvest): Long = harvestDao.insertHarvest(harvest)
    suspend fun updateHarvest(harvest: Harvest) = harvestDao.updateHarvest(harvest)
    suspend fun deleteHarvest(harvest: Harvest) = harvestDao.deleteHarvest(harvest)
}
