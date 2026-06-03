package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AuthViewModel
import com.example.data.repository.FirestoreLeaderboardEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    authViewModel: AuthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfileState by authViewModel.userProfile.collectAsState()
    val achievementsState by authViewModel.allAchievements.collectAsState()
    val rawLeaderboard by authViewModel.leaderboardList.collectAsState()

    var activeMainTab by remember { mutableStateOf(0) } // 0: Global Leaderboard, 1: Badges & Achievements
    var activeSortFilter by remember { mutableStateOf(0) } // 0: XP, 1: Akurasi, 2: Level

    // Compute sorted leaderboard based on activeSortFilter
    val sortedLeaderboard = remember(rawLeaderboard, activeSortFilter) {
        when (activeSortFilter) {
            0 -> rawLeaderboard.sortedByDescending { it.xp }
            1 -> rawLeaderboard.sortedByDescending { it.accuracy }
            else -> rawLeaderboard.sortedByDescending { it.level }
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
                            text = "Papan Peringkat",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                // User stats banner
                userProfileState?.let { profile ->
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AvatarView(
                                avatarIndex = profile.avatarIndex,
                                size = 48.dp,
                                photoUrl = profile.photoUrl
                            )
                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = profile.username,
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Rekan Ahli Kimia",
                                    color = onBgColor.copy(alpha = 0.55f),
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Lvl ${profile.level}",
                                    color = primaryColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "${profile.xp} XP",
                                    color = secondaryColor,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Custom futuristic tab switcher
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(onBgColor.copy(alpha = 0.04f))
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (activeMainTab == 0) primaryColor.copy(alpha = 0.12f) else Color.Transparent)
                            .border(
                                width = if (activeMainTab == 0) 1.dp else 0.dp,
                                color = if (activeMainTab == 0) primaryColor.copy(alpha = 0.4f) else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { activeMainTab = 0 }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Leaderboard",
                            color = if (activeMainTab == 0) onBgColor else onBgColor.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (activeMainTab == 1) primaryColor.copy(alpha = 0.12f) else Color.Transparent)
                            .border(
                                width = if (activeMainTab == 1) 1.dp else 0.dp,
                                color = if (activeMainTab == 1) primaryColor.copy(alpha = 0.4f) else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { activeMainTab = 1 }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Badges Anda",
                            color = if (activeMainTab == 1) onBgColor else onBgColor.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (activeMainTab == 0) {
                    // TAB 1: PERSISTENT FIRESTORE LEADERBOARD LIST
                    Text(
                        text = "Rangking Global Siswa",
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Pill Filters Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Urut XP", "Akurasi", "Level").forEachIndexed { index, label ->
                            val isSelected = activeSortFilter == index
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) primaryColor else onBgColor.copy(alpha = 0.03f))
                                    .border(1.dp, if (isSelected) primaryColor else onBgColor.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
                                    .clickable { activeSortFilter = index }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = label,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else onBgColor.copy(alpha = 0.7f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (sortedLeaderboard.isEmpty()) {
                        Column(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = primaryColor)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Menghubungkan ke Papan Peringkat...",
                                color = onBgColor.copy(alpha = 0.5f),
                                fontSize = 12.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f).testTag("leaderboard_list")
                        ) {
                            itemsIndexed(sortedLeaderboard) { index, entry ->
                                val scorePos = index + 1
                                val isSelf = entry.username == userProfileState?.username
                                val cardBorder = if (isSelf) secondaryColor else onBgColor.copy(alpha = 0.08f)
                                val cardBgColor = if (isSelf) secondaryColor.copy(alpha = 0.08f) else onBgColor.copy(alpha = 0.02f)

                                GlassCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    borderColor = cardBorder,
                                    backgroundColor = cardBgColor
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        // Rank badge
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    when (scorePos) {
                                                        1 -> Color(0xFFFBBF24).copy(alpha = 0.15f)
                                                        2 -> Color(0xFF94A3B8).copy(alpha = 0.15f)
                                                        3 -> Color(0xFFB45309).copy(alpha = 0.15f)
                                                        else -> onBgColor.copy(alpha = 0.06f)
                                                    }
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (scorePos <= 3) {
                                                Icon(
                                                    imageVector = Icons.Default.EmojiEvents,
                                                    contentDescription = "Rank $scorePos",
                                                    tint = when (scorePos) {
                                                        1 -> Color(0xFFFBBF24)
                                                        2 -> Color(0xFF94A3B8)
                                                        else -> Color(0xFFB45309)
                                                    },
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            } else {
                                                Text(
                                                    text = scorePos.toString(),
                                                    color = onBgColor.copy(alpha = 0.7f),
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(12.dp))

                                        AvatarView(
                                            avatarIndex = ((entry.username ?: "").hashCode().let { if (it < 0) -it else it } % 6),
                                            size = 36.dp,
                                            photoUrl = entry.photoUrl
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = if ((entry.username ?: "").isBlank()) "Siswa NeoChem" else (entry.username ?: ""),
                                                color = onBgColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                            val summaryText = when (activeSortFilter) {
                                                0 -> "Akurasi: ${entry.accuracy ?: 100}%  |  Level: Lvl ${entry.level ?: 1}"
                                                1 -> "XP: ${entry.xp ?: 0}  |  Level: Lvl ${entry.level ?: 1}"
                                                else -> "XP: ${entry.xp ?: 0}  |  Akurasi: ${entry.accuracy ?: 100}%"
                                            }
                                            Text(
                                                text = summaryText,
                                                color = onBgColor.copy(alpha = 0.5f),
                                                fontSize = 10.sp
                                            )
                                        }

                                        // Metric Value Showcase
                                        val displayValue = when (activeSortFilter) {
                                            0 -> "${entry.xp ?: 0} XP"
                                            1 -> "${entry.accuracy ?: 100}%"
                                            else -> "Lvl ${entry.level ?: 1}"
                                        }
                                        Text(
                                            text = displayValue,
                                            color = primaryColor,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // TAB 2: PERSONAL SECURE ENCRYPTED BADGES ACHIEVEMENTS DISPLAY
                    Text(
                        text = "Daftar Koleksi Lencana",
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f).testTag("badge_list")
                    ) {
                        items(achievementsState) { badge ->
                            val badgeColor = when (badge.id) {
                                "badge_welcome" -> Color(0xFF10B981)
                                "badge_master_atom" -> Color(0xFFEC4899)
                                "badge_korosi" -> Color(0xFFF59E0B)
                                "badge_element_hunter" -> Color(0xFF06B6D4)
                                "badge_alkana" -> Color(0xFF3B82F6)
                                "badge_reaksi_master" -> Color(0xFF14B8A6)
                                "badge_perfect" -> Color(0xFF8B5CF6)
                                "badge_kimia_pro" -> Color(0xFFEF4444)
                                "badge_speedster" -> Color(0xFFF43F5E)
                                else -> primaryColor
                            }

                            val borderTint = if (badge.isEarned) badgeColor else onBgColor.copy(alpha = 0.1f)
                            val badgeBg = if (badge.isEarned) cardBg.copy(alpha = 0.3f) else onBgColor.copy(alpha = 0.01f)

                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                borderColor = borderTint,
                                backgroundColor = badgeBg
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(52.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (badge.isEarned) badgeColor.copy(alpha = 0.15f) else onBgColor.copy(alpha = 0.04f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = when (badge.id) {
                                                "badge_welcome" -> Icons.Default.Handshake
                                                "badge_master_atom" -> Icons.Default.Science
                                                "badge_korosi" -> Icons.Default.Build
                                                "badge_element_hunter" -> Icons.Default.Favorite
                                                "badge_alkana" -> Icons.Default.Hub
                                                "badge_reaksi_master" -> Icons.Default.AutoStories
                                                "badge_perfect" -> Icons.Default.EmojiEvents
                                                "badge_kimia_pro" -> Icons.Default.WorkspacePremium
                                                "badge_speedster" -> Icons.Default.DirectionsRun
                                                else -> Icons.Default.MilitaryTech
                                            },
                                            contentDescription = badge.title,
                                            tint = if (badge.isEarned) badgeColor else onBgColor.copy(alpha = 0.2f),
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = badge.title,
                                            color = if (badge.isEarned) onBgColor else onBgColor.copy(alpha = 0.4f),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = badge.description,
                                            color = if (badge.isEarned) onBgColor.copy(alpha = 0.65f) else onBgColor.copy(alpha = 0.3f),
                                            fontSize = 11.sp,
                                            lineHeight = 15.sp
                                        )
                                    }

                                    if (badge.isEarned) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Didapatkan",
                                            tint = Color(0xFF10B981),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = "Terkunci",
                                            tint = onBgColor.copy(alpha = 0.15f),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
