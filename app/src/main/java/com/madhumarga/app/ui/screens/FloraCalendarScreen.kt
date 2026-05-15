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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.madhumarga.app.ui.theme.*
import java.util.*

data class FloraEntry(
    val name: String,
    val emoji: String,
    val bloomMonths: List<Int>,  // 1-12
    val beeValue: String,  // High, Medium, Low
    val description: String
)

private val indianFlora = listOf(
    FloraEntry("Mustard", "🌼", listOf(11, 12, 1, 2), "High", "Major nectar source in North India"),
    FloraEntry("Sunflower", "🌻", listOf(2, 3, 4, 5), "High", "Rich pollen and nectar producer"),
    FloraEntry("Litchi", "🍈", listOf(2, 3, 4), "High", "Premium honey source in Bihar & UP"),
    FloraEntry("Eucalyptus", "🌿", listOf(1, 2, 3, 11, 12), "Medium", "Year-round nectar in southern regions"),
    FloraEntry("Mango", "🥭", listOf(1, 2, 3), "Medium", "Provides early season nectar"),
    FloraEntry("Tulsi (Basil)", "🌱", listOf(6, 7, 8, 9, 10), "Medium", "Monsoon-season medicinal flora"),
    FloraEntry("Neem", "🌳", listOf(3, 4, 5), "Medium", "Bitter honey, medicinal properties"),
    FloraEntry("Coriander", "🌿", listOf(12, 1, 2, 3), "High", "Important winter crop for bees"),
    FloraEntry("Jamun", "🫐", listOf(3, 4, 5, 6), "Medium", "Summer blooming fruit tree"),
    FloraEntry("Mahua", "🌺", listOf(2, 3, 4), "High", "Forest honey source in central India"),
    FloraEntry("Drumstick", "🥒", listOf(1, 2, 3, 4), "Low", "Supplementary nectar source"),
    FloraEntry("Ajwain", "🌿", listOf(10, 11, 12), "Medium", "Late season forage crop")
)

@Composable
fun FloraCalendarScreen() {
    val currentMonth = remember { Calendar.getInstance().get(Calendar.MONTH) + 1 }
    val bloomingNow = remember { indianFlora.filter { currentMonth in it.bloomMonths } }

    // Honey Flow Season Progress
    val (seasonName, seasonProgress) = remember {
        when (currentMonth) {
            in 11..12, in 1..2 -> "Winter Flow (Mustard Season)" to
                    when(currentMonth) { 11 -> 0.25f; 12 -> 0.5f; 1 -> 0.75f; else -> 1.0f }
            in 3..5 -> "Spring Flow (Litchi/Mango)" to
                    when(currentMonth) { 3 -> 0.33f; 4 -> 0.66f; else -> 1.0f }
            in 6..8 -> "Monsoon (Low Flow)" to
                    when(currentMonth) { 6 -> 0.33f; 7 -> 0.66f; else -> 1.0f }
            else -> "Autumn Prep" to
                    when(currentMonth) { 9 -> 0.5f; else -> 1.0f }
        }
    }

    val animatedProgress by animateFloatAsState(targetValue = seasonProgress,
        animationSpec = tween(1500), label = "progress")

    LazyColumn(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 16.dp)) {

        // Header
        item {
            Box(modifier = Modifier.fillMaxWidth()
                .background(Brush.verticalGradient(listOf(FloraGreen, FloraGreenLight.copy(0.3f), Color.Transparent)))
                .padding(24.dp)) {
                Column {
                    Text("🌸 Flora Calendar", style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Nectar flow guide for Indian beekeeping", style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(0.8f))
                }
            }
        }

        // Honey Flow Season Progress Bar
        item {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = HoneyLight.copy(0.3f)),
                shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.WaterDrop, null, tint = HoneyAmber, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Honey Flow Season", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(seasonName, style = MaterialTheme.typography.bodyMedium, color = HoneyAmber, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(12.dp))
                    LinearProgressIndicator(progress = animatedProgress, modifier = Modifier.fillMaxWidth().height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                        color = HoneyAmber, trackColor = HoneyCream)
                    Spacer(Modifier.height(4.dp))
                    Text("${(animatedProgress * 100).toInt()}% through season", style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f))
                }
            }
        }

        // Blooming Now
        item {
            Text("🌺 Blooming Now", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp, 8.dp))
        }

        if (bloomingNow.isEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5f)),
                    shape = RoundedCornerShape(16.dp)) {
                    Text("No major flora blooming this month", Modifier.padding(24.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.7f))
                }
            }
        } else {
            item {
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    bloomingNow.take(3).forEach { flora ->
                        Card(modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = FloraGreenPale.copy(0.5f)),
                            shape = RoundedCornerShape(16.dp)) {
                            Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(flora.emoji, style = MaterialTheme.typography.headlineMedium)
                                Spacer(Modifier.height(4.dp))
                                Text(flora.name, style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold, maxLines = 1)
                                BeeValueBadge(flora.beeValue)
                            }
                        }
                    }
                }
            }
            if (bloomingNow.size > 3) {
                item {
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        bloomingNow.drop(3).take(3).forEach { flora ->
                            Card(modifier = Modifier.weight(1f),
                                colors = CardDefaults.cardColors(containerColor = FloraGreenPale.copy(0.5f)),
                                shape = RoundedCornerShape(16.dp)) {
                                Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(flora.emoji, style = MaterialTheme.typography.headlineMedium)
                                    Spacer(Modifier.height(4.dp))
                                    Text(flora.name, style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold, maxLines = 1)
                                    BeeValueBadge(flora.beeValue)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Full Flora Guide
        item {
            Spacer(Modifier.height(16.dp))
            Text("📅 Yearly Flora Guide", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp, 8.dp))
        }

        items(indianFlora) { flora ->
            FloraGuideCard(flora, currentMonth, Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
        }

        // Season Legend
        item {
            Spacer(Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5f)),
                shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Season Guide", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    SeasonRow("🌾 Winter Flow", "Nov–Feb", "Peak mustard/coriander season")
                    SeasonRow("🌸 Spring Flow", "Mar–May", "Litchi, mango, neem blooming")
                    SeasonRow("🌧️ Monsoon", "Jun–Aug", "Low flow, supplementary feeding needed")
                    SeasonRow("🍂 Autumn Prep", "Sep–Oct", "Prepare colonies for winter flow")
                }
            }
        }
    }
}

