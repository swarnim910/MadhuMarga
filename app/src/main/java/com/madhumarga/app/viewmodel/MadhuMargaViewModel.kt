package com.madhumarga.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.madhumarga.app.data.database.AppDatabase
import com.madhumarga.app.data.database.dao.YearlyHarvest
import com.madhumarga.app.data.database.entities.Harvest
import com.madhumarga.app.data.database.entities.Hive
import com.madhumarga.app.data.database.entities.Inspection
import com.madhumarga.app.data.repository.MadhuMargaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MadhuMargaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MadhuMargaRepository

    // ── UI State Flows ──
    val allHives: StateFlow<List<Hive>>
    val hiveCount: StateFlow<Int>
    val allInspections: StateFlow<List<Inspection>>
    val recentInspections: StateFlow<List<Inspection>>
    val alertInspections: StateFlow<List<Inspection>>
    val alertCount: StateFlow<Int>
    val allHarvests: StateFlow<List<Harvest>>
    val totalHarvest: StateFlow<Double>
    val yearlyHarvests: StateFlow<List<YearlyHarvest>>

    // ── Snackbar / Message channel ──
    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage: SharedFlow<String> = _snackbarMessage.asSharedFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MadhuMargaRepository(
            database.hiveDao(),
            database.inspectionDao(),
            database.harvestDao()
        )

        allHives = repository.allHives
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        hiveCount = repository.hiveCount
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        allInspections = repository.allInspections
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        recentInspections = repository.recentInspections
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        alertInspections = repository.alertInspections
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        alertCount = repository.alertCount
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

        allHarvests = repository.allHarvests
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        totalHarvest = repository.totalHarvest
            .map { it ?: 0.0 }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

        yearlyHarvests = repository.yearlyHarvests
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    // ── Hive Actions ──
    fun addHive(name: String, location: String, notes: String = "") {
        viewModelScope.launch {
            repository.insertHive(Hive(name = name, location = location, notes = notes))
            _snackbarMessage.emit("🐝 Hive '$name' registered successfully!")
        }
    }

    fun deleteHive(hive: Hive) {
        viewModelScope.launch {
            repository.deleteHive(hive)
            _snackbarMessage.emit("Hive '${hive.name}' removed")
        }
    }

    // ── Inspection Actions ──
    fun addInspection(
        hiveId: Long,
        queenSeen: Boolean,
        pestsSeen: Boolean,
        activityLevel: String,
        honeyFlow: String,
        notes: String = ""
    ) {
        viewModelScope.launch {
            val inspection = Inspection(
                hiveId = hiveId,
                queenSeen = queenSeen,
                pestsSeen = pestsSeen,
                activityLevel = activityLevel,
                honeyFlow = honeyFlow,
                notes = notes
            )
            repository.insertInspection(inspection)

            // Decision Matrix feedback
            if (activityLevel == "Low") {
                _snackbarMessage.emit("⚠️ INTERVENTION ALERT: Low activity detected! Check this hive immediately.")
            } else if (pestsSeen) {
                _snackbarMessage.emit("⚠️ Pests detected — consider treatment options.")
            } else {
                _snackbarMessage.emit("✅ Inspection logged successfully!")
            }
        }
    }

    fun deleteInspection(inspection: Inspection) {
        viewModelScope.launch {
            repository.deleteInspection(inspection)
            _snackbarMessage.emit("Inspection deleted")
        }
    }

    // ── Harvest Actions ──
    fun addHarvest(hiveId: Long, quantityKg: Double, quality: String, notes: String = "") {
        viewModelScope.launch {
            repository.insertHarvest(
                Harvest(
                    hiveId = hiveId,
                    quantityKg = quantityKg,
                    quality = quality,
                    notes = notes
                )
            )
            _snackbarMessage.emit("🍯 ${quantityKg}kg of honey logged!")
        }
    }

    fun deleteHarvest(harvest: Harvest) {
        viewModelScope.launch {
            repository.deleteHarvest(harvest)
            _snackbarMessage.emit("Harvest entry removed")
        }
    }

    // ── Year-over-Year Computation ──
    fun computeYoYGrowth(yearlyData: List<YearlyHarvest>): Pair<Double, String> {
        if (yearlyData.size < 2) return Pair(0.0, "Not enough data")
        val currentYear = yearlyData[0]
        val previousYear = yearlyData[1]
        if (previousYear.totalKg == 0.0) return Pair(100.0, "First year comparison")

        val growth = ((currentYear.totalKg - previousYear.totalKg) / previousYear.totalKg) * 100
        val trend = when {
            growth > 10 -> "📈 Strong growth"
            growth > 0 -> "📈 Positive trend"
            growth > -10 -> "📉 Slight decline"
            else -> "📉 Significant decline"
        }
        return Pair(growth, trend)
    }

    // ── Utility ──
    suspend fun getHiveName(hiveId: Long): String {
        return repository.getHiveById(hiveId)?.name ?: "Unknown Hive"
    }
}
