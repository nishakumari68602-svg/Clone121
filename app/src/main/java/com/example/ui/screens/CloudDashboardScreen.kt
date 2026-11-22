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
import com.example.ui.components.NeonButton
import com.example.ui.components.NeonCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*
import java.util.Locale

@Composable
fun CloudDashboardScreen(
    isPhoneRunning: Boolean,
    phoneStateText: String,
    ramUsed: Float,
    storageUsed: Float,
    ping: Int,
    battery: Int,
    onStartPhone: () -> Unit,
    onStopPhone: () -> Unit,
    onRestartPhone: () -> Unit,
    onBoostRam: () -> Unit,
    onClearStorage: () -> Unit,
    onOpenVirtualAndroid: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header Status Banner
        item {
            NeonCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "NISHA CLOUD PHONE",
                            color = NeonCyan,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Virtual Android 13 Environment",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                    }
                    StatusBadge(text = phoneStateText, isActive = isPhoneRunning)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NeonButton(
                        text = "Start",
                        onClick = onStartPhone,
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.PlayArrow,
                        isPrimary = true,
                        enabled = !isPhoneRunning
                    )
                    NeonButton(
                        text = "Stop",
                        onClick = onStopPhone,
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Stop,
                        isPrimary = false,
                        enabled = isPhoneRunning
                    )
                    NeonButton(
                        text = "Restart",
                        onClick = onRestartPhone,
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Refresh,
                        isPrimary = false
                    )
                }
            }
        }

        // Enter Virtual Android Launcher Banner
        item {
            NeonCard(onClick = onOpenVirtualAndroid) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Open Virtual Home Screen",
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Launch isolated Android 13 apps & system",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = NeonCyan
                    )
                }
            }
        }

        // Device Info Card
        item {
            NeonCard {
                Text(
                    text = "VIRTUAL DEVICE SPECS",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                SpecRow(label = "Device Model", value = "Nisha Cloud X1 Pro")
                SpecRow(label = "Operating System", value = "Android 13 (Tiramisu)")
                SpecRow(label = "Virtual ID", value = "NC-8821-X")
                SpecRow(label = "Network Status", value = "5G Ultra (${ping}ms)")
                SpecRow(label = "Battery Health", value = "${battery}% (Cloud Charged)")
            }
        }

        // Storage & RAM Cards Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // RAM Card
                NeonCard(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "VIRTUAL RAM",
                        color = NeonCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${String.format(Locale.US, "%.1f", ramUsed)} GB / 8.0 GB",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { ramUsed / 8.0f },
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = NeonCyan,
                        trackColor = DarkSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NeonButton(
                        text = "Boost RAM",
                        onClick = onBoostRam,
                        isPrimary = false
                    )
                }

                // Storage Card
                NeonCard(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "VIRTUAL STORAGE",
                        color = NeonCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${String.format(Locale.US, "%.1f", storageUsed)} GB / 256 GB",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { storageUsed / 256.0f },
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = ElectricBlue,
                        trackColor = DarkSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NeonButton(
                        text = "Clean",
                        onClick = onClearStorage,
                        isPrimary = false
                    )
                }
            }
        }
    }
}

@Composable
fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondary, fontSize = 13.sp)
        Text(text = value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}
