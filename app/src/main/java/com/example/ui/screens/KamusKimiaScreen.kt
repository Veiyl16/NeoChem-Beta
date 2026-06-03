package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.util.SoundManager

data class DictionaryTerm(
    val word: String,
    val definition: String,
    val formulaOrStructure: String = "",
    val categoryOrGroup: String = "",
    val uses: String = "",
    val relatedMateri: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KamusKimiaScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dictionaryData = remember {
        listOf(
            DictionaryTerm(
                word = "Entalpi",
                definition = "Sifat termodinamika yang menyatakan jumlah energi internal suatu sistem ditambah hasil kali tekanan dan volumenya. Digunakan untuk menghitung jumlah energi/kalor yang diserap atau dilepas selama reaksi.",
                formulaOrStructure = "H = U + P × V\nΔH = Qₚ (pada tekanan konstan)",
                categoryOrGroup = "Termokimia / Termodinamika",
                relatedMateri = "Asam-Basa, Termokimia"
            ),
            DictionaryTerm(
                word = "Benzena",
                definition = "Senyawa organik hidrokarbon aromatik yang tersusun dalam cincin heksagonal melingkar stabil. Memiliki enam atom karbon yang beresonansi dengan elektron pi terdelokalisasi.",
                formulaOrStructure = "C₆H₆ (Cincin Pelana)",
                categoryOrGroup = "Hidrokarbon Aromatis",
                uses = "Bahan dasar pembuatan plastik, serat nilon, karet sintetis, obat-obatan, pewarna, dan detergen."
            ),
            DictionaryTerm(
                word = "Stoikiometri",
                definition = "Cabang ilmu kimia yang mempelajari tentang hubungan kuantitatif (perhitungan matematis) antara zat-zat pereaksi (reaktan) dan hasil reaksi (produk) berdasarkan hukum-hukum dasar kimia.",
                formulaOrStructure = "Mol A × (Koefisien B / Koefisien A) = Mol B",
                categoryOrGroup = "Kimia Dasar",
                relatedMateri = "Stoikiometri & Konsep Mol"
            ),
            DictionaryTerm(
                word = "Katalis",
                definition = "Zat kimia yang mempercepat laju reaksi dengan menurunkan energi aktivasi reaksi tanpa membuat dirinya terkonsumsi secara kekal dalam reaksi tersebut.",
                formulaOrStructure = "Reaktan + Katalis → Intermediet → Produk + Katalis",
                categoryOrGroup = "Kinetika Kimia",
                uses = "Penggunaan catalytic converter kendaraan, pembuatan amonia proses Haber-Bosch, dan fermentasi enzimatis."
            ),
            DictionaryTerm(
                word = "Mol",
                definition = "Satuan jumlah zat dalam kimia. Satu mol menyatakan jumlah zat yang mengandung partikel dasar (atom/molekul/ion) sebanyak bilangan Avogadro yaitu 6.022 × 10²³ partikel.",
                formulaOrStructure = "n = massa (gr) / Mr  atau  n = Jumlah Partikel / 6.022 × 10²³",
                categoryOrGroup = "Kimia Analitik",
                relatedMateri = "Stoikiometri & Konsep Mol"
            ),
            DictionaryTerm(
                word = "Elektronegativitas",
                definition = "Kecenderungan relatif suatu atom untuk menarik pasangan elektron ikatan ke arah dirinya dalam membentuk unit ikatan kovalen.",
                formulaOrStructure = "Skala Pauling (Fluorin memiliki nilai tertinggi: 4.0)",
                categoryOrGroup = "Sifat Periodik Unsur",
                relatedMateri = "Struktur Atom & Tabel Periodik"
            ),
            DictionaryTerm(
                word = "Eksoterm",
                definition = "Reaksi kimia yang menghasilkan atau menghasilkan pelepasan kalor (panas) dari sistem ke arah lingkungan sekitar.",
                formulaOrStructure = "ΔH = Hₚᵣₒ_dᵤ_c_t - Hᵣ_e_a_c_t_a_n_t < 0 (Nilai ΔH Negatif)",
                categoryOrGroup = "Termokimia",
                relatedMateri = "Laju Reaksi & Termokimia"
            ),
            DictionaryTerm(
                word = "Endoterm",
                definition = "Reaksi kimia yang membutuhkan atau menyerap energi panas (kalor) dari lingkungan luar ke dalam sistem agar reaksi bisa terjadi.",
                formulaOrStructure = "ΔH = Hₚᵣₒ_dᵤ_c_t - Hᵣ_e_a_c_t_a_n_t > 0 (Nilai ΔH Positif)",
                categoryOrGroup = "Termokimia",
                relatedMateri = "Termokimia Dasar"
            ),
            DictionaryTerm(
                word = "Etanol",
                definition = "Senyawa golongan alkanal/alkohol rantai lurus dua karbon yang berwujud cair jernih mudah menguap pada suhu kamar.",
                formulaOrStructure = "C₂H₅OH (Gugus fungsi -OH)",
                categoryOrGroup = "Alkohol (Alkanol)",
                uses = "Pelarut organik umum, bahan bakar alternatif (bioetanol), antiseptik sterilisasi medis (kadar 70%)."
            ),
            DictionaryTerm(
                word = "Kondensasi",
                definition = "Reaksi penggabungan dua atau lebih molekul kecil membentuk satu senyawa besar dengan melepaskan molekul kecil sisa seperti H₂O atau HCl.",
                formulaOrStructure = "R-H + HO-R' → R-R' + H₂O",
                categoryOrGroup = "Mekanisme Reaksi Organik",
                relatedMateri = "Polimer & Makromolekul"
            ),
            DictionaryTerm(
                word = "Asam Klorida (HCl)",
                definition = "Asam anorganik kuat yang sangat korosif, berbau menyengat, larut dalam air, dan terionisasi sempurna menjadi ion hidronium dan klorida.",
                formulaOrStructure = "HCl(aq) → H⁺(aq) + Cl⁻(aq)",
                categoryOrGroup = "Asam Kuat / Halida",
                uses = "Pembersih kerak logam industrial, pengatur pH kolam, komponen utama cairan lambung mamalia."
            ),
            DictionaryTerm(
                word = "Oksidasi",
                definition = "Reaksi kimia yang ditandai dengan peningkatan bilangan oksidasi (biloks), pelepasan elektron oleh suatu atom/molekul, atau pengikatan gas oksigen.",
                formulaOrStructure = "Logam besi karat: Fe → Fe²⁺ + 2e⁻",
                categoryOrGroup = "Redoks (Reduksi-Oksidasi)",
                relatedMateri = "Elektrokimia & Reaksi Redoks"
            ),
            DictionaryTerm(
                word = "Reduksi",
                definition = "Reaksi kimia yang ditandai dengan penurunan bilangan oksidasi (biloks), penerimaan/penangkapan elektron bebas, atau pelepasan gas oksigen.",
                formulaOrStructure = "Cl₂ + 2e⁻ → 2Cl⁻",
                categoryOrGroup = "Redoks (Reduksi-Oksidasi)",
                relatedMateri = "Elektrokimia "
            ),
            DictionaryTerm(
                word = "Buffer (Penyangga)",
                definition = "Larutan yang mengandung campuran asam lemah dengan basa konjugasinya (atau sebaliknya) untuk menangkal perubahan pH drastis saat terjadi interferensi zat luar.",
                formulaOrStructure = "pH = pKa + log([Garam]/[Asam]) (Hukum Henderson-Hasselbalch)",
                categoryOrGroup = "Larutan Air",
                relatedMateri = "Asam-Basa & KesetimbanganLarutan"
            )
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    val filteredEntries = remember(searchQuery) {
        if (searchQuery.trim().isEmpty()) {
            dictionaryData
        } else {
            dictionaryData.filter {
                it.word.contains(searchQuery, ignoreCase = true) ||
                it.definition.contains(searchQuery, ignoreCase = true) ||
                it.categoryOrGroup.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    // List tracking expanded status for dictionary cards
    var expandedWord by remember { mutableStateOf<String?>(null) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val onBgColor = MaterialTheme.colorScheme.onBackground

    FuturisticBg(modifier = modifier) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Kamus Kimia",
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
            ) {
                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Cari istilah... (misal: Entalpi, Benzena)", color = onBgColor.copy(alpha = 0.4f), fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = primaryColor
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = onBgColor.copy(alpha = 0.4f))
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = onBgColor.copy(alpha = 0.12f),
                        focusedTextColor = onBgColor,
                        unfocusedTextColor = onBgColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dictionary_search_input")
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Menampilkan ${filteredEntries.size} Hasil",
                    color = onBgColor.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (filteredEntries.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = "Tidak ditemukan",
                                tint = onBgColor.copy(alpha = 0.2f),
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Istilah tidak ditemukan",
                                color = onBgColor.copy(alpha = 0.5f),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Coba cari dengan kata kunci lain.",
                                color = onBgColor.copy(alpha = 0.35f),
                                fontSize = 12.sp
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredEntries, key = { it.word }) { entry ->
                            val isExpanded = expandedWord == entry.word

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(onBgColor.copy(alpha = 0.03f))
                                    .border(
                                        1.dp,
                                        if (isExpanded) primaryColor.copy(alpha = 0.3f) else onBgColor.copy(alpha = 0.08f),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        SoundManager.playClick()
                                        expandedWord = if (isExpanded) null else entry.word
                                    }
                                    .padding(14.dp)
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = entry.word,
                                                color = onBgColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = entry.categoryOrGroup.uppercase(),
                                                color = primaryColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 9.sp
                                            )
                                        }

                                        Icon(
                                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                            contentDescription = "Expand",
                                            tint = primaryColor
                                        )
                                    }

                                    AnimatedVisibility(
                                        visible = isExpanded,
                                        enter = expandVertically() + fadeIn(),
                                        exit = shrinkVertically() + fadeOut()
                                    ) {
                                        Column(modifier = Modifier.padding(top = 12.dp)) {
                                            // Definition Section
                                            Text("Definisi:", color = onBgColor.copy(alpha = 0.45f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                            Text(entry.definition, color = onBgColor, fontSize = 13.sp, lineHeight = 18.sp)

                                            Spacer(modifier = Modifier.height(10.dp))

                                            // Formula / Structure Section
                                            if (entry.formulaOrStructure.isNotEmpty()) {
                                                Text("Struktur & Rumus:", color = onBgColor.copy(alpha = 0.45f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(onBgColor.copy(alpha = 0.04f))
                                                        .padding(10.dp)
                                                        .clip(RoundedCornerShape(6.dp))
                                                ) {
                                                    Text(entry.formulaOrStructure, color = secondaryColor, fontSize = 13.sp, fontWeight = FontWeight.Bold, lineHeight = 18.sp)
                                                }
                                                Spacer(modifier = Modifier.height(10.dp))
                                            }

                                            // Uses Section
                                            if (entry.uses.isNotEmpty()) {
                                                Text("Kegunaan & Manfaat:", color = onBgColor.copy(alpha = 0.45f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                                Text(entry.uses, color = onBgColor, fontSize = 13.sp, lineHeight = 18.sp)
                                                Spacer(modifier = Modifier.height(10.dp))
                                            }

                                            // Related topics Section
                                            if (entry.relatedMateri.isNotEmpty()) {
                                                Text("Materi Terkait:", color = onBgColor.copy(alpha = 0.45f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                                Text(entry.relatedMateri, color = primaryColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
    }
}