@Composable
private fun FloraGuideCard(flora: FloraEntry, currentMonth: Int, modifier: Modifier) {
    val isActive = currentMonth in flora.bloomMonths
    Card(modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isActive) FloraGreenPale.copy(0.4f)
            else MaterialTheme.colorScheme.surfaceVariant.copy(0.3f)),
        shape = RoundedCornerShape(16.dp),
        border = if (isActive) androidx.compose.foundation.BorderStroke(1.dp, FloraGreen.copy(0.3f)) else null) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(flora.emoji, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(flora.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        if (isActive) {
                            Spacer(Modifier.width(8.dp))
                            Surface(color = FloraGreen.copy(0.15f), shape = RoundedCornerShape(6.dp)) {
                                Text("ACTIVE", Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall, color = FloraGreen, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Text(flora.description, style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.7f))
                }
                BeeValueBadge(flora.beeValue)
            }
            Spacer(Modifier.height(10.dp))
            // Month heat-map bar
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                val months = listOf("J","F","M","A","M","J","J","A","S","O","N","D")
                months.forEachIndexed { index, label ->
                    val month = index + 1
                    val isBloom = month in flora.bloomMonths
                    val isCurrent = month == currentMonth
                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp))
                            .background(when {
                                isCurrent && isBloom -> FloraGreen
                                isBloom -> FloraGreenLight.copy(0.6f)
                                isCurrent -> HoneyGold.copy(0.4f)
                                else -> MaterialTheme.colorScheme.outline.copy(0.15f)
                            }))
                        Text(label, style = MaterialTheme.typography.labelSmall,
                            color = if (isCurrent) MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(0.4f),
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }
        }
    }
}

@Composable
private fun BeeValueBadge(value: String) {
    val color = when(value) { "High" -> SuccessGreen; "Medium" -> HoneyAmber; else -> AlertRed }
    Surface(color = color.copy(0.1f), shape = RoundedCornerShape(8.dp)) {
        Text("🐝 $value", Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun SeasonRow(title: String, months: String, desc: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.width(120.dp))
        Text(months, style = MaterialTheme.typography.labelSmall, color = HoneyAmber,
            fontWeight = FontWeight.Medium, modifier = Modifier.width(60.dp))
        Text(desc, style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.7f))
    }
}
