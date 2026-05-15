package com.madhumarga.app.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.madhumarga.app.ui.screens.*
import com.madhumarga.app.ui.theme.HoneyAmber
import com.madhumarga.app.viewmodel.MadhuMargaViewModel
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    object Dashboard : Screen("dashboard", "Home", Icons.Outlined.Home, Icons.Filled.Home)
    object HiveRegister : Screen("hives", "Hives", Icons.Outlined.Hive, Icons.Filled.Hive)
    object InspectionLog : Screen("inspections", "Inspect", Icons.Outlined.Checklist, Icons.Filled.Checklist)
    object HarvestTracker : Screen("harvest", "Harvest", Icons.Outlined.WaterDrop, Icons.Filled.WaterDrop)
    object FloraCalendar : Screen("flora", "Flora", Icons.Outlined.LocalFlorist, Icons.Filled.LocalFlorist)
}

val bottomNavScreens = listOf(
    Screen.Dashboard, Screen.HiveRegister, Screen.InspectionLog,
    Screen.HarvestTracker, Screen.FloraCalendar
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MadhuMargaNavGraph(viewModel: MadhuMargaViewModel = viewModel()) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Collect snackbar messages
    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect { message ->
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavScreens.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (selected) screen.selectedIcon else screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(
                                screen.title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = HoneyAmber,
                            selectedTextColor = HoneyAmber,
                            indicatorColor = HoneyAmber.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) },
            exitTransition = { fadeOut(animationSpec = androidx.compose.animation.core.tween(300)) }
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToHives = {
                        navController.navigate(Screen.HiveRegister.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    onNavigateToInspections = {
                        navController.navigate(Screen.InspectionLog.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    onNavigateToHarvest = {
                        navController.navigate(Screen.HarvestTracker.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    },
                    onNavigateToFlora = {
                        navController.navigate(Screen.FloraCalendar.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true; restoreState = true
                        }
                    }
                )
            }
            composable(Screen.HiveRegister.route) { HiveRegisterScreen(viewModel) }
            composable(Screen.InspectionLog.route) { InspectionLogScreen(viewModel) }
            composable(Screen.HarvestTracker.route) { HarvestTrackerScreen(viewModel) }
            composable(Screen.FloraCalendar.route) { FloraCalendarScreen() }
        }
    }
}
