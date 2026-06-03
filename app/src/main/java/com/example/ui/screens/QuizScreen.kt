package com.example.ui.screens

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
import com.example.ui.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    quizViewModel: QuizViewModel,
    authViewModel: com.example.ui.viewmodel.AuthViewModel,
    materiId: String,
    onQuizClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions by quizViewModel.quizQuestions.collectAsState()
    val currentIndex by quizViewModel.currentIndex.collectAsState()
    val selectedOptionIndex by quizViewModel.selectedOptionIndex.collectAsState()
    val isAnswered by quizViewModel.isAnswered.collectAsState()
    val score by quizViewModel.score.collectAsState()
    val comboCount by quizViewModel.comboCount.collectAsState()
    val timeLeft by quizViewModel.timeLeft.collectAsState()
    val isFinished by quizViewModel.isFinished.collectAsState()
    val xpAwarded by quizViewModel.xpAwarded.collectAsState()
    val lastAnswerWasFast by quizViewModel.lastAnswerWasFast.collectAsState()
    val lives by quizViewModel.lives.collectAsState()

    val userProfile by authViewModel.userProfile.collectAsState()

    // Track active choice locally before submitting
    var selectedIndexLocal by remember(currentIndex) { mutableStateOf<Int?>(null) }

    // Automatic memory & thread cleanup upon leaving/exiting Quiz screen
    DisposableEffect(Unit) {
        onDispose {
            quizViewModel.resetQuizState()
        }
    }

    // Initialize once upon launch
    LaunchedEffect(materiId) {
        quizViewModel.startQuiz(materiId)
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
                            text = "Mode Ujian Kuis",
                            color = onBgColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    actions = {
                        IconButton(onClick = onQuizClose) {
                            Icon(Icons.Default.Close, contentDescription = "Selesai", tint = onBgColor)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
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
                if (isFinished) {
                    // --- HIGHLY GAMIFIED FINISHED RESULTS MODULE ---
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val accuracy = if (questions.isNotEmpty()) (score * 100) / questions.size else 0
                        
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(primaryColor.copy(alpha = 0.12f))
                                .border(2.dp, primaryColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when {
                                    accuracy == 100 -> Icons.Default.EmojiEvents
                                    accuracy >= 75 -> Icons.Default.Star
                                    else -> Icons.Default.Science
                                },
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = when {
                                accuracy == 100 -> "Sempurna! Luar Bila!"
                                accuracy >= 75 -> "Hebat! Kamu Juara!"
                                else -> "Latihan Terus Ya!"
                            },
                            color = onBgColor,
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Akurasi Ujianmu: $accuracy%",
                            color = secondaryColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Stats Board Grid
                        GlassCard(
                            modifier = Modifier.fillMaxWidth(),
                            borderColor = primaryColor
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = "RANGKUMAN PRESTASI",
                                    color = onBgColor.copy(alpha = 0.5f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                HorizontalDivider(color = onBgColor.copy(alpha = 0.08f))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Skor Benar", color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp)
                                        Text("$score dari ${questions.size}", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .height(36.dp)
                                            .background(onBgColor.copy(alpha = 0.12f))
                                    )

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Total XP", color = onBgColor.copy(alpha = 0.5f), fontSize = 11.sp)
                                        Text("+$xpAwarded XP", color = primaryColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = onQuizClose,
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("quiz_finish_back_button")
                        ) {
                            Text("Kembali ke Modul", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                } else if (questions.isNotEmpty()) {
                    // --- ACTIVE RUNNING INTERACTIVE QUESTIONS CAROUSEL ---
                    val currentQuestion = questions.getOrNull(currentIndex)
                    if (currentQuestion != null) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Question progress Indicator
                            val progressFraction = (currentIndex.toFloat() / questions.size).coerceIn(0f, 1f)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LinearProgressIndicator(
                                    progress = { progressFraction },
                                    color = primaryColor,
                                    trackColor = onBgColor.copy(alpha = 0.08f),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "${currentIndex + 1}/${questions.size}",
                                    color = onBgColor.copy(alpha = 0.60f),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Stats Headers: Timer, Lives, Combo triggers
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Real-time countdown timer circle
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(onBgColor.copy(alpha = 0.04f))
                                        .border(1.dp, if (timeLeft < 5) Color(0xFFEF4444) else onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Timer,
                                        contentDescription = "Sisa Detik",
                                        tint = if (timeLeft < 5) Color(0xFFEF4444) else primaryColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "${timeLeft}s",
                                        color = if (timeLeft < 5) Color(0xFFEF4444) else onBgColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }

                                // Interactive Combo Multiplying Streak bubble
                                if (comboCount > 1) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color(0xFFFBBF24).copy(alpha = 0.15f))
                                            .border(1.dp, Color(0xFFFBBF24), RoundedCornerShape(12.dp))
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(imageVector = Icons.Default.Whatshot, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Kombo x$comboCount", color = Color(0xFFFBBF24), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        }
                                    }
                                }

                                // Interactive Lives Count (Hearts) heart icons
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(onBgColor.copy(alpha = 0.04f))
                                        .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    for (i in 0 until 3) {
                                        val isFilled = i < lives
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Nyawa",
                                            tint = if (isFilled) Color(0xFFEF4444) else onBgColor.copy(alpha = 0.15f),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Main Interactive Question Sheet text
                            GlassCard(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = currentQuestion.challengeQuestion,
                                    color = onBgColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    lineHeight = 22.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Options List with beautiful color states based on accuracy & choices
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                val activeChoiceIndex = if (isAnswered) selectedOptionIndex else selectedIndexLocal

                                currentQuestion.challengeOptions.forEachIndexed { index, option ->
                                    val isSelected = activeChoiceIndex == index
                                    val isCorrect = index == currentQuestion.challengeAnswerIndex

                                    val borderColor = when {
                                        isAnswered && isCorrect -> Color(0xFF10B981) // Core Green highlight
                                        isAnswered && isSelected && !isCorrect -> Color(0xFFEF4444) // Error Red
                                        isSelected -> primaryColor // Active selected Cyan
                                        else -> onBgColor.copy(alpha = 0.12f)
                                    }

                                    val optionBgColor = when {
                                        isAnswered && isCorrect -> Color(0x2210B981)
                                        isAnswered && isSelected && !isCorrect -> Color(0x22EF4444)
                                        isSelected -> primaryColor.copy(alpha = 0.10f)
                                        else -> onBgColor.copy(alpha = 0.03f)
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(optionBgColor)
                                            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
                                            .clickable(enabled = !isAnswered) {
                                                selectedIndexLocal = index
                                            }
                                            .padding(16.dp)
                                            .testTag("quiz_option_$index"),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val bubbleColor = if (isSelected || (isAnswered && isCorrect)) {
                                            if (isAnswered && !isCorrect) Color(0xFFEF4444) else Color(0xFF10B981)
                                        } else {
                                            onBgColor.copy(alpha = 0.08f)
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(bubbleColor),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${'A' + index}",
                                                color = if (isSelected || (isAnswered && isCorrect)) Color.White else onBgColor.copy(alpha = 0.8f),
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(16.dp))

                                        Text(
                                            text = option,
                                            color = onBgColor,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Submit & Verification Action button
                            if (!isAnswered) {
                                Button(
                                    onClick = { 
                                        selectedIndexLocal?.let { quizViewModel.submitAnswer(it) }
                                    },
                                    enabled = selectedIndexLocal != null,
                                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .testTag("quiz_submit_button")
                                ) {
                                    Text("Periksa Jawaban 🔎", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                                }
                            } else {
                                Button(
                                    onClick = { quizViewModel.nextQuestion() },
                                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .testTag("quiz_next_button")
                                ) {
                                    val buttonText = if (currentIndex == questions.size - 1) "Selesaikan Ujian 🏆" else "Lanjut Soal Berikutnya ➡️"
                                    Text(buttonText, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
            }
            }
        }
    }
}
