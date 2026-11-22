package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.NeonCard
import com.example.ui.theme.*

@Composable
fun SettingsScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            NeonCard {
                Text(
                    text = "GENERAL SETTINGS",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                SettingRow(title = "Neon Glow Effects", subtitle = "Enable high-intensity blue neon UI styling", isChecked = true)
                SettingRow(title = "Cloud Audio Feedback", subtitle = "Play futuristic sci-fi sound effects", isChecked = true)
                SettingRow(title = "Background Auto-Sync", subtitle = "Keep virtual container alive in background", isChecked = false)
            }
        }

        item {
            NeonCard {
                Text(
                    text = "VIRTUAL DEVICE CONFIG",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                SpecRow(label = "Virtual Architecture", value = "ARM64-v8a (Virtualized)")
                SpecRow(label = "CPU Allocation", value = "8 Dedicated Cores")
                SpecRow(label = "Graphics Renderer", value = "OpenGL ES 3.2 / Vulkan")
                SpecRow(label = "Storage Mount", value = "/dev/block/vdc1 (256 GB)")
            }
        }

        item {
            NeonCard {
                Text(
                    text = "ABOUT NISHA xCLONE",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "NISHA xCLONE v1.0.0",
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "A complete server-free local Android 13 cloud phone simulator with blue neon gaming interface and isolated root management.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No paid features • No subscriptions • 100% Free",
                    color = NeonGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SettingRow(title: String, subtitle: String, isChecked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(text = subtitle, color = TextSecondary, fontSize = 11.sp)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = {},
            colors = SwitchDefaults.colors(
                checkedThumbColor = DeepNavy,
                checkedTrackColor = NeonCyan
            )
        )
    }
}
