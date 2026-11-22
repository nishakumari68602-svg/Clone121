package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BackgroundTask
import com.example.ui.components.NeonButton
import com.example.ui.components.NeonCard
import com.example.ui.theme.*

@Composable
fun GamingDashboardScreen(
    currentFps: Int,
    isBoostMode: Boolean,
    cpuGovernor: String,
    ping: Int,
    backgroundTasks: List<BackgroundTask>,
    onToggleBoostMode: () -> Unit,
    onKillTask: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Gaming Turbo Header Card
        item {
            NeonCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "NISHA GAME TURBO",
                            color = NeonCyan,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Hardware acceleration & FPS booster",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Ultra Boost Mode",
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (isBoostMode) "Locked at 120 FPS Max" else "Standard 60 FPS",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    Switch(
                        checked = isBoostMode,
                        onCheckedChange = { onToggleBoostMode() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DeepNavy,
                            checkedTrackColor = NeonCyan
                        )
                    )
                }
            }
        }

        // Live Telemetry Row (FPS & Ping)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NeonCard(modifier = Modifier.weight(1f)) {
                    Text(text = "LIVE FPS", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$currentFps FPS",
                        color = NeonCyan,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Stable Render Rate", color = TextSecondary, fontSize = 11.sp)
                }

                NeonCard(modifier = Modifier.weight(1f)) {
                    Text(text = "NETWORK LATENCY", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${ping}ms",
                        color = NeonGreen,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "5G Ultra Connection", color = TextSecondary, fontSize = 11.sp)
                }
            }
        }

        // CPU Governor & Profile
        item {
            NeonCard {
                Text(
                    text = "HARDWARE PROFILES",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                SpecRow(label = "CPU Governor", value = cpuGovernor)
                SpecRow(label = "GPU Acceleration", value = "Vulkan 1.3 High Performance")
                SpecRow(label = "Thermal State", value = "Normal (32.4°C)")
            }
        }

        // Background Tasks Optimizer
        item {
            NeonCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "BACKGROUND PROCESSES (${backgroundTasks.size})",
                        color = NeonCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                if (backgroundTasks.isEmpty()) {
                    Text(
                        text = "All background tasks optimized. System is clean.",
                        color = TextSecondary,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    backgroundTasks.forEach { task ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = task.name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                Text(text = "CPU: ${task.cpuUsage}% • RAM: ${task.memoryMb} MB", color = TextSecondary, fontSize = 11.sp)
                            }
                            IconButton(onClick = { onKillTask(task.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Kill Task",
                                    tint = NeonRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
