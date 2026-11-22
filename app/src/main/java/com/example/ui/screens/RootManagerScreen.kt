package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RootPermissionItem
import com.example.model.SystemFileItem
import com.example.ui.components.NeonButton
import com.example.ui.components.NeonCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun RootManagerScreen(
    isRootEnabled: Boolean,
    rootCheckLog: String,
    rootPermissions: List<RootPermissionItem>,
    systemFiles: List<SystemFileItem>,
    onToggleRoot: (Boolean) -> Unit,
    onRunRootCheck: () -> Unit,
    onToggleAppRoot: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Root Status Card
        item {
            NeonCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "VIRTUAL ROOT MANAGER",
                            color = NeonCyan,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sandbox root access (Isolated to virtual environment)",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    StatusBadge(
                        text = if (isRootEnabled) "ROOT ACTIVE" else "ROOT OFF",
                        isActive = isRootEnabled
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Enable Virtual Root (su)",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Switch(
                        checked = isRootEnabled,
                        onCheckedChange = onToggleRoot,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DeepNavy,
                            checkedTrackColor = NeonCyan
                        )
                    )
                }
            }
        }

        // Root Access Checker Card
        item {
            NeonCard {
                Text(
                    text = "ROOT ACCESS CHECKER",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Test su binary execution within the virtual container.",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                NeonButton(
                    text = "Run Root Validation",
                    onClick = onRunRootCheck,
                    icon = Icons.Default.Security,
                    isPrimary = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = DeepNavy
                ) {
                    Text(
                        text = rootCheckLog,
                        color = if (isRootEnabled) NeonGreen else NeonRed,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Root Permissions App List
        item {
            NeonCard {
                Text(
                    text = "ROOT PERMISSION CONTROL",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Manage apps requesting superuser privileges",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                rootPermissions.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.appName,
                                color = TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "${item.packageName} • Access: ${item.accessCount}x",
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                        Switch(
                            checked = item.isGranted,
                            onCheckedChange = { onToggleAppRoot(item.packageName) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = DeepNavy,
                                checkedTrackColor = NeonCyan
                            )
                        )
                    }
                }
            }
        }

        // Virtual System File Manager
        item {
            NeonCard {
                Text(
                    text = "VIRTUAL SYSTEM FILES (/system)",
                    color = NeonCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                systemFiles.forEach { file ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (file.isDirectory) Icons.Default.Folder else Icons.Default.InsertDriveFile,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = file.name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                Text(text = "${file.path} • ${file.size}", color = TextSecondary, fontSize = 11.sp)
                            }
                        }
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = CardBorder
                        ) {
                            Text(
                                text = file.permissions,
                                color = NeonCyan,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
