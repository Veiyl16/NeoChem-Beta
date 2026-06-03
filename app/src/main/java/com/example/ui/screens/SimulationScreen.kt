package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.SimulationViewModel
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.pointerInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulationScreen(
    simulationViewModel: SimulationViewModel,
    onBack: () -> Unit,
    onSimulationComplete: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(0) } // 0: Atom Builder, 1: Bonding, 2: Hydrocarbon, 3: Multi-Reactor
    val tabs = listOf("Atom Lab", "Bonding Lab", "Hydrocarbon", "Multi-Reactor")

    FuturisticBg(modifier = modifier) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Chemical Reactor Simulator", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 18.sp)
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
                // TAB BAR CAPSULES
                TabRow(
                    selectedTabIndex = activeTab,
                    containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                    indicator = { Box(Modifier.fillMaxSize()) }, // Hide standard indicator
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .padding(4.dp)
                        .testTag("simulation_tabs")
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = activeTab == index
                        val tabBg = if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
                        val tabText = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(tabBg)
                                .clickable { activeTab = index }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                color = tabText,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // CURRENT INTERACTIVE MODULE VIEWER
                Box(modifier = Modifier.weight(1f)) {
                    when (activeTab) {
                        0 -> AtomBuilderTab(simulationViewModel)
                        1 -> BondingLabTab(simulationViewModel)
                        2 -> HydrocarbonTab(simulationViewModel) { onSimulationComplete("hydrocarbon") }
                        else -> AdvancedReactorTab(simulationViewModel) { onSimulationComplete("reactor") }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// --- TAB 1: INTERACTIVE ATOM BUILDER COMPOSABLE ---
@Composable
fun AtomBuilderTab(viewModel: SimulationViewModel) {
    var selectedCompound by remember { mutableStateOf("H2O") } // "H2O", "CH4", "C2H4", "Kustom"
    var rotationAngle by remember { mutableStateOf(45f) }
    var zoomScale by remember { mutableStateOf(1.0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Compound Selection Chips (Auto-build)
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Pilih Senyawa / Unsur (Auto Build):",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val compounds = listOf("H2O", "CH4", "C2H4", "Kustom")
                val displayLabels = listOf("H₂O (Air)", "CH₄ (Metana)", "C₂H₄ (Etena)", "Kustom 🔬")
                compounds.forEachIndexed { index, name ->
                    val isSelected = selectedCompound == name
                    val buttonColor = when (name) {
                        "H2O" -> Color(0xFF2DD4BF)
                        "CH4" -> Color(0xFF10B981)
                        "C2H4" -> Color(0xFF8B5CF6)
                        else -> MaterialTheme.colorScheme.primary
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) buttonColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                            .clickable { selectedCompound = name }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = displayLabels[index],
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedCompound == "Kustom") {
            // ORIGINAL KUSTOM PARTICLE MODEL CONTENT
            val protons by viewModel.protons.collectAsState()
            val neutrons by viewModel.neutrons.collectAsState()
            val electrons by viewModel.electrons.collectAsState()

            val info = viewModel.getAtomDetails()

            // Infinite rotation for electronic orbital orbits!
            val infiniteTransition = rememberInfiniteTransition(label = "orbit_anim")
            val orbitAngle by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(8000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "angle"
            )

            // Bohr Orbital Animation Screen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f))
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                val shellColor = MaterialTheme.colorScheme.onSurface
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2f, size.height / 2f)

                    // 1. Draw central glowing nucleus (protons & neutrons)
                    drawCircle(
                        color = Color(0x338B5CF6),
                        radius = 32.dp.toPx(),
                        center = center
                    )
                    drawCircle(
                        color = Color(0xFF8B5CF6),
                        radius = 18.dp.toPx(),
                        center = center
                    )

                    // 2. Draw circular shells representing K and L ring capacities
                    if (info.shells.isNotEmpty()) {
                        // Shell 1 (K - max 2)
                        drawCircle(
                            color = shellColor.copy(alpha = 0.15f),
                            radius = 48.dp.toPx(),
                            center = center,
                            style = Stroke(width = 1.dp.toPx())
                        )

                        // Draw moving electrons on Shell 1
                        val kCount = info.shells[0]
                        for (i in 0 until kCount) {
                            val angle = Math.toRadians((orbitAngle + (i * (360 / kCount))).toDouble())
                            val eX = center.x + (Math.cos(angle) * 48.dp.toPx()).toFloat()
                            val eY = center.y + (Math.sin(angle) * 48.dp.toPx()).toFloat()
                            drawCircle(Color(0xFF06B6D4), 5.dp.toPx(), Offset(eX, eY))
                        }
                    }

                    if (info.shells.size > 1) {
                        // Shell 2 (L - max 8)
                        drawCircle(
                            color = shellColor.copy(alpha = 0.10f),
                            radius = 72.dp.toPx(),
                            center = center,
                            style = Stroke(width = 1.dp.toPx())
                        )

                        // Draw moving electrons on Shell 2
                        val lCount = info.shells[1]
                        for (i in 0 until lCount) {
                            val angle = Math.toRadians(((-orbitAngle) + (i * (360 / lCount))).toDouble())
                            val eX = center.x + (Math.cos(angle) * 72.dp.toPx()).toFloat()
                            val eY = center.y + (Math.sin(angle) * 72.dp.toPx()).toFloat()
                            drawCircle(Color(0xFF06B6D4), 5.dp.toPx(), Offset(eX, eY))
                        }
                    }
                }

                // Central Symbol Text overlays
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = info.symbol, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Black, fontSize = 24.sp)
                    Text(text = "${info.massNumber}u", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Live stats outputs (Mass, Symbol, Charge, Stability)
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Nama Unsur", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), fontSize = 10.sp)
                        Text(info.name, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Status Inti", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), fontSize = 10.sp)
                        Text(
                            text = if (info.isStable) "STABIL" else "TIDAK STABIL",
                            color = if (info.isStable) Color(0xFF10B981) else Color(0xFFEF4444),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Muatan Bersih", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), fontSize = 10.sp)
                        Text(info.chargeText, color = SecondaryNeonCyan, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // INTERACTIVE INCREMENTORS FOR PROTONS, NEUTRONS, ELECTRONS
            Text("Konfigurasi Partikel Inti & Orbital:", color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                // Protons adjustment
                PartikelRow(
                    label = "Proton (Nomor Atom)",
                    count = protons,
                    color = Color(0xFFEF4444), // cohesive Red
                    subText = "Menentukan nama jati diri unsur",
                    onMinus = { viewModel.adjustProtons(-1) },
                    onPlus = { viewModel.adjustProtons(1) }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Neutrons adjustment
                PartikelRow(
                    label = "Neutron (Isotop)",
                    count = neutrons,
                    color = Color(0xFF94A3B8), // cohesive Slate
                    subText = "Menyeimbangkan stabilitas atom",
                    onMinus = { viewModel.adjustNeutrons(-1) },
                    onPlus = { viewModel.adjustNeutrons(1) }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Electrons adjustment
                PartikelRow(
                    label = "Elektron (Kulit)",
                    count = electrons,
                    color = Color(0xFF06B6D4), // cohesive Cyan
                    subText = "Menentukan gaya listrik muatan",
                    onMinus = { viewModel.adjustElectrons(-1) },
                    onPlus = { viewModel.adjustElectrons(1) }
                )
            }
        } else {
            // AUTO BUILD POPULER SENYAWA 3D VIEW (H2O, CH4, C2H4)
            val infoText = when (selectedCompound) {
                "H2O" -> "2 Atom H • 1 Atom O • Geometri Bentuk Molekul V-Shape Bengkok. Ikatan kovalen polar terjadi karena atom Oksigen yang sangat elektronegatif menarik kerapatan elektron bersama lebih ke arah dirinya dibanding hidrogen."
                "CH4" -> "1 Atom C • 4 Atom H • Geometri Struktur Metana (Tetrahedral). Karbon memusatkan ikatan kovalen tunggal nonpolar secara simetris ke empat atom Hidrogen luar menjadikannya hidrokarbon paling murni."
                else -> "2 Atom C • 4 Atom H • Geometri Struktur Alkena Etena (Planar Segitiga). Dua atom karbon berkolaborasi di pusat lewat ikatan kovalen rangkap dua (C=C) sejajar dan kokoh."
            }
            val compoundName = when (selectedCompound) {
                "H2O" -> "Air (Dihydrogen Monoxide)"
                "CH4" -> "Gas Metana"
                else -> "Etena (Etilen)"
            }
            val formulaText = when (selectedCompound) {
                "H2O" -> "H₂O"
                "CH4" -> "CH₄"
                else -> "C₂H₄"
            }
            val typeColor = when (selectedCompound) {
                "H2O" -> Color(0xFF2DD4BF)
                "CH4" -> Color(0xFF10B981)
                else -> Color(0xFF8B5CF6)
            }

            // Define the visual atoms and bonds for compound mapping
            val compoundAtoms = when (selectedCompound) {
                "H2O" -> listOf(
                    VisualAtom("O", Color(0xFFEC4899), 14f, 0f, 0f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, -50f, 40f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, 50f, 40f, 0f)
                )
                "CH4" -> listOf(
                    VisualAtom("C", Color(0xFF6B7280), 16f, 0f, 0f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, 0f, -70f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, -60f, 30f, 40f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, 60f, 30f, 40f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, 0f, 30f, -70f)
                )
                else -> listOf( // C2H4
                    VisualAtom("C", Color(0xFF6B7280), 16f, -35f, 0f, 0f),
                    VisualAtom("C", Color(0xFF6B7280), 16f, 35f, 0f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, -85f, -50f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, -85f, 50f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, 85f, -50f, 0f),
                    VisualAtom("H", Color(0xFFE5E7EB), 10f, 85f, 50f, 0f)
                )
            }

            val compoundBonds = when (selectedCompound) {
                "H2O" -> listOf(
                    VisualBond(0, 1, 1),
                    VisualBond(0, 2, 1)
                )
                "CH4" -> listOf(
                    VisualBond(0, 1, 1),
                    VisualBond(0, 2, 1),
                    VisualBond(0, 3, 1),
                    VisualBond(0, 4, 1)
                )
                else -> listOf( // C2H4
                    VisualBond(0, 1, 2), // Double Bond!
                    VisualBond(0, 2, 1),
                    VisualBond(0, 3, 1),
                    VisualBond(1, 4, 1),
                    VisualBond(1, 5, 1)
                )
            }

            // Interactive molecule 3D board
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Visualisasi Struktur 3D 🧊",
                        color = typeColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Swipe Untuk Memutar",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .border(1.5.dp, typeColor.copy(alpha = 0.25f), RoundedCornerShape(24.dp))
                        .background(Color(0xFF0F172A))
                        .pointerInput(selectedCompound) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                rotationAngle += dragAmount.x * 0.7f
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val rad = (rotationAngle * Math.PI / 180f).toFloat()

                        // Rotate atoms
                        val rotatedAtoms = compoundAtoms.map { atom ->
                            val rx = atom.x * Math.cos(rad.toDouble()) - atom.z * Math.sin(rad.toDouble())
                            val rz = atom.x * Math.sin(rad.toDouble()) + atom.z * Math.cos(rad.toDouble())
                            Triple(rx.toFloat(), atom.y, rz.toFloat())
                        }

                        // 1. Draw Bonds
                        compoundBonds.forEach { bond ->
                            val start = rotatedAtoms.getOrNull(bond.fromIdx) ?: return@forEach
                            val end = rotatedAtoms.getOrNull(bond.toIdx) ?: return@forEach

                            val startPt = Offset(center.x + start.first * zoomScale, center.y + start.second * zoomScale)
                            val endPt = Offset(center.x + end.first * zoomScale, center.y + end.second * zoomScale)

                            when (bond.order) {
                                1 -> {
                                    drawLine(
                                        color = Color.White.copy(alpha = 0.4f),
                                        start = startPt,
                                        end = endPt,
                                        strokeWidth = 6f
                                    )
                                }
                                2 -> {
                                    drawLine(
                                        color = Color.White.copy(alpha = 0.4f),
                                        start = Offset(startPt.x - 4f, startPt.y - 4f),
                                        end = Offset(endPt.x - 4f, endPt.y - 4f),
                                        strokeWidth = 4f
                                    )
                                    drawLine(
                                        color = Color.White.copy(alpha = 0.4f),
                                        start = Offset(startPt.x + 4f, startPt.y + 4f),
                                        end = Offset(endPt.x + 4f, endPt.y + 4f),
                                        strokeWidth = 4f
                                    )
                                }
                            }
                        }

                        // 2. Draw Atoms
                        val indexedAtoms = compoundAtoms.mapIndexed { idx, atom ->
                            val rot = rotatedAtoms[idx]
                            Quadruplet(atom, rot.first, rot.second, rot.third)
                        }.sortedBy { it.fourth }

                        indexedAtoms.forEach { q ->
                            val atom = q.first
                            val rx = q.second
                            val ry = q.third
                            val scaledRadius = atom.r * zoomScale

                            val atomPt = Offset(center.x + rx * zoomScale, center.y + ry * zoomScale)

                            drawCircle(
                                color = atom.color.copy(alpha = 0.2f),
                                radius = scaledRadius + 6f,
                                center = atomPt
                            )
                            drawCircle(
                                color = atom.color,
                                radius = scaledRadius,
                                center = atomPt
                            )
                            drawCircle(
                                color = Color.White.copy(alpha = 0.4f),
                                radius = scaledRadius * 0.35f,
                                center = Offset(atomPt.x - scaledRadius * 0.3f, atomPt.y - scaledRadius * 0.3f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Zoom Slider
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Zoom Molekul:", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(12.dp))
                    Slider(
                        value = zoomScale,
                        onValueChange = { zoomScale = it },
                        valueRange = 0.6f..1.5f,
                        colors = SliderDefaults.colors(
                            thumbColor = typeColor,
                            activeTrackColor = typeColor
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Compound Description Readout Panel
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = formulaText,
                        color = typeColor,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp
                    )
                    Box(
                        modifier = Modifier
                            .background(typeColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Bentuk Molekul Sempurna",
                            color = typeColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                Text(
                    text = compoundName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                Text(
                    text = infoText,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun PartikelRow(
    label: String,
    count: Int,
    color: Color,
    subText: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(label, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Text(subText, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 11.sp)
        }

        // Controllers
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                    .clickable { onMinus() },
                contentAlignment = Alignment.Center
            ) {
                Text("—", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                count.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.widthIn(min = 20.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                    .clickable { onPlus() },
                contentAlignment = Alignment.Center
            ) {
                Text("+", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- TAB 2: INTERACTIVE CHEMICAL BONDING LAB COMPOSABLE ---
@Composable
fun BondingLabTab(viewModel: SimulationViewModel) {
    val elementA by viewModel.selectedElementA.collectAsState()
    val elementB by viewModel.selectedElementB.collectAsState()

    val bond = viewModel.getBondDetails()

    val elementAList = listOf("Na", "H", "C")
    val elementBList = listOf("Cl", "H", "O")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Selection board
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Element A selection
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Pilih Unsur A:", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        elementAList.forEach { symbol ->
                            val isChosen = elementA == symbol
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isChosen) SecondaryNeonCyan else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                    .clickable { viewModel.setElementsForBond(symbol, elementB) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(symbol, color = if (isChosen) Color.Black else MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Icon(
                    imageVector = Icons.Default.FlashOn,
                    contentDescription = "Reaction",
                    tint = SecondaryNeonCyan,
                    modifier = Modifier.size(24.dp)
                )

                // Element B selection
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Pilih Unsur B:", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        elementBList.forEach { symbol ->
                            val isChosen = elementB == symbol
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isChosen) NeonPurple else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                    .clickable { viewModel.setElementsForBond(elementA, symbol) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(symbol, color = if (isChosen) Color.White else MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Large compound visualization board
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f))
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Glow circle element A
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(bond.colorA).copy(alpha = 0.2f))
                        .border(1.5.dp, Color(bond.colorA), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(elementA, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Black, fontSize = 20.sp)
                }

                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Bond Link",
                    tint = SecondaryNeonCyan,
                    modifier = Modifier.size(24.dp)
                )

                // Glow circle element B
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(bond.colorB).copy(alpha = 0.2f))
                        .border(1.5.dp, Color(bond.colorB), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(elementB, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Black, fontSize = 20.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Results Card description
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Formula: ${bond.formula}",
                color = SecondaryNeonCyan,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = bond.compoundName,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Kategori: ${bond.type}",
                color = NeonPurple,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

            Text(
                text = bond.description,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                fontSize = 13.sp,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


// --- TAB 3: HYDROCARBON BUILDER COMPOSABLE ---
private data class VisualAtom(
    val element: String, // "C", "H", "O"
    val color: Color,
    val r: Float, // radius
    val x: Float,
    val y: Float,
    val z: Float
)

private data class VisualBond(
    val fromIdx: Int,
    val toIdx: Int,
    val order: Int // 1, 2, 3
)

private data class Quadruplet<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

@Composable
fun HydrocarbonTab(viewModel: SimulationViewModel, onSimulationComplete: () -> Unit) {
    val type by viewModel.hydrocarbonType.collectAsState()
    val carbonCount by viewModel.carbonCount.collectAsState()
    val hc = viewModel.getHydrocarbonDetails()

    var rotationAngle by remember { mutableStateOf(45f) }
    var zoomScale by remember { mutableStateOf(1.0f) }

    // Generates coordinates for atoms dynamically
    val molecule = remember(type, carbonCount) {
        val atoms = mutableListOf<VisualAtom>()
        val bonds = mutableListOf<VisualBond>()

        if (type == "Benzena") {
            // Hexagonal Ring
            val ringRadius = 70f
            val hDist = 45f
            for (i in 0 until 6) {
                val angle = (i * Math.PI / 3).toFloat()
                val cx = (ringRadius * Math.cos(angle.toDouble())).toFloat()
                val cy = (ringRadius * Math.sin(angle.toDouble())).toFloat()
                atoms.add(VisualAtom("C", Color(0xFF6B7280), 16f, cx, cy, 0f))
                
                val hx = ((ringRadius + hDist) * Math.cos(angle.toDouble())).toFloat()
                val hy = ((ringRadius + hDist) * Math.sin(angle.toDouble())).toFloat()
                atoms.add(VisualAtom("H", Color(0xFFE5E7EB), 10f, hx, hy, 0f))
            }
            
            // Octet resonating benzene ring bonds
            for (i in 0 until 6) {
                val nextC = (i + 1) % 6
                val bondOrder = if (i % 2 == 0) 2 else 1
                bonds.add(VisualBond(i * 2, nextC * 2, bondOrder))
                bonds.add(VisualBond(i * 2, i * 2 + 1, 1))
            }
        } else {
            // Straight chain configuration
            val spacing = 75f
            
            // Add carbon nodes
            for (i in 0 until carbonCount) {
                val cx = (i - (carbonCount - 1) / 2f) * spacing
                val cy = if (i % 2 == 0) -25f else 25f
                atoms.add(VisualAtom("C", Color(0xFF6B7280), 16f, cx, cy, 0f))
            }
            
            // Attach Carbon-Carbon bonds
            for (i in 0 until carbonCount - 1) {
                val order = when {
                    type == "Alkena" && i == 0 -> 2
                    type == "Alkuna" && i == 0 -> 3
                    else -> 1
                }
                bonds.add(VisualBond(i, i + 1, order))
            }
            
            // Attach Hydrogen and Oxygen functional nodes
            for (i in 0 until carbonCount) {
                val cx = atoms[i].x
                val cy = atoms[i].y
                
                // Top Hydrogen
                val hTopIdx = atoms.size
                atoms.add(VisualAtom("H", Color(0xFFE5E7EB), 10f, cx, cy - 50f, 30f))
                bonds.add(VisualBond(i, hTopIdx, 1))
                
                // Bottom Hydrogen / Alcohol function
                val isLast = i == carbonCount - 1
                if (type == "Alkohol" && isLast) {
                    val oIdx = atoms.size
                    atoms.add(VisualAtom("O", Color(0xFFEF4444), 14f, cx + 55f, cy, 0f))
                    bonds.add(VisualBond(i, oIdx, 1))
                    
                    val hOIdx = atoms.size
                    atoms.add(VisualAtom("H", Color(0xFFE5E7EB), 10f, cx + 55f, cy + 45f, 0f))
                    bonds.add(VisualBond(oIdx, hOIdx, 1))
                } else {
                    val hBotIdx = atoms.size
                    atoms.add(VisualAtom("H", Color(0xFFE5E7EB), 10f, cx, cy + 50f, -30f))
                    bonds.add(VisualBond(i, hBotIdx, 1))
                }
            }
            
            // Extreme boundaries hydrogens
            val hLeftIdx = atoms.size
            atoms.add(VisualAtom("H", Color(0xFFE5E7EB), 10f, atoms[0].x - 50f, atoms[0].y, 0f))
            bonds.add(VisualBond(0, hLeftIdx, 1))
            
            if (type != "Alkohol") {
                val hRightIdx = atoms.size
                atoms.add(VisualAtom("H", Color(0xFFE5E7EB), 10f, atoms[carbonCount - 1].x + 50f, atoms[carbonCount - 1].y, 0f))
                bonds.add(VisualBond(carbonCount - 1, hRightIdx, 1))
            }
        }
        
        atoms to bonds
    }

    LaunchedEffect(type, carbonCount) {
        onSimulationComplete() // Satisfyingly reward the student automatically!
    }

    val themeColor = when (type) {
        "Alkana" -> PrimaryNeonBlue
        "Alkena" -> Color(0xFF10B981)
        "Alkuna" -> Color(0xFF8B5CF6)
        "Alkohol" -> Color(0xFFF59E0B)
        else -> Color(0xFFEC4899)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Step 1: Filter Categories and Carbons Count Selectors
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Pilih Kategori Hidrokarbon:",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val categories = listOf("Alkana", "Alkena", "Alkuna", "Alkohol", "Benzena")
                categories.forEach { name ->
                    val isSelected = type == name
                    val tabColor = when (name) {
                        "Alkana" -> PrimaryNeonBlue
                        "Alkena" -> Color(0xFF10B981)
                        "Alkuna" -> Color(0xFF8B5CF6)
                        "Alkohol" -> Color(0xFFF59E0B)
                        else -> Color(0xFFEC4899)
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) tabColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                            .clickable { viewModel.setHydrocarbonType(name) }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = name,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            if (type != "Benzena") {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                Text("Jumlah Atom Karbon (C):", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 11.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    val minC = when (type) {
                        "Alkana" -> 1
                        "Benzena" -> 6
                        else -> 2
                    }
                    for (i in minC..6) {
                        val isSelected = carbonCount == i
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) themeColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                .clickable { viewModel.setCarbonCount(i) }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = i.toString(),
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Interaktif 3D Molecule Canvas Board with Auto-build, Rotate and Zoom
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Visualisasi Struktur 3D 🧊",
                    color = themeColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = "Swipe Kiri/Kanan Untuk Memutar",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            // 3D Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.5.dp, themeColor.copy(alpha = 0.25f), RoundedCornerShape(24.dp))
                    .background(Color(0xFF0F172A)) // High contrast dark space color
                    .pointerInput(type, carbonCount) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            rotationAngle += dragAmount.x * 0.7f
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val rad = (rotationAngle * Math.PI / 180f).toFloat()

                    val atomsList = molecule.first
                    val bondsList = molecule.second

                    // Transform and rotate atoms around the Y-axis
                    val rotatedAtoms = atomsList.map { atom ->
                        val rx = atom.x * Math.cos(rad.toDouble()) - atom.z * Math.sin(rad.toDouble())
                        val rz = atom.x * Math.sin(rad.toDouble()) + atom.z * Math.cos(rad.toDouble())
                        // Pre-rotated coordinate packet
                        Triple(rx.toFloat(), atom.y, rz.toFloat())
                    }

                    // 1. Draw Covalent chemical bonds first
                    bondsList.forEach { bond ->
                        val start = rotatedAtoms.getOrNull(bond.fromIdx) ?: return@forEach
                        val end = rotatedAtoms.getOrNull(bond.toIdx) ?: return@forEach

                        val startPt = Offset(center.x + start.first * zoomScale, center.y + start.second * zoomScale)
                        val endPt = Offset(center.x + end.first * zoomScale, center.y + end.second * zoomScale)

                        when (bond.order) {
                            1 -> {
                                drawLine(
                                    color = Color.White.copy(alpha = 0.4f),
                                    start = startPt,
                                    end = endPt,
                                    strokeWidth = 6f
                                )
                            }
                            2 -> {
                                // Double parallel bonds
                                drawLine(
                                    color = Color.White.copy(alpha = 0.4f),
                                    start = Offset(startPt.x - 4f, startPt.y - 4f),
                                    end = Offset(endPt.x - 4f, endPt.y - 4f),
                                    strokeWidth = 4f
                                )
                                drawLine(
                                    color = Color.White.copy(alpha = 0.4f),
                                    start = Offset(startPt.x + 4f, startPt.y + 4f),
                                    end = Offset(endPt.x + 4f, endPt.y + 4f),
                                    strokeWidth = 4f
                                )
                            }
                            3 -> {
                                // Triple functional bonds
                                drawLine(
                                    color = Color.White.copy(alpha = 0.4f),
                                    start = startPt,
                                    end = endPt,
                                    strokeWidth = 3f
                                )
                                drawLine(
                                    color = Color.White.copy(alpha = 0.4f),
                                    start = Offset(startPt.x - 6f, startPt.y - 6f),
                                    end = Offset(endPt.x - 6f, endPt.y - 6f),
                                    strokeWidth = 3f
                                )
                                drawLine(
                                    color = Color.White.copy(alpha = 0.4f),
                                    start = Offset(startPt.x + 6f, startPt.y + 6f),
                                    end = Offset(endPt.x + 6f, endPt.y + 6f),
                                    strokeWidth = 3f
                                )
                            }
                        }
                    }

                    // 2. Render atom spheres sorted by depth (Z-axis) to solve 3D occlusion
                    val indexedAtoms = atomsList.mapIndexed { idx, atom ->
                        val rot = rotatedAtoms[idx]
                        Quadruplet(atom, rot.first, rot.second, rot.third)
                    }.sortedBy { it.fourth } // Ascending depth order

                    indexedAtoms.forEach { q ->
                        val atom = q.first
                        val rx = q.second
                        val ry = q.third
                        val scaledRadius = atom.r * zoomScale

                        val atomPt = Offset(center.x + rx * zoomScale, center.y + ry * zoomScale)

                        // Outer Glow ball
                        drawCircle(
                            color = atom.color.copy(alpha = 0.2f),
                            radius = scaledRadius + 6f,
                            center = atomPt
                        )

                        // Main shaded circle
                        drawCircle(
                            color = atom.color,
                            radius = scaledRadius,
                            center = atomPt
                        )

                        // Inner Highlight sparkle for glossy 3D ball realism
                        drawCircle(
                            color = Color.White.copy(alpha = 0.4f),
                            radius = scaledRadius * 0.35f,
                            center = Offset(atomPt.x - scaledRadius * 0.3f, atomPt.y - scaledRadius * 0.3f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Zoom Controller Slider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zoom Molekul:", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(12.dp))
                Slider(
                    value = zoomScale,
                    onValueChange = { zoomScale = it },
                    valueRange = 0.6f..1.5f,
                    colors = SliderDefaults.colors(
                        thumbColor = themeColor,
                        activeTrackColor = themeColor
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Molecular Profile Information Readout
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = hc.formula,
                    color = themeColor,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp
                )
                Box(
                    modifier = Modifier
                        .background(themeColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = type,
                        color = themeColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }

            Text(
                text = hc.name,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

            Text(
                text = hc.structuralText,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = themeColor,
                    modifier = Modifier.size(16.dp).padding(top = 1.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Aplikasi Industri & Alam:\n${hc.useCase}",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}


// --- TAB 4: ADVANCED MULTI CHEMICAL REACTOR (Acid-Base, Electrolysis, Redox, Combustion, Rusting, States etc) ---
@Composable
fun AdvancedReactorTab(viewModel: SimulationViewModel, onSimulationComplete: () -> Unit) {
    val advType by viewModel.advReactorType.collectAsState()
    val sliderVal by viewModel.advSliderVal.collectAsState()
    val timeFactor by viewModel.advTimeMultiplier.collectAsState()
    val catalyst by viewModel.advCatalyst.collectAsState()
    val isRunning by viewModel.advIsRunning.collectAsState()
    val progress by viewModel.advProgress.collectAsState()

    // Smooth progress animation loops
    LaunchedEffect(isRunning) {
        if (isRunning) {
            var p = 0f
            while (p < 1.0f) {
                kotlinx.coroutines.delay(40)
                p += 0.025f
                viewModel.updateAdvProgress(p)
            }
            onSimulationComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Multi Reactor type Selection Caps
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text("Pilih Simulasi Advanced Lab:", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 11.sp)
            Spacer(modifier = Modifier.height(6.dp))

            val advTabs = listOf("Asam-Basa (pH)", "Elektrolisis", "Pembakaran", "Korosi", "Fase & Zat")
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    advTabs.take(3).forEach { tab ->
                        val isSelected = advType == tab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) SecondaryNeonCyan else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                .clickable { viewModel.setAdvReactorType(tab) }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(tab, color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    advTabs.drop(3).forEach { tab ->
                        val isSelected = advType == tab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) SecondaryNeonCyan else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                .clickable { viewModel.setAdvReactorType(tab) }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(tab, color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Large Graphic Visualizer (Beaker / Flame / Plate / Grid depending on the simulation)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f))
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            when (advType) {
                "Asam-Basa (pH)" -> {
                    // Draw solution beaker with dynamic pH indicator color
                    val liquidColor = when {
                        sliderVal < 4.0f -> Color(0xFFEF4444) // Acid Red
                        sliderVal < 6.5f -> Color(0xFFF97316) // Weak Acid Orange
                        sliderVal < 7.5f -> Color(0xFF10B981) // Neutral Green
                        sliderVal < 10.0f -> Color(0xFF3B82F6) // Weak Alkaline Blue
                        else -> Color(0xFF8B5CF6) // Strong Alkaline Purple
                    }
                    val volumeRatio = (timeFactor / 100f).coerceIn(0.1f, 0.9f)

                    val strokeColor = MaterialTheme.colorScheme.onSurface
                    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        val w = size.width
                        val h = size.height

                        // Beaker outline
                        drawRoundRect(
                            color = strokeColor.copy(alpha = 0.15f),
                            topLeft = Offset(w * 0.35f, h * 0.15f),
                            size = androidx.compose.ui.geometry.Size(w * 0.3f, h * 0.7f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f),
                            style = Stroke(width = 3.dp.toPx())
                        )

                        // Measuring line marks on beaker
                        for (i in 1..4) {
                            val markY = h * 0.15f + (h * 0.7f) * (i / 5f)
                            drawLine(
                                color = strokeColor.copy(alpha = 0.4f),
                                start = Offset(w * 0.35f, markY),
                                end = Offset(w * 0.38f, markY),
                                strokeWidth = 2.dp.toPx()
                            )
                        }

                        // Beaker Liquid content
                        val liquidH = h * 0.7f * volumeRatio
                        drawRoundRect(
                            color = liquidColor.copy(alpha = 0.6f),
                            topLeft = Offset(w * 0.355f, h * 0.85f - liquidH),
                            size = androidx.compose.ui.geometry.Size(w * 0.29f, liquidH),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
                        )
                    }
                }
                "Elektrolisis" -> {
                    // Show double plates anode cathode with dynamic sparks/bubbling and current voltage gauge
                    val currentVal = sliderVal
                    val bubbleColor = MaterialTheme.colorScheme.onSurface
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        val midX = w / 2f

                        // Liquid electrolyte
                        drawRect(
                            color = Color(0x3300D8F6),
                            topLeft = Offset(w * 0.2f, h * 0.4f),
                            size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.5f)
                        )

                        // Cathode Left Plate (-)
                        drawRect(
                            color = Color(0xFFE2E8F0),
                            topLeft = Offset(w * 0.33f, h * 0.15f),
                            size = androidx.compose.ui.geometry.Size(w * 0.05f + currentVal.dp.toPx() * 0.5f, h * 0.65f)
                        )

                        // Anode Right Plate (+)
                        drawRect(
                            color = Color(0xFFCD7F32), // Copper color
                            topLeft = Offset(w * 0.62f, h * 0.15f),
                            size = androidx.compose.ui.geometry.Size(w * 0.05f, h * 0.65f)
                        )

                        // Spark effect based on progress/run
                        if (isRunning) {
                            for (i in 0..5) {
                                drawCircle(
                                    color = bubbleColor.copy(alpha = 0.8f),
                                    radius = 3.dp.toPx(),
                                    center = Offset(w * 0.36f + i * 15, h * 0.45f + i * 20)
                                )
                            }
                        }
                    }
                }
                "Pembakaran" -> {
                    // Show a flame gas source where scale depend on oxygen ratio O2
                    val flameScale = sliderVal.coerceIn(1.0f, 10.0f)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.size(100.dp)
                        ) {
                            // Draw animated glowing cone representation
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.1f * flameScale)
                                    .fillMaxHeight(0.08f * flameScale)
                                    .clip(RoundedCornerShape(topStartPercent = 80, topEndPercent = 80))
                                    .background(
                                        androidx.compose.ui.graphics.Brush.verticalGradient(
                                            colors = listOf(Color(0xFFFFEA79), Color(0xFFFF4800), Color(0xFF00CBF6))
                                        )
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Dapur Pembakaran Hidrokarbon",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            fontSize = 11.sp
                        )
                    }
                }
                "Korosi" -> {
                    // Show steel iron nail rusting
                    val rustingLevel = sliderVal // 1 = light, 2 = medium, 3 = extreme
                    val ironColor = when (rustingLevel) {
                        1f -> Color(0xFF94A3B8) // slate metal
                        2f -> Color(0xFFB45309) // light brown rust
                        else -> Color(0xFF78350F) // extreme dark burnt rust
                    }
                    Text(
                        text = "🔩 PENGARUH ELEKTROLIT AIR ASAM-GARAM",
                        color = ironColor,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp
                    )
                }
                else -> { // Stoikiometri & Fase
                    val temperature = sliderVal
                    val (stateName, stateGlow) = when {
                        temperature < 0 -> "Fase Padat (H₂O Es Jenuh Grid)" to Color(0xFF00E1FD)
                        temperature <= 100 -> "Fase Cair (H₂O Air Drifting)" to Color(0xFF22C55E)
                        else -> "Fase Gas (Gas H₂O Bebas Hambatan)" to Color(0xFFEC4899)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stateName,
                            color = stateGlow,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // Represent molecul circle cells floating
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (i in 1..5) {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(stateGlow)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Simulated sliders controls for advanced chemical reactions
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Konfigurasi Variable Reaksi",
                color = SecondaryNeonCyan,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            when (advType) {
                "Asam-Basa (pH)" -> {
                    Text("Titik Batas pH Larutan: ${String.format("%.1f", sliderVal)}", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = sliderVal,
                        onValueChange = { viewModel.setAdvSliderVal(it) },
                        valueRange = 0.0f..14.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Volume Larutan: ${timeFactor.toInt()} mL", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = timeFactor,
                        onValueChange = { viewModel.setAdvTimeMultiplier(it) },
                        valueRange = 10f..100f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )
                }
                "Elektrolisis" -> {
                    Text("Besar Arus Listrik: ${String.format("%.1f", sliderVal)} Ampere (A)", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = sliderVal,
                        onValueChange = { viewModel.setAdvSliderVal(it) },
                        valueRange = 0.5f..10.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Durasi Aliran Elektron: ${timeFactor.toInt()} Menit", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = timeFactor,
                        onValueChange = { viewModel.setAdvTimeMultiplier(it) },
                        valueRange = 1f..60f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )
                }
                "Pembakaran" -> {
                    Text("Rasio Oksigen Pembakar (O₂): ${String.format("%.1f", sliderVal)} mol/s", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = sliderVal,
                        onValueChange = { viewModel.setAdvSliderVal(it) },
                        valueRange = 1.0f..10.0f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Temperatur Piringan Core: ${timeFactor.toInt()} °C", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = timeFactor,
                        onValueChange = { viewModel.setAdvTimeMultiplier(it) },
                        valueRange = 100f..500f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )
                }
                "Korosi" -> {
                    Text("Solusi Larutan: ${if (sliderVal < 1.5f) "Air Suling / Kelembapan Udara" else if (sliderVal < 2.5f) "Air Asam Hujan" else "Cairan Air Garam Pekat"}", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = sliderVal,
                        onValueChange = { viewModel.setAdvSliderVal(it) },
                        valueRange = 1.0f..3.0f,
                        steps = 2,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Durasi Kontak: ${timeFactor.toInt()} Hari", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = timeFactor,
                        onValueChange = { viewModel.setAdvTimeMultiplier(it) },
                        valueRange = 1f..31f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )
                }
                else -> { // Stoikiometri & Fase
                    Text("Temperatur Termik: ${sliderVal.toInt()} °C", color = MaterialTheme.colorScheme.onSurface, fontSize = 11.sp)
                    Slider(
                        value = sliderVal,
                        onValueChange = { viewModel.setAdvSliderVal(it) },
                        valueRange = -100f..250f,
                        colors = SliderDefaults.colors(
                            thumbColor = SecondaryNeonCyan,
                            activeTrackColor = SecondaryNeonCyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Selection Caps for Advanced Catalyst
            Text("Katalis Tambahan:", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 11.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                listOf("Tanpa Katalis", "Enzim Amilase", "Padatan Platinum").forEach { cat ->
                    val isSelected = catalyst == cat
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) SecondaryNeonCyan.copy(alpha = 0.15f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                            .border(1.dp, if (isSelected) SecondaryNeonCyan else Color.Transparent, RoundedCornerShape(8.dp))
                            .clickable { viewModel.setAdvCatalyst(cat) }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(cat, color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontWeight = FontWeight.Bold, fontSize = 9.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Action run button
            if (!isRunning) {
                Button(
                    onClick = { viewModel.runAdvReactor() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryNeonCyan),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Simulasikan Reaksi Reaktor", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Simulasi Sedang Berjalan: ${(progress * 100).toInt()}%", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = SecondaryNeonCyan,
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Outcome Info board
        if (!isRunning && progress >= 0.9f) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                val (title, bodyText) = when (advType) {
                    "Asam-Basa (pH)" -> {
                        val acidBaseDesc = if (sliderVal < 7.0f) {
                            "Larutan terindikasi Asam Kuat. Reaksi penetralan dengan NaOH menghasilkan panas eksoterm serta ionisasi garam."
                        } else if (sliderVal > 7.0f) {
                            "Larutan terindikasi Basa Kuat. Tingkat kebasaan tinggi mengionisasi indikator Fenolftalein menghasilkan pancaran warna merah fuchsia tajam."
                        } else {
                            "Larutan Netral Sempurna (Air Murni - pH 7.0) dengan konsentrasi H₃O⁺ dan OH⁻ yang seimbang."
                        }
                        "Hasil Analitis pH Meter" to acidBaseDesc
                    }
                    "Elektrolisis" -> {
                        val electrolisisDesc = "Aliran arus ${String.format("%.1f", sliderVal)} Ampere melepaskan kation Cu²⁺ dari katoda ke kawat tembaga secara kuantitatif. Berdasarkan Hukum Faraday Pertama, endapan terkondensasi sebanding dengan laju transfer muatan bebas."
                        "Simulasi Redoks Larutan Elektrolit" to electrolisisDesc
                    }
                    "Pembakaran" -> {
                        val combustionDesc = if (sliderVal < 4.0f) {
                            "Pembakaran Tidak Sempurna terjadi karena defisit suplai Oksigen (O₂). Reaksi menghasilkan Jelaga kental (Minyak karbon) hitam kotor beserta gas beracun Karbon Monoksida (CO)."
                        } else {
                            "Pembakaran Sempurna Hidrokarbon Metana (CH₄) menghasilkan api biru bertemperatur tinggi di atas 1000°C dengan sisa fasa gas berupa CO₂ dan uap air H₂O murni."
                        }
                        "Status Tungku Pembakaran" to combustionDesc
                    }
                    "Korosi" -> {
                        val corrosionDesc = if (sliderVal < 1.5f) {
                            "Korosi berjalan lambat di kelembapan udara biasa karena laju perpindahan elektron kation Fe²⁺ yang rendah."
                        } else {
                            "Keberadaan Elektrolit air garam/asam meningkatkan hantaran arus mikro lokal secara ekstrem, mengakibatkan reduksi paksa besi menghasilkan senyawa karat oranye Fe₂O₃ · n H₂O yang rapuh."
                        }
                        "Laporan Korosif Logam Besi" to corrosionDesc
                    }
                    else -> { // Stoikiometri & Fase
                        val phaseDesc = if (sliderVal < 0) {
                            "Pada suhu di bawah membeku, struktur molekul kaku terikat pada getaran kisi heksagonal es fasa padat bermassa jenis rendah."
                        } else if (sliderVal <= 100) {
                            "Pada suhu ruang stabil, ikatan hidrogen dinamis mengalami sirkulasi cepat fasa cair fleksibel dengan mobilitas tinggi."
                        } else {
                            "Pada suhu mendidih ekstrem, klorida air pecah total menembus momentum uap gas bebas berenergi kinetik kinetis tak beraturan."
                        }
                        "Simulasi Perubahan Fase Air" to phaseDesc
                    }
                }

                Text(title, color = SecondaryNeonCyan, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(bodyText, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp, lineHeight = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
