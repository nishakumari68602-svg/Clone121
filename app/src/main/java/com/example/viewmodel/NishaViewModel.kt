package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.BackgroundTask
import com.example.model.RootPermissionItem
import com.example.model.SystemFileItem
import com.example.model.VirtualApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NishaViewModel : ViewModel() {

    private val _isPhoneRunning = MutableStateFlow(true)
    val isPhoneRunning: StateFlow<Boolean> = _isPhoneRunning.asStateFlow()

    private val _phoneStateText = MutableStateFlow("ACTIVE / ONLINE")
    val phoneStateText: StateFlow<String> = _phoneStateText.asStateFlow()

    private val _virtualRamUsed = MutableStateFlow(3.2f) // GB out of 8.0 GB
    val virtualRamUsed: StateFlow<Float> = _virtualRamUsed.asStateFlow()

    private val _virtualStorageUsed = MutableStateFlow(48.2f) // GB out of 256 GB
    val virtualStorageUsed: StateFlow<Float> = _virtualStorageUsed.asStateFlow()

    private val _networkPing = MutableStateFlow(12) // ms
    val networkPing: StateFlow<Int> = _networkPing.asStateFlow()

    private val _batteryLevel = MutableStateFlow(98)
    val batteryLevel: StateFlow<Int> = _batteryLevel.asStateFlow()

    // Root Manager
    private val _isRootEnabled = MutableStateFlow(true)
    val isRootEnabled: StateFlow<Boolean> = _isRootEnabled.asStateFlow()

    private val _rootCheckLog = MutableStateFlow("Ready for root validation inside virtual environment.")
    val rootCheckLog: StateFlow<String> = _rootCheckLog.asStateFlow()

    private val _rootPermissions = MutableStateFlow(
        mutableListOf(
            RootPermissionItem("com.nisha.filemanager", "Virtual File Manager", true, 42),
            RootPermissionItem("com.nisha.shell", "Nisha Terminal Shell", true, 128),
            RootPermissionItem("com.nisha.gameturbo", "Game Turbo Booster", true, 19),
            RootPermissionItem("com.android.browser", "Virtual Browser", false, 0)
        )
    )
    val rootPermissions: StateFlow<List<RootPermissionItem>> = _rootPermissions.asStateFlow()

    // System File Manager
    private val _currentPath = MutableStateFlow("/system/xbin")
    val currentPath: StateFlow<String> = _currentPath.asStateFlow()

    private val _systemFiles = MutableStateFlow(
        listOf(
            SystemFileItem("/system/xbin", "su", false, "248 KB", "rwsr-xr-x"),
            SystemFileItem("/system/xbin", "busybox", false, "1.2 MB", "rwxr-xr-x"),
            SystemFileItem("/system/app", "NishaOverlay.apk", false, "14.5 MB", "rw-r--r--"),
            SystemFileItem("/system/etc", "init.nisha.rc", false, "4.2 KB", "rw-r--r--"),
            SystemFileItem("/data/local", "tmp", true, "4.0 KB", "rwxrwx--x")
        )
    )
    val systemFiles: StateFlow<List<SystemFileItem>> = _systemFiles.asStateFlow()

    // Virtual Apps
    private val _virtualApps = MutableStateFlow(
        listOf(
            VirtualApp("chrome", "Chrome Browser", "public", "Internet", true, false),
            VirtualApp("playstore", "Nisha Store", "store", "System", true, true),
            VirtualApp("filemanager", "File Manager", "folder", "System", true, true),
            VirtualApp("settings", "Virtual Settings", "settings", "System", true, true),
            VirtualApp("gallery", "Gallery", "image", "Media", true, false),
            VirtualApp("calculator", "Calculator", "calculate", "Tools", true, true),
            VirtualApp("gameturbo", "Game Turbo", "sports_esports", "Gaming", true, true)
        )
    )
    val virtualApps: StateFlow<List<VirtualApp>> = _virtualApps.asStateFlow()

    // Active Modal App ("browser", "playstore", "filemanager", "gallery", "calculator", "settings", null)
    private val _activeApp = MutableStateFlow<String?>(null)
    val activeApp: StateFlow<String?> = _activeApp.asStateFlow()

    // Gaming Dashboard
    private val _currentFps = MutableStateFlow(60)
    val currentFps: StateFlow<Int> = _currentFps.asStateFlow()

    private val _isBoostMode = MutableStateFlow(true)
    val isBoostMode: StateFlow<Boolean> = _isBoostMode.asStateFlow()

    private val _cpuGovernor = MutableStateFlow("Performance (Nisha Tuned)")
    val cpuGovernor: StateFlow<String> = _cpuGovernor.asStateFlow()

    private val _backgroundTasks = MutableStateFlow(
        listOf(
            BackgroundTask(101, "com.nisha.cloudservice", 2.4f, 142),
            BackgroundTask(105, "com.android.surfaceflinger", 4.1f, 210),
            BackgroundTask(112, "com.nisha.gameboost", 1.8f, 95),
            BackgroundTask(128, "com.google.android.gms", 0.9f, 180)
        )
    )
    val backgroundTasks: StateFlow<List<BackgroundTask>> = _backgroundTasks.asStateFlow()

    init {
        // Start simulated metrics ticker
        viewModelScope.launch {
            while (true) {
                delay(3000)
                if (_isPhoneRunning.value) {
                    // Slight variation in ping & FPS
                    _networkPing.update { (10..18).random() }
                    if (_isBoostMode.value) {
                        _currentFps.update { (118..120).random() }
                    } else {
                        _currentFps.update { (58..60).random() }
                    }
                }
            }
        }
    }

    fun startCloudPhone() {
        _phoneStateText.value = "STARTING..."
        viewModelScope.launch {
            delay(1200)
            _isPhoneRunning.value = true
            _phoneStateText.value = "ACTIVE / ONLINE"
        }
    }

    fun stopCloudPhone() {
        _phoneStateText.value = "STOPPED"
        _isPhoneRunning.value = false
    }

    fun restartCloudPhone() {
        _phoneStateText.value = "RESTARTING..."
        _isPhoneRunning.value = false
        viewModelScope.launch {
            delay(1500)
            _isPhoneRunning.value = true
            _phoneStateText.value = "ACTIVE / ONLINE"
        }
    }

    fun toggleRoot(enabled: Boolean) {
        _isRootEnabled.value = enabled
        if (enabled) {
            _rootCheckLog.value = "Root access enabled in isolated virtual environment."
        } else {
            _rootCheckLog.value = "Root access disabled."
        }
    }

    fun runRootAccessCheck() {
        if (!_isRootEnabled.value) {
            _rootCheckLog.value = "[-] Root check failed: Root access is turned OFF in Virtual Settings."
            return
        }
        _rootCheckLog.value = "[*] Executing 'su' binary check...\n[+] UID: 0 (root) - Superuser privileges confirmed in virtual container."
    }

    fun toggleAppRoot(packageName: String) {
        _rootPermissions.update { list ->
            list.map {
                if (it.packageName == packageName) {
                    it.copy(isGranted = !it.isGranted)
                } else {
                    it
                }
            }.toMutableList()
        }
    }

    fun boostRam() {
        _virtualRamUsed.update { maxOf(1.8f, it - 1.2f) }
    }

    fun clearStorage() {
        _virtualStorageUsed.update { maxOf(42.0f, it - 3.5f) }
    }

    fun killTask(taskId: Int) {
        _backgroundTasks.update { tasks ->
            tasks.filter { it.id != taskId }
        }
    }

    fun toggleBoostMode() {
        _isBoostMode.update { !it }
        if (_isBoostMode.value) {
            _currentFps.value = 120
            _cpuGovernor.value = "Ultra Gaming Turbo (120Hz)"
        } else {
            _currentFps.value = 60
            _cpuGovernor.value = "Balanced Power"
        }
    }

    fun setActiveApp(appId: String?) {
        _activeApp.value = appId
    }
}
