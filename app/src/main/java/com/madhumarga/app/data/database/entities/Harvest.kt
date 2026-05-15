package com.madhumarga.app.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "harvests",
    foreignKeys = [
        ForeignKey(
            entity = Hive::class,
            parentColumns = ["id"],
            childColumns = ["hiveId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["hiveId"])]
)
data class Harvest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val hiveId: Long,
    val date: Long = System.currentTimeMillis(),
    val quantityKg: Double,
    val quality: String = "Good",  // Poor, Fair, Good, Excellent
    val notes: String = ""
)
