package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.util.SoundManager

data class Reactant(
    val formula: String,
    val name: String,
    val phase: String, // (aq), (l), (s), (g)
    val colorAccent: Color
)

data class ReactionResult(
    val rA: String,
    val rB: String,
    val equation: String,
    val reactionName: String,
    val type: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JikaDicampurScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val reactantsPool = remember {
        listOf(
            Reactant("HCl", "Asam Klorida", "(aq)", Color(0xFFEF4444)),
            Reactant("NaOH", "Natrium Hidroksida", "(aq)", Color(0xFF3B82F6)),
            Reactant("Na", "Logam Natrium", "(s)", Color(0xFFC084FC)),
            Reactant("H₂O", "Air Murni", "(l)", Color(0xFF2DD4BF)),
            Reactant("O₂", "Gas Oksigen", "(g)", Color(0xFFFBBF24)),
            Reactant("CH₄", "Gas Metana", "(g)", Color(0xFF10B981)),
            Reactant("NH₃", "Gas Amonia", "(g)", Color(0xFFEC4899)),
            Reactant("CH₃COOH", "Asam Asetat (Cuka)", "(aq)", Color(0xFFF97316)),
            Reactant("CaCO₃", "Kalsium Karbonat (Kapur)", "(s)", Color(0xFF94A3B8))
        )
    }

    val reactions = remember {
        listOf(
            ReactionResult(
                rA = "HCl", rB = "NaOH",
                equation = "HCl(aq) + NaOH(aq) → NaCl(aq) + H₂O(l)",
                reactionName = "Reaksi Netralisasi (Penggaraman)",
                type = "Reaksi Penetralan Asam-Basa",
                description = "Asam klorida kuat bereaksi sangat hangat dengan basa kuat natrium hidroksida menghasilkan garam dapur biasa (Natrium Klorida) yang netral dan air cair murni.",
                icon = Icons.Default.WaterDrop,
                color = Color(0xFF3B82F6)
            ),
            ReactionResult(
                rA = "NaOH", rB = "HCl",
                equation = "HCl(aq) + NaOH(aq) → NaCl(aq) + H₂O(l)",
                reactionName = "Reaksi Netralisasi (Penggaraman)",
                type = "Reaksi Penetralan Asam-Basa",
                description = "Asam klorida kuat bereaksi sangat hangat dengan basa kuat natrium hidroksida menghasilkan garam dapur (Natrium Klorida) dan air murni.",
                icon = Icons.Default.WaterDrop,
                color = Color(0xFF3B82F6)
            ),
            ReactionResult(
                rA = "Na", rB = "H₂O",
                equation = "2Na(s) + 2H₂O(l) → 2NaOH(aq) + H₂(g) ↑",
                reactionName = "Reaksi Eksoterm Hebat Alkali",
                type = "Reaksi Redoks & Eksotermik",
                description = "Natrium bereaksi secara dahsyat meledak-ledak di permukaan air. Melepaskan gas hidrogen mudah terbakar dan menghasilkan larutan basa natrium hidroksida yang sangat panas.",
                icon = Icons.Default.LocalFireDepartment,
                color = Color(0xFFEF4444)
            ),
            ReactionResult(
                rA = "H₂O", rB = "Na",
                equation = "2Na(s) + 2H₂O(l) → 2NaOH(aq) + H₂(g) ↑",
                reactionName = "Reaksi Eksoterm Hebat Alkali",
                type = "Reaksi Redoks / Eksoterm",
                description = "Natrium bereaksi secara dahsyat meledak-ledak di permukaan air. Melepaskan gas hidrogen mudah terbakar dan menghasilkan larutan basa natrium hidroksida yang sangat panas.",
                icon = Icons.Default.LocalFireDepartment,
                color = Color(0xFFEF4444)
            ),
            ReactionResult(
                rA = "CH₄", rB = "O₂",
                equation = "CH₄(g) + 2O₂(g) → CO₂(g) + 2H₂O(g) + Energi",
                reactionName = "Pembakaran Sempurna Metana",
                type = "Reaksi Pembakaran",
                description = "Gas metana bereaksi dengan gas oksigen pada suhu pengapian menghasilkan nyala api biru bersih, melepaskan gas karbondioksida, uap air, dan energi kalor yang melimpah.",
                icon = Icons.Default.Whatshot,
                color = Color(0xFF10B981)
            ),
            ReactionResult(
                rA = "O₂", rB = "CH₄",
                equation = "CH₄(g) + 2O₂(g) → CO₂(g) + 2H₂O(g) + Energi",
                reactionName = "Pembakaran Sempurna Metana",
                type = "Reaksi Pembakaran",
                description = "Gas metana bereaksi dengan gas oksigen pada suhu pengapian menghasilkan nyala api biru bersih, melepaskan gas karbondioksida, uap air, dan energi kalor yang melimpah.",
                icon = Icons.Default.Whatshot,
                color = Color(0xFF10B981)
            ),
            ReactionResult(
                rA = "NH₃", rB = "HCl",
                equation = "NH₃(g) + HCl(aq) → NH₄Cl(s)",
                reactionName = "Sintesis Garam Amonium Klorida",
                type = "Reaksi Kombinasi / Sintesis",
                description = "Gas amonia dan uap asam klorida bereaksi langsung membentuk asap putih tebal yang mengendap menjadi kristal halus amonium klorida mirip seperti salju.",
                icon = Icons.Default.Cloud,
                color = Color(0xFFC084FC)
            ),
            ReactionResult(
                rA = "HCl", rB = "NH₃",
                equation = "NH₃(g) + HCl(aq) → NH₄Cl(s)",
                reactionName = "Sintesis Garam Amonium Klorida",
                type = "Reaksi Kombinasi / Sintesis",
                description = "Gas amonia dan uap asam klorida bereaksi langsung membentuk asap putih tebal yang mengendap menjadi kristal amonium klorida.",
                icon = Icons.Default.Cloud,
                color = Color(0xFFC084FC)
            ),
            ReactionResult(
                rA = "CH₃COOH", rB = "NaOH",
                equation = "CH₃COOH(aq) + NaOH(aq) → CH₃COONa(aq) + H₂O(l)",
                reactionName = "Reaksi Asam Lemah dan Basa Kuat",
                type = "Reaksi Penetralan",
                description = "Asam cuka lemah menyeimbangkan alkalinitas soda api menghasilkan larutan penyangga garam natrium asetat cair dan air.",
                icon = Icons.Default.Science,
                color = Color(0xFFF97316)
            ),
            ReactionResult(
                rA = "NaOH", rB = "CH₃COOH",
                equation = "CH₃COOH(aq) + NaOH(aq) → CH₃COONa(aq) + H₂O(l)",
                reactionName = "Reaksi Asam Lemah dan Basa Kuat",
                type = "Reaksi Penetralan",
                description = "Asam cuka lemah menyeimbangkan alkalinitas soda api menghasilkan larutan garam natrium asetat cair dan air.",
                icon = Icons.Default.Science,
                color = Color(0xFFF97316)
            ),
            ReactionResult(
                rA = "HCl", rB = "CaCO₃",
                equation = "2HCl(aq) + CaCO₃(s) → CaCl₂(aq) + H₂O(l) + CO₂(g) ↑",
                reactionName = "Reaksi Pembebasan Gas Karbonat",
                type = "Reaksi Dekomposisi / Penguraian",
                description = "Asam klorida encer menuangkan korosinya ke permukaan kapur melepaskan kalsium klorida cair, air, disertai buih gelembung karbon dioksida gas yang aktif meluap.",
                icon = Icons.Default.BubbleChart,
                color = Color(0xFF94A3B8)
            ),
            ReactionResult(
                rA = "CaCO₃", rB = "HCl",
                equation = "2HCl(aq) + CaCO₃(s) → CaCl₂(aq) + H₂O(l) + CO₂(g) ↑",
                reactionName = "Reaksi Pembebasan Gas Karbonat",
                type = "Reaksi Dekomposisi / Penguraian",
                description = "Asam klorida menggigit kalsium karbonat menghasilkan larutan kalsium klorida jernih, air, dan gas karbon dioksida berbusa riuh.",
                icon = Icons.Default.BubbleChart,
                color = Color(0xFF94A3B8)
            )
        )
    }

    var selectedIndexA by remember { mutableStateOf(-1) }
    var selectedIndexB by remember { mutableStateOf(-1) }

    var expandedDropdownA by remember { mutableStateOf(false) }
    var expandedDropdownB by remember { mutableStateOf(false) }

    val reactionReport = remember(selectedIndexA, selectedIndexB) {
        if (selectedIndexA == -1 || selectedIndexB == -1) {
            null
        } else {
            val fA = reactantsPool[selectedIndexA].formula
            val fB = reactantsPool[selectedIndexB].formula
            
            reactions.find { it.rA == fA && it.rB == fB }
        }
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
                            "Jika Dicampur...",
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
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // Lab flask beaker graphic
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(primaryColor.copy(alpha = 0.08f))
                        .border(1.5.dp, primaryColor.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Science,
                        contentDescription = "Beaker",
                        tint = primaryColor,
                        modifier = Modifier.size(54.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ayo Campurkan Zat Kimia!",
                    color = onBgColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Pilih Zat A dan Zat B di bawah ini untuk melihat reaksi yang terjadi secara teoritis.",
                    color = onBgColor.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Reactant Selection Controls
                Column(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Reactant A Selector Card
                    Column {
                        Text("Pilih Zat Reaktan 1 (A):", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(onBgColor.copy(alpha = 0.03f))
                                .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .clickable {
                                    SoundManager.playClick()
                                    expandedDropdownA = true
                                }
                                .padding(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (selectedIndexA == -1) {
                                    Text("Klik untuk memilih Zat Reaktan A...", color = onBgColor.copy(alpha = 0.4f), fontSize = 14.sp)
                                } else {
                                    val reactant = reactantsPool[selectedIndexA]
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(reactant.colorAccent)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("${reactant.formula} ${reactant.phase} — ${reactant.name}", color = onBgColor, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = primaryColor)
                            }

                            DropdownMenu(
                                expanded = expandedDropdownA,
                                onDismissRequest = { expandedDropdownA = false },
                                modifier = Modifier.fillMaxWidth(0.85f)
                            ) {
                                reactantsPool.forEachIndexed { index, reactant ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(reactant.colorAccent))
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text("${reactant.formula} — ${reactant.name}")
                                            }
                                        },
                                        onClick = {
                                            SoundManager.playClick()
                                            selectedIndexA = index
                                            expandedDropdownA = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Plus separator
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(primaryColor)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Ditambah",
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    // Reactant B Selector Card
                    Column {
                        Text("Pilih Zat Reaktan 2 (B):", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(onBgColor.copy(alpha = 0.03f))
                                .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                                .clickable {
                                    SoundManager.playClick()
                                    expandedDropdownB = true
                                }
                                .padding(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (selectedIndexB == -1) {
                                    Text("Klik untuk memilih Zat Reaktan B...", color = onBgColor.copy(alpha = 0.4f), fontSize = 14.sp)
                                } else {
                                    val reactant = reactantsPool[selectedIndexB]
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(reactant.colorAccent)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("${reactant.formula} ${reactant.phase} — ${reactant.name}", color = onBgColor, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = primaryColor)
                            }

                            DropdownMenu(
                                expanded = expandedDropdownB,
                                onDismissRequest = { expandedDropdownB = false },
                                modifier = Modifier.fillMaxWidth(0.85f)
                            ) {
                                reactantsPool.forEachIndexed { index, reactant ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(reactant.colorAccent))
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text("${reactant.formula} — ${reactant.name}")
                                            }
                                        },
                                        onClick = {
                                            SoundManager.playClick()
                                            selectedIndexB = index
                                            expandedDropdownB = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Reaction Report Panel
                if (reactionReport != null) {
                    val report = reactionReport
                    LaunchedEffect(report) {
                        SoundManager.playCorrect() // Play soft reactive bip
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(report.color.copy(alpha = 0.04f))
                            .border(1.5.dp, report.color.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(report.color.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(report.icon, contentDescription = "Tipe", tint = report.color, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(report.reactionName, color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(report.type.uppercase(), color = report.color, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Balanced Molecular Equation Card
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(onBgColor.copy(alpha = 0.04f))
                                .padding(12.dp)
                        ) {
                            Text("Persamaan Reaksi Setara:", color = onBgColor.copy(alpha = 0.4f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            ChemistryText(report.equation, color = report.color, fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Description
                        Text("Fenomena & Penjelasan:", color = onBgColor.copy(alpha = 0.45f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = report.description,
                            color = onBgColor,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                } else if (selectedIndexA != -1 && selectedIndexB != -1) {
                    // Selected both but they don't react
                    LaunchedEffect(selectedIndexA, selectedIndexB) {
                        SoundManager.playIncorrect() // Play duller indicator bip
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(onBgColor.copy(alpha = 0.02f))
                            .border(1.dp, onBgColor.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Rilis",
                            tint = onBgColor.copy(alpha = 0.4f),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Tidak Bereaksi Langsung",
                            color = onBgColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Kedua zat yaitu ${reactantsPool[selectedIndexA].formula} dan ${reactantsPool[selectedIndexB].formula} tidak melakukan reaksi kimia berenergi tunggal atau spontan pada kondisi kamar normal.",
                            color = onBgColor.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
