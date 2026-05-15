package com.madhumarga.app.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "inspections",
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
data class Inspection(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val hiveId: Long,
    val date: Long = System.currentTimeMillis(),
    val queenSeen: Boolean = false,
    val pestsSeen: Boolean = false,
    val activityLevel: String = "Normal",  // Low, Normal, High
    val honeyFlow: String = "Normal",       // Low, Normal, High
    val notes: String = "",
    val interventionAlert: Boolean = false   // Auto-set by Decision Matrix
)
