package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.dummy.ChemistryData
import com.example.data.model.MateriCategory
import com.example.data.model.SubMateri
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateriScreen(
    authViewModel: com.example.ui.viewmodel.AuthViewModel,
    onNavigateToQuiz: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State to trace if a category is selected and showing its subtopics or active details
    var selectedCategory by remember { mutableStateOf<MateriCategory?>(null) }
    var selectedSubMateri by remember { mutableStateOf<SubMateri?>(null) }
    
    val progressState by authViewModel.allMateriProgress.collectAsState()

    // Tracking reading states from the database progress
    val isReadingCompletedBySession = remember(selectedSubMateri?.id, progressState) {
        progressState.any { it.materiId == selectedSubMateri?.id && it.isCompleted }
    }

    // Quiz prompt state for current selected section
    var showChallengeComplete by remember { mutableStateOf(false) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var ansSubmitted by remember { mutableStateOf(false) }

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
                            text = selectedSubMateri?.title
                                ?: selectedCategory?.title
                                ?: "Modul Belajar",
                            color = onBgColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        NeoChemBackButton(
                            onClick = {
                                if (selectedSubMateri != null) {
                                    selectedSubMateri = null
                                    selectedAnswerIndex = null
                                    ansSubmitted = false
                                } else if (selectedCategory != null) {
                                    selectedCategory = null
                                } else {
                                    onBack()
                                }
                            },
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                // Render view based on drill-down state representation
                when {
                    selectedSubMateri != null -> {
                        // IMMERSIVE READING SCREEN WITH SUMMARY NOTES AND MINI CHALLENGE!
                        val sub = selectedSubMateri ?: return@Box
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))

                            // Glowing summary cards
                            GlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                borderColor = secondaryColor,
                                backgroundColor = cardBg.copy(alpha = 0.4f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("📝", fontSize = 28.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Ringkasan Cepat",
                                            color = secondaryColor,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = sub.summary,
                                            color = onBgColor.copy(alpha = 0.9f),
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Main explanatory interactive reading content
                            GlassCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                ChemistryText(
                                    text = sub.fullText,
                                    color = onBgColor.copy(alpha = 0.85f),
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

// Selesai Membaca & Dapatkan Koin Button
                            val completedButtonText = if (isReadingCompletedBySession) "Materi Selesai Dibaca! 🎉" else "Tandai Selesai Membaca 📖"
                            val completedButtonColor = if (isReadingCompletedBySession) Color(0xFF10B981) else primaryColor

                            Button(
                                onClick = {
                                    if (!isReadingCompletedBySession) {
                                        sub.id.let { id ->
                                            authViewModel.completeReadingMaterial(id)
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = completedButtonColor),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("complete_reading_button")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (isReadingCompletedBySession) Icons.Default.CheckCircle else Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = completedButtonText,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // GAMIFIED MINI CHALLENGE BOX
                            Text(
                                text = "⚡ Uji Pemahaman: Mini Challenge!",
                                color = onBgColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            GlassCard(
                                modifier = Modifier.fillMaxWidth().testTag("challenge_box"),
                                borderColor = primaryColor
                            ) {
                                Text(
                                    text = sub.challengeQuestion,
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Options List
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    sub.challengeOptions.forEachIndexed { idx, option ->
                                        val optBorderColor = when {
                                            ansSubmitted && idx == sub.challengeAnswerIndex -> Color(0xFF10B981) // Correct green
                                            ansSubmitted && selectedAnswerIndex == idx -> Color(0xFFEF4444) // Incorrect red
                                            selectedAnswerIndex == idx -> secondaryColor
                                            else -> onBgColor.copy(alpha = 0.12f)
                                        }

                                        val optBgColor = when {
                                            ansSubmitted && idx == sub.challengeAnswerIndex -> Color(0x2210B981)
                                            ansSubmitted && selectedAnswerIndex == idx -> Color(0x22EF4444)
                                            selectedAnswerIndex == idx -> secondaryColor.copy(alpha = 0.12f)
                                            else -> onBgColor.copy(alpha = 0.03f)
                                        }

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(optBgColor)
                                                .border(1.dp, optBorderColor, RoundedCornerShape(12.dp))
                                                .clickable(enabled = !ansSubmitted) {
                                                    selectedAnswerIndex = idx
                                                }
                                                .padding(14.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${'A' + idx}.  $option",
                                                color = onBgColor,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                if (!ansSubmitted) {
                                    Button(
                                        onClick = { ansSubmitted = true },
                                        enabled = selectedAnswerIndex != null,
                                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.align(Alignment.End).testTag("verify_challenge_button")
                                    ) {
                                        Text("Kunci Jawaban 🔒", color = MaterialTheme.colorScheme.onPrimary)
                                    }
                                } else {
                                    val isCorrect = selectedAnswerIndex == sub.challengeAnswerIndex
                                    Column(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = if (isCorrect) "Jawaban Benar! 🎉 (+20 XP)" else "Jawaban Kurang Tepat 🥺",
                                            color = if (isCorrect) Color(0xFF10B981) else Color(0xFFEF4444),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = sub.explanation,
                                            color = onBgColor.copy(alpha = 0.65f),
                                            fontSize = 11.sp,
                                            lineHeight = 15.sp
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Button(
                                            onClick = {
                                                selectedCategory?.id?.let { onNavigateToQuiz(it) }
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier.fillMaxWidth().testTag("launch_full_quiz_button")
                                        ) {
                                            Text("Ayo Ambil Kuis Lengkap!", color = MaterialTheme.colorScheme.onPrimary)
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(48.dp))
                        }
                    }

                    selectedCategory != null -> {
                        // CATEGORY TOPIC DETAIL LIST (LIST SUBTOPICS)
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Pilih Submateri yang ingin dipelajari:",
                                    color = onBgColor.copy(alpha = 0.8f),
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            items(selectedCategory?.subMateriList ?: emptyList()) { sub ->
                                GlassCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedSubMateri = sub }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(46.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(primaryColor.copy(alpha = 0.12f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MenuBook,
                                                contentDescription = null,
                                                tint = primaryColor,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(16.dp))

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = sub.title,
                                                color = onBgColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = sub.summary,
                                                color = onBgColor.copy(alpha = 0.55f),
                                                fontSize = 11.sp
                                            )
                                        }

                                        Icon(
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Buka",
                                            tint = onBgColor.copy(alpha = 0.4f),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                GlassButton(
                                    text = "Mulai Ujian Topik Kuis! 🎖️",
                                    isPrimary = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = { selectedCategory?.id?.let { onNavigateToQuiz(it) } }
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                            }
                        }
                    }

                    else -> {
                        // CATEGORY SUMMARY GRID
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize().testTag("materi_list")
                        ) {
                            item {
                                Text(
                                    text = "Modul Belajar Kimia Interaktif",
                                    color = onBgColor,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Pilih modul belajar, asah pemahaman kuis, & kuasai materi kimia!",
                                    color = onBgColor.copy(alpha = 0.55f),
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            items(ChemistryData.categories) { cat ->
                                val difficultyColor = when (cat.difficulty) {
                                    "Pemula" -> Color(0xFF10B981) // green
                                    "Menengah" -> Color(0xFF3B82F6) // blue
                                    else -> Color(0xFF8B5CF6) // purple
                                }

                                GlassCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedCategory = cat }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(6.dp))
                                                        .background(difficultyColor.copy(alpha = 0.15f))
                                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                                ) {
                                                    Text(
                                                        text = cat.difficulty,
                                                        color = difficultyColor,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "⏱️ ${cat.estimatedMinutes} mnt",
                                                    color = onBgColor.copy(alpha = 0.5f),
                                                    fontSize = 11.sp
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Text(
                                                text = cat.title,
                                                color = onBgColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

                                            Text(
                                                text = "${cat.subMateriList.size} Topik Belajar Menantang",
                                                color = primaryColor,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 12.sp
                                            )
                                        }

                                        val displayIcon = when (cat.iconName) {
                                            "blur_on" -> Icons.Default.BlurOn
                                            "grid_view" -> Icons.Default.GridView
                                            "link" -> Icons.Default.Link
                                            "bubble_chart" -> Icons.Default.BubbleChart
                                            else -> Icons.Default.Science
                                        }
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(primaryColor.copy(alpha = 0.12f))
                                                .border(1.dp, primaryColor.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = displayIcon,
                                                contentDescription = cat.title,
                                                tint = primaryColor,
                                                modifier = Modifier.size(26.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            
                            item {
                                Spacer(modifier = Modifier.height(48.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
