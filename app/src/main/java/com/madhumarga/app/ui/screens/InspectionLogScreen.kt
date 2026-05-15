package com.madhumarga.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.madhumarga.app.data.database.entities.Hive
import com.madhumarga.app.data.database.entities.Inspection
import com.madhumarga.app.ui.theme.*
import com.madhumarga.app.viewmodel.MadhuMargaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InspectionLogScreen(viewModel: MadhuMargaViewModel) {
    val hives by viewModel.allHives.collectAsState()
    val inspections by viewModel.allInspections.collectAsState()
    var showAddForm by remember { mutableStateOf(false) }
    var selectedHive by remember { mutableStateOf<Hive?>(null) }
    var hiveDropdownExpanded by remember { mutableStateOf(false) }
    var queenSeen by remember { mutableStateOf(false) }
    var pestsSeen by remember { mutableStateOf(false) }
    var activityLevel by remember { mutableStateOf("Normal") }
    var honeyFlow by remember { mutableStateOf("Normal") }
    var notes by remember { mutableStateOf("") }

    fun resetForm() {
        showAddForm = false; selectedHive = null; queenSeen = false
        pestsSeen = false; activityLevel = "Normal"; honeyFlow = "Normal"; notes = ""
    }

    Scaffold(
        floatingActionButton = {
            if (!showAddForm && hives.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = { showAddForm = true },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add") },
                    text = { Text("New Inspection") },
                    containerColor = CombBrown, contentColor = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(Brush.verticalGradient(listOf(CombBrown, CombBrownLight.copy(alpha = 0.3f), Color.Transparent)))
                        .padding(24.dp)
                ) {
                    Column {
                        Text("📋 Inspection Log", style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Record hive health observations", style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f))
                    }
                }
            }

            if (showAddForm) {
                item { InspectionForm(hives, selectedHive, hiveDropdownExpanded, queenSeen, pestsSeen,
                    activityLevel, honeyFlow, notes,
                    onHiveDropdownChange = { hiveDropdownExpanded = it },
                    onHiveSelect = { selectedHive = it; hiveDropdownExpanded = false },
                    onQueenChange = { queenSeen = it }, onPestsChange = { pestsSeen = it },
                    onActivityChange = { activityLevel = it }, onFlowChange = { honeyFlow = it },
                    onNotesChange = { notes = it },
                    onCancel = { resetForm() },
                    onSave = {
                        selectedHive?.let {
                            viewModel.addInspection(it.id, queenSeen, pestsSeen, activityLevel, honeyFlow, notes)
                            resetForm()
                        }
                    }
                )}
            }

            if (inspections.isEmpty() && !showAddForm) {
                item { EmptyInspectionState(hives.isEmpty()) }
            }

            if (!showAddForm && inspections.isNotEmpty()) {
                item { Text("Inspection History", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp, 8.dp)) }
                items(inspections, key = { it.id }) { inspection ->
                    var hiveName by remember { mutableStateOf("...") }
                    LaunchedEffect(inspection.hiveId) { hiveName = viewModel.getHiveName(inspection.hiveId) }
                    InspectionCard(hiveName, inspection, { viewModel.deleteInspection(inspection) },
                        Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InspectionForm(
    hives: List<Hive>, selectedHive: Hive?, expanded: Boolean,
    queenSeen: Boolean, pestsSeen: Boolean, activityLevel: String,
    honeyFlow: String, notes: String,
    onHiveDropdownChange: (Boolean) -> Unit, onHiveSelect: (Hive) -> Unit,
    onQueenChange: (Boolean) -> Unit, onPestsChange: (Boolean) -> Unit,
    onActivityChange: (String) -> Unit, onFlowChange: (String) -> Unit,
    onNotesChange: (String) -> Unit, onCancel: () -> Unit, onSave: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = HoneyCream.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text("New Inspection", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = onHiveDropdownChange) {
                OutlinedTextField(value = selectedHive?.name ?: "Select a hive", onValueChange = {},
                    readOnly = true, label = { Text("Hive") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CombBrown))
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onHiveDropdownChange(false) }) {
                    hives.forEach { hive -> DropdownMenuItem(text = { Text("${hive.name} — ${hive.location}") },
                        onClick = { onHiveSelect(hive) }) }
                }
            }

            Text("Checklist", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FilterChip(selected = queenSeen, onClick = { onQueenChange(!queenSeen) },
                    label = { Text("Queen Seen") },
                    leadingIcon = { if (queenSeen) Icon(Icons.Filled.Check, null, Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = SuccessGreen.copy(0.2f),
                        selectedLabelColor = SuccessGreen), modifier = Modifier.weight(1f))
                FilterChip(selected = pestsSeen, onClick = { onPestsChange(!pestsSeen) },
                    label = { Text("Pests Seen") },
                    leadingIcon = { if (pestsSeen) Icon(Icons.Filled.Check, null, Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AlertRed.copy(0.2f),
                        selectedLabelColor = AlertRed), modifier = Modifier.weight(1f))
            }

            Text("Activity Level", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Low", "Normal", "High").forEach { level ->
                    FilterChip(selected = activityLevel == level, onClick = { onActivityChange(level) },
                        label = { Text(level) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = when(level) { "Low" -> AlertRed; "Normal" -> HoneyGold; else -> SuccessGreen }.copy(0.2f),
                            selectedLabelColor = when(level) { "Low" -> AlertRed; "Normal" -> HoneyAmber; else -> SuccessGreen }),
                        modifier = Modifier.weight(1f))
                }
            }

            AnimatedVisibility(visible = activityLevel == "Low", enter = fadeIn() + expandVertically()) {
                Card(colors = CardDefaults.cardColors(containerColor = AlertRedLight.copy(0.4f)), shape = RoundedCornerShape(12.dp)) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Warning, null, tint = AlertRed, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("⚠️ Low activity will trigger an Intervention Alert!", style = MaterialTheme.typography.bodySmall,
                            color = AlertRed, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Text("Honey Flow", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Low", "Normal", "High").forEach { level ->
                    FilterChip(selected = honeyFlow == level, onClick = { onFlowChange(level) },
                        label = { Text(level) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = HoneyGold.copy(0.2f),
                            selectedLabelColor = HoneyAmber), modifier = Modifier.weight(1f))
                }
            }

            OutlinedTextField(value = notes, onValueChange = onNotesChange, label = { Text("Notes (optional)") },
                maxLines = 3, modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CombBrown))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("Cancel") }
                Button(onClick = onSave, enabled = selectedHive != null,
                    colors = ButtonDefaults.buttonColors(containerColor = CombBrown),
                    modifier = Modifier.weight(1f)) { Text("Save") }
            }
        }
    }
}

