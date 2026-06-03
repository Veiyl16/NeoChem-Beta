package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
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
import com.example.data.dummy.PeriodicTableData
import com.example.data.model.ChemicalElement
import com.example.ui.theme.*
import com.example.ui.viewmodel.PeriodicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodicTableScreen(
    periodicViewModel: PeriodicViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val searchQuery by periodicViewModel.searchQuery.collectAsState()
    val selectedCategory by periodicViewModel.selectedCategory.collectAsState()
    val filteredElements by periodicViewModel.filteredElements.collectAsState()
    val favorites by periodicViewModel.favoriteElements.collectAsState()

    var activeDetailElement by remember { mutableStateOf<ChemicalElement?>(null) }

    val favIdsBySet = remember(favorites) { favorites.map { it.atomicNumber }.toSet() }
    val lowPerf by com.example.data.local.NeoChemSettings.lowPerformanceMode.collectAsState()

    val categoriesList = listOf(
        "Alkali",
        "Alkali tanah",
        "Logam transisi",
        "Metaloid",
        "Nonlogam",
        "Halogen",
        "Gas mulia",
        "Aktinida",
        "Favorit"
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val onBgColor = MaterialTheme.colorScheme.onBackground
    val cardBgColor = MaterialTheme.colorScheme.surfaceVariant

    FuturisticBg(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // TITLE AND BACK BUTTON
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                NeoChemBackButton(onClick = onBack)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Tabel Periodik Unsur",
                        color = onBgColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Eksplorasi cerdas dari Hidrogen ke Oganesson",
                        color = onBgColor.copy(alpha = 0.55f),
                        fontSize = 11.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SEARCH BAR
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { periodicViewModel.setSearchQuery(it) },
                placeholder = {
                    Text(
                        text = "Cari unsur (cth: H, Karbon, 6)...",
                        color = onBgColor.copy(alpha = 0.5f),
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Cari",
                        tint = primaryColor
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = onBgColor,
                    unfocusedTextColor = onBgColor,
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = onBgColor.copy(alpha = 0.15f),
                    focusedContainerColor = cardBgColor.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("periodic_search_input")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // FILTER CATEGORIES
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    val isAllSelected = selectedCategory == null
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(if (isAllSelected) primaryColor else cardBgColor.copy(alpha = 0.6f))
                            .border(1.dp, if (isAllSelected) primaryColor else onBgColor.copy(alpha = 0.12f), RoundedCornerShape(30.dp))
                            .clickable { periodicViewModel.selectCategory(null) }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Semua",
                            color = if (isAllSelected) MaterialTheme.colorScheme.onPrimary else onBgColor.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }

                items(categoriesList) { cat ->
                    val isSelected = selectedCategory == cat
                    val catColor = if (cat == "Favorit") Color(0xE6E11D48) else Color(PeriodicTableData.getCategoryColor(cat))
                    
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(if (isSelected) catColor else cardBgColor.copy(alpha = 0.6f))
                            .border(1.dp, if (isSelected) catColor else onBgColor.copy(alpha = 0.12f), RoundedCornerShape(30.dp))
                            .clickable { periodicViewModel.selectCategory(cat) }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) Color.White else onBgColor.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // MAIN PERIODIC TABLE VIEWER (GRID VIEW FOR ALL SCREENS COMPLIANCE)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (filteredElements.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("🔍", fontSize = 44.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Unsur Kimia Tidak Ditemukan",
                            color = onBgColor.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Coba kata kunci pencarian atau filter kategori lainnya",
                            color = onBgColor.copy(alpha = 0.5f),
                            fontSize = 11.sp
                        )
                    }
                } else {
                    // Optimized LazyVerticalGrid showing scrollable compact elements list representation
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 72.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("periodic_grid")
                    ) {
                        items(
                            items = filteredElements,
                            key = { it.number }
                        ) { element ->
                            val colorHex = PeriodicTableData.getCategoryColor(element.category)
                            val elementColor = Color(colorHex)
                            val isFav = element.number in favIdsBySet

                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        elementColor.copy(alpha = 0.12f)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = elementColor.copy(alpha = 0.45f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        com.example.util.SoundManager.playClick()
                                        activeDetailElement = element
                                    }
                                    .padding(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = element.number.toString(),
                                            color = onBgColor.copy(alpha = 0.5f),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        if (isFav) {
                                            Icon(
                                                 imageVector = Icons.Default.Favorite,
                                                 contentDescription = "Favorit",
                                                 tint = Color(0xFFEF4444),
                                                 modifier = Modifier.size(10.dp)
                                             )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier.weight(1f).fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = element.symbol,
                                            color = onBgColor,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 18.sp
                                        )
                                    }

                                    Text(
                                        text = element.name,
                                        color = onBgColor.copy(alpha = 0.55f),
                                        fontSize = 8.sp,
                                        maxLines = 1,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // ELEMENT DETAIL SHEET MODAL-DIALOG POPUP
        val element = activeDetailElement
        if (element != null) {
            val categoryColor = Color(PeriodicTableData.getCategoryColor(element.category))
            val isFavoriteElement = favorites.any { it.atomicNumber == element.number }

            AlertDialog(
                onDismissRequest = { activeDetailElement = null },
                confirmButton = {
                    TextButton(onClick = { activeDetailElement = null }) {
                        Text("Tutup", color = primaryColor, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    IconButton(
                        onClick = {
                            periodicViewModel.toggleFavorite(element.number)
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavoriteElement) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Suka",
                            tint = if (isFavoriteElement) Color(0xE6E11D48) else onBgColor.copy(alpha = 0.5f)
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(categoryColor.copy(alpha = 0.2f))
                                .border(1.5.dp, categoryColor, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = element.symbol,
                                color = onBgColor,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = element.name,
                                color = onBgColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "${element.category} | Nomor ${element.number}",
                                color = categoryColor,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Properties Grid Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DetailStatBox(label = "Massa", value = "${element.mass} u", modifier = Modifier.weight(1f))
                            DetailStatBox(label = "Fase", value = element.phase, modifier = Modifier.weight(1f))
                            DetailStatBox(label = "Valensi", value = "${element.valenceElectrons}", modifier = Modifier.weight(1f))
                        }

                        DetailStatBoxLong(label = "Konfigurasi Elektron", value = element.electronConfig)
                        DetailStatBoxLong(label = "Penemu & Tahun", value = "${element.discoveredBy} (${element.discoveredYear})")
                        DetailStatBoxLong(label = "Aplikasi Praktis", value = element.uses)

                        // Cool facts text
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(categoryColor.copy(alpha = 0.08f))
                                .border(1.dp, categoryColor.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Fakta Unik ✨",
                                    color = categoryColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = element.interestingFact,
                                    color = onBgColor.copy(alpha = 0.75f),
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                textContentColor = onBgColor,
                titleContentColor = onBgColor,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.border(1.5.dp, categoryColor.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
            )
        }
    }
}

@Composable
fun DetailStatBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val onBgColor = MaterialTheme.colorScheme.onBackground
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(onBgColor.copy(alpha = 0.03f))
            .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(10.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, color = onBgColor.copy(alpha = 0.5f), fontSize = 10.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }
    }
}

@Composable
fun DetailStatBoxLong(
    label: String,
    value: String
) {
    val onBgColor = MaterialTheme.colorScheme.onBackground
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(onBgColor.copy(alpha = 0.03f))
            .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(text = label, color = onBgColor.copy(alpha = 0.5f), fontSize = 10.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, color = onBgColor, fontWeight = FontWeight.Medium, fontSize = 12.sp)
        }
    }
}
