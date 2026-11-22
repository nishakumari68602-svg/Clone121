package com.example.model

data class VirtualApp(
    val id: String,
    val name: String,
    val iconName: String,
    val category: String,
    val isInstalled: Boolean = true,
    val isSystem: Boolean = false,
    val version: String = "1.0.3"
)

data class RootPermissionItem(
    val packageName: String,
    val appName: String,
    var isGranted: Boolean,
    val accessCount: Int
)

data class SystemFileItem(
    val path: String,
    val name: String,
    val isDirectory: Boolean,
    val size: String,
    val permissions: String
)

data class BackgroundTask(
    val id: Int,
    val name: String,
    val cpuUsage: Float,
    val memoryMb: Int
)
