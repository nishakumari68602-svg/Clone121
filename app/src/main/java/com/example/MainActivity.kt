package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.NishaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NishaAppRoot()
            }
        }
    }
}

sealed class NavigationTab(val route: String, val title: String, val icon: ImageVector) {
    object Dashboard : NavigationTab("dashboard", "Cloud Phone", Icons.Default.Cloud)
    object VirtualOS : NavigationTab("virtual_os", "Virtual OS", Icons.Default.PhoneAndroid)
    object RootManager : NavigationTab("root", "Root Manager", Icons.Default.Security)
    object GameTurbo : NavigationTab("gaming", "Game Turbo", Icons.Default.SportsEsports)
    object Settings : NavigationTab("settings", "Settings", Icons.Default.Settings)
}

@Composable
fun NishaAppRoot(viewModel: NishaViewModel = viewModel()) {
    var currentTab by remember { mutableStateOf<NavigationTab>(NavigationTab.Dashboard) }

    val isPhoneRunning by viewModel.isPhoneRunning.collectAsState()
    val phoneStateText by viewModel.phoneStateText.collectAsState()
    val ramUsed by viewModel.virtualRamUsed.collectAsState()
    val storageUsed by viewModel.virtualStorageUsed.collectAsState()
    val ping by viewModel.networkPing.collectAsState()
    val battery by viewModel.batteryLevel.collectAsState()

    val isRootEnabled by viewModel.isRootEnabled.collectAsState()
    val rootCheckLog by viewModel.rootCheckLog.collectAsState()
    val rootPermissions by viewModel.rootPermissions.collectAsState()
    val systemFiles by viewModel.systemFiles.collectAsState()

    val virtualApps by viewModel.virtualApps.collectAsState()
    val activeApp by viewModel.activeApp.collectAsState()

    val currentFps by viewModel.currentFps.collectAsState()
    val isBoostMode by viewModel.isBoostMode.collectAsState()
    val cpuGovernor by viewModel.cpuGovernor.collectAsState()
    val backgroundTasks by viewModel.backgroundTasks.collectAsState()

    if (activeApp != null) {
        BackHandler {
            viewModel.setActiveApp(null)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (activeApp == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    val tabs = listOf(
                        NavigationTab.Dashboard,
                        NavigationTab.VirtualOS,
                        NavigationTab.RootManager,
                        NavigationTab.GameTurbo,
                        NavigationTab.Settings
                    )
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            icon = { Icon(tab.icon, contentDescription = tab.title) },
                            label = { Text(tab.title) },
                            selected = currentTab == tab,
                            onClick = { currentTab = tab },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NeonCyan,
                                selectedTextColor = NeonCyan,
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (activeApp != null) {
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                VirtualAppModalContainer(
                    appId = activeApp!!,
                    onClose = { viewModel.setActiveApp(null) }
                )
            }
        } else {
            Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                when (currentTab) {
                    NavigationTab.Dashboard -> CloudDashboardScreen(
                        isPhoneRunning = isPhoneRunning,
                        phoneStateText = phoneStateText,
                        ramUsed = ramUsed,
                        storageUsed = storageUsed,
                        ping = ping,
                        battery = battery,
                        onStartPhone = { viewModel.startCloudPhone() },
                        onStopPhone = { viewModel.stopCloudPhone() },
                        onRestartPhone = { viewModel.restartCloudPhone() },
                        onBoostRam = { viewModel.boostRam() },
                        onClearStorage = { viewModel.clearStorage() },
                        onOpenVirtualAndroid = { currentTab = NavigationTab.VirtualOS }
                    )
                    NavigationTab.VirtualOS -> VirtualAndroidScreen(
                        virtualApps = virtualApps,
                        batteryLevel = battery,
                        onLaunchApp = { appId ->
                            if (appId == "gameturbo") {
                                currentTab = NavigationTab.GameTurbo
                            } else {
                                viewModel.setActiveApp(appId)
                            }
                        }
                    )
                    NavigationTab.RootManager -> RootManagerScreen(
                        isRootEnabled = isRootEnabled,
                        rootCheckLog = rootCheckLog,
                        rootPermissions = rootPermissions,
                        systemFiles = systemFiles,
                        onToggleRoot = { viewModel.toggleRoot(it) },
                        onRunRootCheck = { viewModel.runRootAccessCheck() },
                        onToggleAppRoot = { viewModel.toggleAppRoot(it) }
                    )
                    NavigationTab.GameTurbo -> GamingDashboardScreen(
                        currentFps = currentFps,
                        isBoostMode = isBoostMode,
                        cpuGovernor = cpuGovernor,
                        ping = ping,
                        backgroundTasks = backgroundTasks,
                        onToggleBoostMode = { viewModel.toggleBoostMode() },
                        onKillTask = { viewModel.killTask(it) }
                    )
                    NavigationTab.Settings -> SettingsScreen()
                }
            }
        }
    }
}
