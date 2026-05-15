package com.madhumarga.app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.madhumarga.app.data.database.entities.Harvest
import com.madhumarga.app.data.database.entities.Hive
import com.madhumarga.app.ui.theme.*
import com.madhumarga.app.viewmodel.MadhuMargaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HarvestTrackerScreen(viewModel: MadhuMargaViewModel) {
    val hives by viewModel.allHives.collectAsState()
    val harvests by viewModel.allHarvests.collectAsState()
    val totalHarvest by viewModel.totalHarvest.collectAsState()
    val yearlyHarvests by viewModel.yearlyHarvests.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    val (growth, trend) = remember(yearlyHarvests) { viewModel.computeYoYGrowth(yearlyHarvests) }

    Scaffold(
        floatingActionButton = {
            if (hives.isNotEmpty()) {
                ExtendedFloatingActionButton(onClick = { showAddDialog = true },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Add") },
                    text = { Text("Log Harvest") },
                    containerColor = FloraGreen, contentColor = Color.White)
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 80.dp)) {

            item {
                Box(modifier = Modifier.fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(FloraGreen, FloraGreenLight.copy(0.3f), Color.Transparent)))
                    .padding(24.dp)) {
                    Column {
                        Text("🍯 Harvest Tracker", style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Track your honey production", style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(0.8f))
                    }
                }
            }

            // YoY Comparison Banner
            if (yearlyHarvests.size >= 2) {
                item {
                    Card(modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = if (growth >= 0) SuccessGreenLight.copy(0.3f) else AlertRedLight.copy(0.3f)),
                        shape = RoundedCornerShape(20.dp)) {
                        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(56.dp).clip(CircleShape)
                                .background(if (growth >= 0) SuccessGreen.copy(0.15f) else AlertRed.copy(0.15f)),
                                contentAlignment = Alignment.Center) {
                                Icon(if (growth >= 0) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                                    null, tint = if (growth >= 0) SuccessGreen else AlertRed, modifier = Modifier.size(28.dp))
                            }
                            Spacer(Modifier.width(16.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Year-over-Year", style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${if (growth >= 0) "+" else ""}${String.format("%.1f", growth)}%",
                                    style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                                    color = if (growth >= 0) SuccessGreen else AlertRed)
                                Text(trend, style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.7f))
                            }
                        }
                    }
                }
            }

            // Total Harvest Summary
            item {
                Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = HoneyLight.copy(0.3f)),
                    shape = RoundedCornerShape(16.dp)) {
                    Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(50.dp).clip(CircleShape).background(HoneyGold.copy(0.2f)),
                            contentAlignment = Alignment.Center) {
                            Text("🍯", style = MaterialTheme.typography.headlineMedium)
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text("Total Harvest", style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${String.format("%.1f", totalHarvest)} kg", style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold, color = HoneyAmber)
                            Text("${harvests.size} entries logged", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.7f))
                        }
                    }
                }
            }

            if (harvests.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth().padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5f)),
                        shape = RoundedCornerShape(20.dp)) {
                        Column(Modifier.fillMaxWidth().padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Outlined.WaterDrop, null, Modifier.size(64.dp), tint = HoneyGold.copy(0.5f))
                            Spacer(Modifier.height(16.dp))
                            Text("No harvests logged yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            Text(if (hives.isEmpty()) "Register a hive first" else "Tap + to log your first harvest",
                                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.7f))
                        }
                    }
                }
            } else {
                item { Text("Harvest Log", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp, 12.dp)) }
                items(harvests, key = { it.id }) { harvest ->
                    var hiveName by remember { mutableStateOf("...") }
                    LaunchedEffect(harvest.hiveId) { hiveName = viewModel.getHiveName(harvest.hiveId) }
                    HarvestCard(hiveName, harvest, { viewModel.deleteHarvest(harvest) },
                        Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                }
            }
        }
    }

    if (showAddDialog) {
        AddHarvestDialog(hives, onDismiss = { showAddDialog = false },
            onConfirm = { hiveId, qty, quality, notes ->
                viewModel.addHarvest(hiveId, qty, quality, notes); showAddDialog = false })
    }
}

@Composable
private fun HarvestCard(hiveName: String, harvest: Harvest, onDelete: () -> Unit, modifier: Modifier) {
    val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    val qualityColor = when(harvest.quality) { "Excellent" -> SuccessGreen; "Good" -> HoneyAmber
        "Fair" -> PollenOrange; else -> AlertRed }

    Card(modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5f)),
        shape = RoundedCornerShape(16.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp).clip(CircleShape).background(HoneyGold.copy(0.15f)),
                contentAlignment = Alignment.Center) {
                Text("🍯", style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(hiveName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text("${harvest.quantityKg} kg", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, color = HoneyAmber)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(color = qualityColor.copy(0.1f), shape = RoundedCornerShape(8.dp)) {
                        Text(harvest.quality, Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall, color = qualityColor, fontWeight = FontWeight.Medium)
                    }
                    Text(dateFormat.format(Date(harvest.date)), style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f))
                }
            }
            IconButton(onClick = onDelete) { Icon(Icons.Outlined.Delete, "Delete",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f), modifier = Modifier.size(20.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddHarvestDialog(hives: List<Hive>, onDismiss: () -> Unit,
    onConfirm: (Long, Double, String, String) -> Unit) {
    var selectedHive by remember { mutableStateOf<Hive?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf("") }
    var quality by remember { mutableStateOf("Good") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismiss,
        icon = { Icon(Icons.Filled.WaterDrop, null, tint = FloraGreen) },
        title = { Text("Log Harvest") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(value = selectedHive?.name ?: "Select hive", onValueChange = {},
                        readOnly = true, label = { Text("Hive") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FloraGreen))
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        hives.forEach { hive -> DropdownMenuItem(text = { Text(hive.name) },
                            onClick = { selectedHive = hive; expanded = false }) }
                    }
                }
                OutlinedTextField(value = quantity, onValueChange = { quantity = it },
                    label = { Text("Quantity (kg)") }, singleLine = true, modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FloraGreen))

                Text("Quality", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Poor", "Fair", "Good", "Excellent").forEach { q ->
                        FilterChip(selected = quality == q, onClick = { quality = q },
                            label = { Text(q, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FloraGreen.copy(0.2f), selectedLabelColor = FloraGreen),
                            modifier = Modifier.weight(1f))
                    }
                }
                OutlinedTextField(value = notes, onValueChange = { notes = it },
                    label = { Text("Notes (optional)") }, maxLines = 2, modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FloraGreen))
            }
        },
        confirmButton = {
            Button(onClick = { selectedHive?.let { onConfirm(it.id, quantity.toDoubleOrNull() ?: 0.0, quality, notes) } },
                enabled = selectedHive != null && (quantity.toDoubleOrNull() ?: 0.0) > 0,
                colors = ButtonDefaults.buttonColors(containerColor = FloraGreen)) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } })
}
