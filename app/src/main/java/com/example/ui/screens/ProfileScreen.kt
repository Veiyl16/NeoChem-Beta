package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*
import com.example.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfileState by authViewModel.userProfile.collectAsState()
    val progressState by authViewModel.allMateriProgress.collectAsState()

    var isEditingName by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf("") }
    var selectedAvatarIdx by remember { mutableStateOf(0) }

    val authState by authViewModel.authState.collectAsState()
    LaunchedEffect(authState) {
        if (authState is com.example.ui.viewmodel.AuthState.Idle) {
            onLogout()
        }
    }

    // Sync edited name with current db state upon initial screen bind
    LaunchedEffect(userProfileState) {
        userProfileState?.let {
            if (editedName.isEmpty()) {
                editedName = it.username
            }
            selectedAvatarIdx = it.avatarIndex
        }
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val onBgColor = MaterialTheme.colorScheme.onBackground
    val cardBg = MaterialTheme.colorScheme.surfaceVariant

    FuturisticBg(modifier = modifier) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.profile_settings_title),
                            color = onBgColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        NeoChemBackButton(onClick = onBack, modifier = Modifier.padding(start = 12.dp))
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            userProfileState?.let { profile ->
                // Temp debug log
                android.util.Log.d("NeoChem_Debug", "Profile XP: ${profile.xp}")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // AVATAR PHOTO (Static/built-in only)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.size(96.dp)
                        ) {
                            AvatarView(
                                avatarIndex = selectedAvatarIdx,
                                size = 92.dp,
                                photoUrl = profile.photoUrl,
                                modifier = Modifier.testTag("profile_avatar_selector")
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(R.string.choose_avatar_desc),
                            color = primaryColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Avatar Selection carousel
                    Text(
                        stringResource(R.string.ganti_avatar),
                        color = onBgColor.copy(alpha = 0.5f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (i in 0..5) {
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        selectedAvatarIdx = i
                                        authViewModel.updateProfile(editedName, i)
                                    }
                            ) {
                                AvatarView(
                                    avatarIndex = i,
                                    size = 40.dp,
                                    modifier = Modifier.padding(2.dp)
                                )
                                if (selectedAvatarIdx == i) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .border(2.dp, primaryColor, CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // EDIT USERNAME BOX
                    if (isEditingName) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = editedName,
                                onValueChange = { editedName = it },
                                singleLine = true,
                                label = { Text(stringResource(R.string.ubah_nama), color = primaryColor) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = onBgColor,
                                    unfocusedTextColor = onBgColor,
                                    focusedBorderColor = primaryColor,
                                    unfocusedBorderColor = onBgColor.copy(alpha = 0.15f)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("edit_name_input")
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(
                                onClick = {
                                    if (editedName.isNotBlank()) {
                                        authViewModel.updateProfile(editedName.trim(), selectedAvatarIdx)
                                    }
                                    isEditingName = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("OK", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = profile.username,
                                color = onBgColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = { isEditingName = true },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Name",
                                    tint = primaryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Text(
                            text = profile.email,
                            color = onBgColor.copy(alpha = 0.5f),
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // PROGRESS STATS CARD
                    Text(
                        text = stringResource(R.string.analisis_aktivitas),
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Level Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(onBgColor.copy(alpha = 0.03f))
                                .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(stringResource(R.string.level_belajar), color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                val levelValue = com.example.util.LevelSystem.calculateLevel(profile.xp)
                                Text("$levelValue", color = primaryColor, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }

                        // XP Belajar Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(onBgColor.copy(alpha = 0.03f))
                                .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(stringResource(R.string.xp_belajar), color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("${profile.xp}", color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Materi Selesai Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(onBgColor.copy(alpha = 0.03f))
                                .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(stringResource(R.string.materi_selesai), color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                val completedMateri = progressState.count { it.isCompleted }
                                Text("$completedMateri", color = secondaryColor, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }

                        // Quiz Selesai Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(onBgColor.copy(alpha = 0.03f))
                                .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(stringResource(R.string.quiz_selesai), color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                val completedMateri = progressState.count { it.isCompleted }
                                val quizVal = maxOf(completedMateri, profile.totalQuizzesCompleted)
                                Text("$quizVal", color = Color(0xFFEC4899), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Simulasi Selesai Box
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(onBgColor.copy(alpha = 0.03f))
                                .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(stringResource(R.string.simulasi_selesai), color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("${profile.totalSimulationsCompleted}", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }

                        // Spacer to hold balance
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // SETTINGS AREA
                    Text(
                        text = stringResource(R.string.profile_settings_title),
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val context = androidx.compose.ui.platform.LocalContext.current
                    val lowPerfEnabled by com.example.data.local.NeoChemSettings.lowPerformanceMode.collectAsState()
                    val soundEnabled by com.example.data.local.NeoChemSettings.soundEnabled.collectAsState()
                    val selectedThemeMode by com.example.data.local.NeoChemSettings.themeMode.collectAsState()
                    // 1. Theme selection segmented card
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Palette, contentDescription = "Tema", tint = primaryColor, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(stringResource(R.string.theme_title), color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                stringResource(R.string.theme_desc),
                                color = onBgColor.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                                    .background(onBgColor.copy(alpha = 0.02f))
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                val modes = listOf("Terang", "Gelap", "Sistem")
                                modes.forEachIndexed { index, label ->
                                    val isSelected = selectedThemeMode == index
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (isSelected) primaryColor else Color.Transparent)
                                            .clickable {
                                                com.example.data.local.NeoChemSettings.setThemeMode(context, index)
                                                com.example.util.SoundManager.playClick()
                                            }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = label,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else onBgColor.copy(alpha = 0.7f),
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 2. Sound Effects toggle card
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(stringResource(R.string.sound_effects), color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(stringResource(R.string.sound_effects_desc), color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp)
                            }

                            Switch(
                                checked = soundEnabled,
                                onCheckedChange = {
                                    com.example.data.local.NeoChemSettings.setSoundEnabled(context, it)
                                    if (it) {
                                        com.example.util.SoundManager.playSuccess()
                                    } else {
                                        com.example.util.SoundManager.playClick()
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = primaryColor,
                                    checkedTrackColor = primaryColor.copy(alpha = 0.3f)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3. Sign Out row
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { authViewModel.signOut() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(stringResource(R.string.logout), color = Color(0xFFEF4444), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(stringResource(R.string.logout_desc), color = onBgColor.copy(alpha = 0.4f), fontSize = 11.sp)
                            }

                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Keluar akun",
                                tint = Color(0xFFEF4444)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Branding Footer
                    Text(
                        text = "NeoChem v1.0.3 - Premium Simplicity Education\nLightweight Education & Interactive Lab",
                        color = onBgColor.copy(alpha = 0.3f),
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
            }
        }
    }
}
