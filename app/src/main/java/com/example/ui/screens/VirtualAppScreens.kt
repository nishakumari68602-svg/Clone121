package com.example.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.components.NeonButton
import com.example.ui.components.NeonCard
import com.example.ui.theme.*

@Composable
fun VirtualAppModalContainer(
    appId: String,
    onClose: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DeepNavy
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardBackground)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClose) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Close", tint = NeonCyan)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (appId) {
                            "chrome" -> "Chrome Browser"
                            "playstore" -> "Nisha Store"
                            "filemanager" -> "Virtual File Manager"
                            "gallery" -> "Virtual Gallery"
                            "calculator" -> "Virtual Calculator"
                            else -> "Virtual App"
                        },
                        color = TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                }
            }

            // App Content
            Box(modifier = Modifier.fillMaxSize()) {
                when (appId) {
                    "chrome" -> VirtualBrowserView()
                    "playstore" -> VirtualPlayStoreView()
                    "filemanager" -> VirtualFileManagerView()
                    "gallery" -> VirtualGalleryView()
                    "calculator" -> VirtualCalculatorView()
                    else -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "App loaded inside virtual sandbox.", color = TextSecondary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VirtualBrowserView() {
    var url by remember { mutableStateOf("https://www.google.com") }
    var inputUrl by remember { mutableStateOf("https://www.google.com") }

    Column(modifier = Modifier.fillMaxSize()) {
        // URL Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputUrl,
                onValueChange = { inputUrl = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CardBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            NeonButton(text = "Go", onClick = { url = inputUrl }, modifier = Modifier.width(70.dp))
        }

        // WebView
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

data class StoreApp(val name: String, val category: String, val size: String, var isInstalled: Boolean)

@Composable
fun VirtualPlayStoreView() {
    val apps = remember {
        mutableStateListOf(
            StoreApp("Nisha PUBG Turbo", "Action", "1.2 GB", false),
            StoreApp("Neon Music Player", "Media", "45 MB", true),
            StoreApp("Cyber VPN Pro", "Tools", "18 MB", false),
            StoreApp("Nisha PDF Reader", "Productivity", "24 MB", true)
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "DISCOVER APPS & GAMES", color = NeonCyan, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Certified compatible apps for virtual Android 13 container", color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(apps) { app ->
            NeonCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = app.name, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${app.category} • ${app.size}", color = TextSecondary, fontSize = 12.sp)
                    }
                    Button(
                        onClick = { app.isInstalled = !app.isInstalled },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (app.isInstalled) CardBorder else NeonCyan,
                            contentColor = if (app.isInstalled) NeonCyan else DeepNavy
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = if (app.isInstalled) "Open" else "Install")
                    }
                }
            }
        }
    }
}

@Composable
fun VirtualFileManagerView() {
    val folders = listOf(
        Pair("/storage/emulated/0/Download", "Downloads (14 files)"),
        Pair("/storage/emulated/0/Pictures", "Neon Wallpapers (6 items)"),
        Pair("/system/bin", "System Binaries"),
        Pair("/sdcard/NishaBackups", "Cloud Backups")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "VIRTUAL STORAGE BROWSER", color = NeonCyan, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "256 GB Sandboxed File System", color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(folders) { folder ->
            NeonCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Folder, contentDescription = null, tint = NeonCyan)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = folder.first, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text(text = folder.second, color = TextSecondary, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun VirtualGalleryView() {
    val images = listOf("Nisha Neon Wallpaper 1", "Cyber Crown HD", "Lightning Hooded Artwork", "Cloud Phone Screenshot")

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = "VIRTUAL GALLERY", color = NeonCyan, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Saved wallpapers and snapshots", color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(images) { img ->
            NeonCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Image, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = img, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text(text = "PNG • 1080x2400 • 3.4 MB", color = TextSecondary, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun VirtualCalculatorView() {
    var display by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NeonCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = display,
                color = NeonCyan,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { display = "0" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("C", color = NeonRed, fontSize = 20.sp) }
            Button(onClick = { display = "7" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("7", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "8" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("8", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "9" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("9", color = TextPrimary, fontSize = 20.sp) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { display = "4" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("4", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "5" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("5", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "6" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("6", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "+" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)) { Text("+", color = TextPrimary, fontSize = 20.sp) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { display = "1" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("1", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "2" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("2", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "3" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = CardBackground)) { Text("3", color = TextPrimary, fontSize = 20.sp) }
            Button(onClick = { display = "=" }, modifier = Modifier.weight(1f).height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)) { Text("=", color = DeepNavy, fontSize = 20.sp) }
        }
    }
}
