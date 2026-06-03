package com.example.ui.screens

import kotlinx.coroutines.delay
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboardScreen(
    authViewModel: AuthViewModel,
    onNavigateToMateri: () -> Unit,
    onNavigateToPeriodicTable: () -> Unit,
    onNavigateToSimulation: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToFlashcards: () -> Unit,
    onNavigateToKamus: () -> Unit,
    onNavigateToJikaDicampur: () -> Unit,
    onNavigateToWrapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfileState by authViewModel.userProfile.collectAsState()
    val achievementsState by authViewModel.allAchievements.collectAsState()
    val materiProgressState by authViewModel.allMateriProgress.collectAsState()

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val onBgColor = MaterialTheme.colorScheme.onBackground
    val cardBg = MaterialTheme.colorScheme.surface

    // Daily Chemistry Fact (reloads on launch / persistent for the day)
    val dailyFact = remember { com.example.data.dummy.ChemistryFacts.getRandomFact() }

    FuturisticBg(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. WELCOME SECTION (Warm, elegant and personal)
            val profile = userProfileState ?: com.example.data.model.UserProfile(
                id = 1,
                username = "Pengguna",
                email = "belajar@neochem.edu",
                xp = 0,
                level = 1,
                lastLoginTimestamp = System.currentTimeMillis(),
                avatarIndex = 0,
                totalQuizzesCompleted = 0,
                totalSimulationsCompleted = 0,
                photoUrl = "",
                coins = 100
            )
            // Temp debug log
            android.util.Log.d("NeoChem_Debug", "Dashboard XP: ${profile.xp}")
            val rawName = profile.username
            val displayName = if (rawName.isBlank() || rawName.contains("@") || rawName.equals("User", ignoreCase = true)) {
                "Rekan Kimiawan"
            } else {
                rawName
            }

            Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onNavigateToProfile() }
                        .padding(vertical = 12.dp)
                        .testTag("welcome_row"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarView(
                        avatarIndex = profile.avatarIndex,
                        size = 54.dp,
                        modifier = Modifier.testTag("home_profile_avatar"),
                        photoUrl = profile.photoUrl
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Halo, $displayName!",
                            color = onBgColor,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Selamat datang kembali di Ruang Belajar",
                            color = onBgColor.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Profile",
                        tint = onBgColor.copy(alpha = 0.4f),
                        modifier = Modifier.size(24.dp)
                    )
                }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 2. FAKTA KIMIA HARI INI
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = secondaryColor.copy(alpha = 0.25f),
                        backgroundColor = secondaryColor.copy(alpha = 0.02f)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Fakta",
                                tint = secondaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Fakta Kimia Hari Ini",
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = dailyFact,
                                    color = onBgColor.copy(alpha = 0.7f),
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3. LANJUTKAN BELAJAR (Direct smart redirect banner to continue reading or experimenting)
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToMateri() },
                        borderColor = primaryColor.copy(alpha = 0.15f),
                        backgroundColor = primaryColor.copy(alpha = 0.02f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(primaryColor.copy(alpha = 0.08f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Lanjutkan",
                                        tint = primaryColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = "Lanjutkan Belajar",
                                        color = onBgColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Kembali ke modul materi kimia & asah pemahamanmu!",
                                        color = onBgColor.copy(alpha = 0.55f),
                                        fontSize = 11.sp,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Buka",
                                tint = primaryColor,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 4. MATERI TERBARU (Core learning materials section)
                    Text(
                        text = "Materi Utama & Laboratorium",
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DashboardMenuTile(
                            title = "Molecule Reactor Lab",
                            subtitle = "Simulasi interaktif ikatan gas, atom besi korosi, dan pembakaran senyawa",
                            icon = Icons.Default.Science,
                            iconColor = secondaryColor,
                            onClick = onNavigateToSimulation
                        )

                        DashboardMenuTile(
                            title = "Tabel Periodik Inteligen",
                            subtitle = "Eksplorasi cerdas unsur golongan & periode dengan visualisasi filter",
                            icon = Icons.Default.ViewModule,
                            iconColor = primaryColor,
                            onClick = onNavigateToPeriodicTable
                        )

                        DashboardMenuTile(
                            title = "Modul Belajar Kimia",
                            subtitle = "Pelajari konsep alkana, benzena, asam-basa, lengkap dengan ringkasan",
                            icon = Icons.Default.MenuBook,
                            iconColor = Color(0xFF8B5CF6),
                            onClick = onNavigateToMateri
                        )

                        DashboardMenuTile(
                            title = "Prestasi & Pencapaian",
                            subtitle = "Klaim lencana prestasi dan bersaing di papan peringkat",
                            icon = Icons.Default.WorkspacePremium,
                            iconColor = Color(0xFFFBBF24),
                            onClick = onNavigateToLeaderboard
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 5. PROGRESS BELAJAR (Aura of academic completion)
                    Text(
                        text = "Progress Belajar",
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                onBgColor.copy(alpha = 0.06f),
                                RoundedCornerShape(12.dp)
                            )
                            .background(onBgColor.copy(alpha = 0.015f))
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        // Materi Selesai
                        val completedMateriCount = materiProgressState.count { it.isCompleted }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = "Materi Selesai",
                                    tint = secondaryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "$completedMateriCount",
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Materi Selesai",
                                color = onBgColor.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                        }

                        // Quiz Selesai
                        val completedQuizCount = maxOf(completedMateriCount, profile.totalQuizzesCompleted)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = "Quiz Selesai",
                                    tint = Color(0xFFFBBF24),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "$completedQuizCount",
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Quiz Selesai",
                                color = onBgColor.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                        }

                        // Simulasi Selesai
                        val completedSimulationCount = profile.totalSimulationsCompleted
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Science,
                                    contentDescription = "Simulasi Selesai",
                                    tint = Color(0xFF10B981),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "$completedSimulationCount",
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Simulasi Selesai",
                                color = onBgColor.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 6. LEVEL & XP (Fitted with Material 3 capsule, no cyberpunk graphics)
                    val level = com.example.util.LevelSystem.calculateLevel(profile.xp)
                    val currentLevelXp = com.example.util.LevelSystem.calculateCurrentXp(profile.xp)
                    val requiredXpForLevel = com.example.util.LevelSystem.calculateRequiredXp(level)
                    val progressFraction = (currentLevelXp.toFloat() / requiredXpForLevel).coerceIn(0f, 1f)

                    GlassCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = "Level",
                                    tint = primaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "LEVEL $level",
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }

                            Text(
                                text = "$currentLevelXp / $requiredXpForLevel XP",
                                color = primaryColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(onBgColor.copy(alpha = 0.08f))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progressFraction)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(primaryColor)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // 7. AKTIVITAS TERAKHIR / FITUR EKSPERIMEN LAINNYA
                    Text(
                        text = "Aktivitas Terakhir & Eksperimen",
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DashboardMenuTile(
                            title = "Flashcard Kimia",
                            subtitle = "Latih hafalan rumus, gugus fungsi, definisi & istilah organik",
                            icon = Icons.Default.CardMembership,
                            iconColor = Color(0xFFF43F5E),
                            onClick = onNavigateToFlashcards
                        )

                        DashboardMenuTile(
                            title = "Kamus Kimia Saku",
                            subtitle = "Pencarian super kilat arti benzena, entalpi, kegunaan, dan siri unsur",
                            icon = Icons.Default.Search,
                            iconColor = Color(0xFF06B6D4),
                            onClick = onNavigateToKamus
                        )

                        DashboardMenuTile(
                            title = "Reaktor Jika Dicampur...",
                            subtitle = "Mini lab pencampur zat reaktif (e.g., Na + H2O) lengkap hasil setara",
                            icon = Icons.Default.CompareArrows,
                            iconColor = Color(0xFF10B981),
                            onClick = onNavigateToJikaDicampur
                        )

                        DashboardMenuTile(
                            title = "Learning Insights",
                            subtitle = "Laporan komprehensif perkembangan kuis, materi selesai, dan akurasi akademik-mu",
                            icon = Icons.Default.TrendingUp,
                            iconColor = Color(0xFFA78BFA),
                            onClick = onNavigateToWrapped
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

@Composable
fun DashboardMenuTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    val onBgColor = MaterialTheme.colorScheme.onBackground

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconColor.copy(alpha = 0.12f))
                    .border(1.dp, iconColor.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = onBgColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    color = onBgColor.copy(alpha = 0.55f),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Buka",
                tint = onBgColor.copy(alpha = 0.35f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
