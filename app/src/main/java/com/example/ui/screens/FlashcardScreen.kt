package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AuthViewModel
import com.example.util.SoundManager

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll

data class ChemistryCard(
    val id: String,
    val category: String,
    val front: String,
    val back: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    authViewModel: AuthViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val flashcardsPool = remember {
        com.example.data.dummy.FlashcardsData.getCards()
    }

    val dbStatusList by authViewModel.allFlashcardStatus.collectAsState()

    var activeCategoryIndex by remember { mutableStateOf(0) }
    val categoriesList = remember {
        listOf("Semua") + com.example.data.dummy.FlashcardsData.categories
    }

    var filterOnlyDifficult by remember { mutableStateOf(false) }
    var filterOnlyFavorite by remember { mutableStateOf(false) }
    var filterOnlyUnstudied by remember { mutableStateOf(false) }
    var isShuffled by remember { mutableStateOf(false) }
    var shuffleSeed by remember { mutableStateOf(1) }

    // Map pool to current Room statuses
    val filteredCards = remember(activeCategoryIndex, filterOnlyDifficult, filterOnlyFavorite, filterOnlyUnstudied, isShuffled, shuffleSeed, dbStatusList) {
        val raw = flashcardsPool.filter { card ->
            // Category filter
            val matchesCategory = activeCategoryIndex == 0 || card.category.equals(categoriesList[activeCategoryIndex], ignoreCase = true)
            
            // Database status lookup
            val status = dbStatusList.find { it.cardId == card.id }
            val isDiff = status?.isDifficult ?: false
            val isFav = status?.isFavorite ?: false
            val isUnstudied = status == null || (!status.isDifficult && !status.isFavorite)

            val matchesDifficult = !filterOnlyDifficult || isDiff
            val matchesFavorite = !filterOnlyFavorite || isFav
            val matchesUnstudied = !filterOnlyUnstudied || isUnstudied

            matchesCategory && matchesDifficult && matchesFavorite && matchesUnstudied
        }
        if (isShuffled) {
            raw.shuffled(java.util.Random(shuffleSeed.toLong()))
        } else {
            raw
        }
    }

    var cardIndex by remember { mutableStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    // When filters change, reset position
    LaunchedEffect(filteredCards.size, activeCategoryIndex, filterOnlyDifficult, filterOnlyFavorite, filterOnlyUnstudied, isShuffled) {
        cardIndex = 0
        isFlipped = false
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val onBgColor = MaterialTheme.colorScheme.onBackground

    FuturisticBg(modifier = modifier) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Flashcards Kimia",
                            color = onBgColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
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
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category Filter Carousel
                ScrollableTabRow(
                    selectedTabIndex = activeCategoryIndex,
                    edgePadding = 0.dp,
                    indicator = { tabPositions ->
                        if (activeCategoryIndex < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[activeCategoryIndex]),
                                color = primaryColor
                            )
                        }
                    },
                    containerColor = Color.Transparent,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categoriesList.forEachIndexed { idx, title ->
                        Tab(
                            selected = activeCategoryIndex == idx,
                            onClick = {
                                SoundManager.playClick()
                                activeCategoryIndex = idx
                            },
                            text = { Text(title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp) },
                            selectedContentColor = primaryColor,
                            unselectedContentColor = onBgColor.copy(alpha = 0.5f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Custom filters for Difficult, Favorites, Unstudied, and Shuffling
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterChip(
                        selected = filterOnlyDifficult,
                        onClick = {
                            SoundManager.playClick()
                            filterOnlyDifficult = !filterOnlyDifficult
                            if (filterOnlyDifficult) {
                                filterOnlyUnstudied = false
                            }
                        },
                        label = { Text("Sukar", fontSize = 11.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0x22EF4444),
                            selectedLabelColor = Color(0xFFEF4444),
                            selectedLeadingIconColor = Color(0xFFEF4444)
                        )
                    )

                    FilterChip(
                        selected = filterOnlyFavorite,
                        onClick = {
                            SoundManager.playClick()
                            filterOnlyFavorite = !filterOnlyFavorite
                            if (filterOnlyFavorite) {
                                filterOnlyUnstudied = false
                            }
                        },
                        label = { Text("Favorit", fontSize = 11.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0x22FBBF24),
                            selectedLabelColor = Color(0xFFFBBF24),
                            selectedLeadingIconColor = Color(0xFFFBBF24)
                        )
                    )

                    FilterChip(
                        selected = filterOnlyUnstudied,
                        onClick = {
                            SoundManager.playClick()
                            filterOnlyUnstudied = !filterOnlyUnstudied
                            if (filterOnlyUnstudied) {
                                filterOnlyDifficult = false
                                filterOnlyFavorite = false
                            }
                        },
                        label = { Text("Belum Dipelajari", fontSize = 11.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = primaryColor.copy(alpha = 0.15f),
                            selectedLabelColor = primaryColor,
                            selectedLeadingIconColor = primaryColor
                        )
                    )

                    FilterChip(
                        selected = isShuffled,
                        onClick = {
                            SoundManager.playClick()
                            isShuffled = !isShuffled
                            if (isShuffled) {
                                shuffleSeed += 1
                            }
                        },
                        label = { Text(if (isShuffled) "Teracak" else "Acak Urutan", fontSize = 11.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = secondaryColor.copy(alpha = 0.15f),
                            selectedLabelColor = secondaryColor,
                            selectedLeadingIconColor = secondaryColor
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (filteredCards.isEmpty()) {
                    // Empty list state
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inbox,
                                contentDescription = "Kosong",
                                tint = onBgColor.copy(alpha = 0.2f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tidak ada kartu di sini",
                                color = onBgColor.copy(alpha = 0.5f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Ubah saringan kategori, tandai sulit, atau rilis favorit terlebih dahulu.",
                                color = onBgColor.copy(alpha = 0.35f),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    val activeCard = filteredCards[cardIndex.coerceIn(0, filteredCards.size - 1)]

                    val currentStatus = dbStatusList.find { it.cardId == activeCard.id }
                    val isDifficultLocal = currentStatus?.isDifficult ?: false
                    val isFavoriteLocal = currentStatus?.isFavorite ?: false

                    // Flipped card rotation animation
                    val rotationState by animateFloatAsState(
                        targetValue = if (isFlipped) 180f else 0f,
                        animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
                        label = "card_flip"
                    )

                    // Card visual container
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .graphicsLayer {
                                rotationY = rotationState
                                cameraDistance = 12f * density
                            }
                            .clickable {
                                SoundManager.playClick()
                                isFlipped = !isFlipped
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Glass Card representation
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(24.dp))
                                .background(
                                    if (rotationState > 90f) {
                                        Brush.verticalGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.surfaceVariant,
                                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
                                            )
                                        )
                                    } else {
                                        Brush.verticalGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                                            )
                                        )
                                    }
                                )
                                .border(
                                    width = 1.5.dp,
                                    color = if (rotationState > 90f) {
                                        onBgColor.copy(alpha = 0.12f)
                                    } else {
                                        primaryColor.copy(alpha = 0.3f)
                                    },
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(24.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Card Top area: Category Tag and Action Toggles
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .graphicsLayer {
                                            if (rotationState > 90f) {
                                                rotationY = 180f
                                            }
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Custom badge indicating category
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(primaryColor.copy(alpha = 0.12f))
                                            .border(1.dp, primaryColor.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            activeCard.category.uppercase(),
                                            color = primaryColor,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // Action bar: Favorite & Mark as difficult
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Mark as Difficult
                                        IconButton(
                                            onClick = {
                                                SoundManager.playClick()
                                                authViewModel.updateFlashcardStatus(
                                                    cardId = activeCard.id,
                                                    isDifficult = !isDifficultLocal,
                                                    isFavorite = isFavoriteLocal
                                                )
                                            },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isDifficultLocal) Icons.Default.Warning else Icons.Default.WarningAmber,
                                                contentDescription = "Tandai Sulit",
                                                tint = if (isDifficultLocal) Color(0xFFFF5252) else onBgColor.copy(alpha = 0.35f),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }

                                        // Mark as Favorite
                                        IconButton(
                                            onClick = {
                                                SoundManager.playClick()
                                                authViewModel.updateFlashcardStatus(
                                                    cardId = activeCard.id,
                                                    isDifficult = isDifficultLocal,
                                                    isFavorite = !isFavoriteLocal
                                                )
                                            },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (isFavoriteLocal) Icons.Default.Star else Icons.Default.StarBorder,
                                                contentDescription = "Favorit",
                                                tint = if (isFavoriteLocal) Color(0xFFFFD700) else onBgColor.copy(alpha = 0.35f),
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }

                                // Card Center area: Text Content
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .graphicsLayer {
                                            if (rotationState > 90f) {
                                                rotationY = 180f
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (rotationState > 90f) {
                                        // BACK VIEW
                                        Text(
                                            text = activeCard.back,
                                            color = onBgColor,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 26.sp
                                        )
                                    } else {
                                        // FRONT VIEW
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = activeCard.front,
                                                color = onBgColor,
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                lineHeight = 32.sp
                                            )
                                            Spacer(modifier = Modifier.height(14.dp))
                                            Text(
                                                "Sentuh untuk membalik kartu",
                                                color = primaryColor.copy(alpha = 0.5f),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }

                                // Card Bottom area: Tracker index
                                Text(
                                    text = "KLIK UNTUK MEMBALIK • ${cardIndex + 1} / ${filteredCards.size}",
                                    color = onBgColor.copy(alpha = 0.3f),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.graphicsLayer {
                                        if (rotationState > 90f) {
                                            rotationY = 180f
                                        }
                                    }
                                )
                            }
                        }
                    }

                    // Navigation Controller Carousel Buttons (Previous & Next)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (cardIndex > 0) {
                                    SoundManager.playClick()
                                    cardIndex--
                                    isFlipped = false
                                }
                            },
                            enabled = cardIndex > 0,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (cardIndex > 0) primaryColor.copy(alpha = 0.1f) else Color.Transparent
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Sebelumnya",
                                tint = if (cardIndex > 0) primaryColor else onBgColor.copy(alpha = 0.15f)
                            )
                        }

                        Text(
                            text = "Geser dengan Tombol",
                            color = onBgColor.copy(alpha = 0.35f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )

                        IconButton(
                            onClick = {
                                if (cardIndex < filteredCards.size - 1) {
                                    SoundManager.playClick()
                                    cardIndex++
                                    isFlipped = false
                                }
                            },
                            enabled = cardIndex < filteredCards.size - 1,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (cardIndex < filteredCards.size - 1) primaryColor.copy(alpha = 0.1f) else Color.Transparent
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Berikutnya",
                                tint = if (cardIndex < filteredCards.size - 1) primaryColor else onBgColor.copy(alpha = 0.15f)
                            )
                        }
                    }
                }
            }
        }
    }
}