@Composable
private fun EmptyInspectionState(noHives: Boolean) {
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5f)),
        shape = RoundedCornerShape(20.dp)) {
        Column(Modifier.fillMaxWidth().padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Outlined.Assignment, null, Modifier.size(64.dp), tint = CombBrownLight.copy(0.5f))
            Spacer(Modifier.height(16.dp))
            Text("No inspections recorded", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(if (noHives) "Register a hive first" else "Tap + to log your first inspection",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.7f))
        }
    }
}

@Composable
private fun InspectionCard(hiveName: String, inspection: Inspection, onDelete: () -> Unit, modifier: Modifier) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }
    val isAlert = inspection.interventionAlert
    Card(modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isAlert) AlertRedLight.copy(0.2f)
            else MaterialTheme.colorScheme.surfaceVariant.copy(0.5f)),
        shape = RoundedCornerShape(16.dp),
        border = if (isAlert) androidx.compose.foundation.BorderStroke(1.dp, AlertRed.copy(0.3f)) else null) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(40.dp).clip(CircleShape).background(
                    if (isAlert) AlertRed.copy(0.15f) else HoneyGold.copy(0.15f)), contentAlignment = Alignment.Center) {
                    Icon(if (isAlert) Icons.Filled.Warning else Icons.Filled.Checklist, null,
                        tint = if (isAlert) AlertRed else CombBrown, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(hiveName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(dateFormat.format(Date(inspection.date)), style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f))
                }
                if (isAlert) { SuggestionChip(onClick = {}, label = { Text("ALERT", style = MaterialTheme.typography.labelSmall) },
                    colors = SuggestionChipDefaults.suggestionChipColors(containerColor = AlertRed.copy(0.15f), labelColor = AlertRed)) }
                IconButton(onClick = onDelete) { Icon(Icons.Outlined.Delete, "Delete",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f), modifier = Modifier.size(20.dp)) }
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DetailChip("Queen: ${if (inspection.queenSeen) "✓" else "✗"}", if (inspection.queenSeen) SuccessGreen else AlertRed)
                DetailChip("Pests: ${if (inspection.pestsSeen) "Yes" else "No"}", if (inspection.pestsSeen) AlertRed else SuccessGreen)
                DetailChip("Activity: ${inspection.activityLevel}", when(inspection.activityLevel) {
                    "Low" -> AlertRed; "Normal" -> HoneyAmber; else -> SuccessGreen })
            }
            if (inspection.notes.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(inspection.notes, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.8f))
            }
        }
    }
}

@Composable
private fun DetailChip(label: String, color: Color) {
    Surface(color = color.copy(0.1f), shape = RoundedCornerShape(8.dp)) {
        Text(label, Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Medium)
    }
}
