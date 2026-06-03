package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SimulationViewModel : ViewModel() {

    // --- 1. ATOM BUILDER STATE ---
    private val _protons = MutableStateFlow(1)
    val protons = _protons.asStateFlow()

    private val _neutrons = MutableStateFlow(0)
    val neutrons = _neutrons.asStateFlow()

    private val _electrons = MutableStateFlow(1)
    val electrons = _electrons.asStateFlow()

    fun adjustProtons(delta: Int) {
        val next = (_protons.value + delta).coerceIn(1, 10)
        _protons.value = next
    }

    fun adjustNeutrons(delta: Int) {
        val next = (_neutrons.value + delta).coerceIn(0, 12)
        _neutrons.value = next
    }

    fun adjustElectrons(delta: Int) {
        val next = (_electrons.value + delta).coerceIn(0, 10)
        _electrons.value = next
    }

    fun resetAtom() {
        _protons.value = 1
        _neutrons.value = 0
        _electrons.value = 1
    }

    // Get current atom details dynamically
    fun getAtomDetails(): AtomInfo {
        val p = _protons.value
        val n = _neutrons.value
        val e = _electrons.value

        val (name, symbol) = when (p) {
            1 -> "Hidrogen" to "H"
            2 -> "Helium" to "He"
            3 -> "Litium" to "Li"
            4 -> "Berilium" to "Be"
            5 -> "Boron" to "B"
            6 -> "Karbon" to "C"
            7 -> "Nitrogen" to "N"
            8 -> "Oksigen" to "O"
            9 -> "Fluor" to "F"
            10 -> "Neon" to "Ne"
            else -> "Unsur Tidak Dikenal" to "?"
        }

        val massNumber = p + n
        val netCharge = p - e
        val chargeText = when {
            netCharge > 0 -> "Kation (+$netCharge)"
            netCharge < 0 -> "Anion ($netCharge)"
            else -> "Netral (0)"
        }

        // Stability approx
        val isStable = when (p) {
            1 -> n == 0 || n == 1 || n == 2
            2 -> n == 2 || n == 1
            3 -> n == 4 || n == 3
            4 -> n == 5
            5 -> n == 6 || n == 5
            6 -> n == 6 || n == 7
            7 -> n == 7 || n == 8
            8 -> n == 8 || n == 9 || n == 10
            9 -> n == 10
            10 -> n == 10 || n == 11 || n == 12
            else -> true
        }

        // Bohr shell distribution
        val shells = mutableListOf<Int>()
        var remaining = e
        if (remaining > 0) {
            val k = minOf(remaining, 2)
            shells.add(k)
            remaining -= k
        }
        if (remaining > 0) {
            val l = minOf(remaining, 8)
            shells.add(l)
            remaining -= l
        }

        return AtomInfo(
            name = name,
            symbol = symbol,
            atomicNumber = p,
            massNumber = massNumber,
            netCharge = netCharge,
            chargeText = chargeText,
            isStable = isStable,
            shells = shells
        )
    }

    data class AtomInfo(
        val name: String,
        val symbol: String,
        val atomicNumber: Int,
        val massNumber: Int,
        val netCharge: Int,
        val chargeText: String,
        val isStable: Boolean,
        val shells: List<Int>
    )


    // --- 2. BONDING BUILDER STATE ---
    private val _selectedElementA = MutableStateFlow("Na")
    val selectedElementA = _selectedElementA.asStateFlow()

    private val _selectedElementB = MutableStateFlow("Cl")
    val selectedElementB = _selectedElementB.asStateFlow()

    fun setElementsForBond(a: String, b: String) {
        _selectedElementA.value = a
        _selectedElementB.value = b
    }

    fun getBondDetails(): BondInfo {
        val a = _selectedElementA.value
        val b = _selectedElementB.value

        return when {
            a == "Na" && b == "Cl" -> BondInfo(
                compoundName = "Natrium Klorida (Garam Dapur)",
                formula = "NaCl",
                type = "Ikatan Ion",
                description = "Natrium (Na) melepas 1 elektron valensinya membentuk kation Na+, diserahkan seutuhnya ke Klorin (Cl) yang butuh 1 elektron membentuk anion Cl-. Terjadi gaya tarik menarik elektrostatik yang kuat antara keduanya membentuk kisi kristal garam.",
                colorA = 0xFF2563EB, // Na alkali blue
                colorB = 0xFF10B981  // Cl halogen green
            )
            a == "Na" && b == "H" -> BondInfo(
                compoundName = "Natrium Hidrida",
                formula = "NaH",
                type = "Ikatan Ionik Hidrida",
                description = "Natrium (Na) menyerahkan 1 elektron valensinya kepada Hidrogen (H) yang bertindak sebagai hidrida logam (H-). Terbentuk padatan kristal ionik reaktif yang merupakan agen pereduksi kuat di industri sintesis organik.",
                colorA = 0xFF2563EB,
                colorB = 0xFF38BDF8
            )
            a == "Na" && b == "O" -> BondInfo(
                compoundName = "Natrium Oksida",
                formula = "Na₂O",
                type = "Ikatan Ionik Oksida",
                description = "Dua atom Natrium (Na) melepaskan masing-masing 1 elektron valensi kation Na+ untuk diberikan sepenuhnya pada satu atom Oksigen (O) yang membutuhkan 2 elektron (O²-). Menghasilkan senyawa kovalen keras berenergi kisi tinggi.",
                colorA = 0xFF2563EB,
                colorB = 0xFFEC4899
            )
            a == "H" && b == "Cl" -> BondInfo(
                compoundName = "Hidrogen Klorida (Asam Lambung)",
                formula = "HCl",
                type = "Ikatan Kovalen Polar",
                description = "Atom Hidrogen (H) dan Klorin (Cl) saling berbagi satu pasang elektron valensi terluar. Tarikan awan elektron tertarik kuat ke arah Klorin karena elektronegativitasnya jauh lebih besar, menghasilkan dipol polaritas tinggi.",
                colorA = 0xFF38BDF8,
                colorB = 0xFF10B981
            )
            a == "H" && b == "H" -> BondInfo(
                compoundName = "Gas Hidrogen",
                formula = "H₂",
                type = "Ikatan Kovalen Tunggal",
                description = "Setiap atom Hidrogen (H) memiliki 1 elektron valensi dan masing-masing membutuhkan 1 elektron lagi agar stabil (duplet). Mereka saling bersekutu berbagi sepasang elektron terluar bersama secara adil.",
                colorA = 0xFF38BDF8,
                colorB = 0xFF38BDF8
            )
            a == "H" && b == "O" -> BondInfo(
                compoundName = "Air (Dihydrogen Monoxide)",
                formula = "H₂O",
                type = "Ikatan Kovalen Polar",
                description = "Oksigen (O) memiliki 6 elektron valensi dan butuh 2 elektron. Dua atom Hidrogen patungan berbagi masing-masing 1 elektronnya. Terbentuk dua kolaborasi ikatan kovalen tunggal dengan tarikan elektron lebih kuat ke arah Oksigen karena elektronegativitas tinggi.",
                colorA = 0xFF38BDF8,
                colorB = 0xFFEC4899
            )
            a == "C" && b == "Cl" -> BondInfo(
                compoundName = "Karbon Tetraklorida",
                formula = "CCl₄",
                type = "Ikatan Kovalen Nonpolar",
                description = "Satu atom Karbon (C) berikatan kovalen tunggal secara memusat dengan empat atom Klorin (Cl). Walau ikatan C-Cl bersifat polar, simetri geometri tetrahedral yang sempurna saling meniadakan vektor dipol, menghasilkan zat cair nonpolar.",
                colorA = 0xFFF59E0B, // Carbon amber
                colorB = 0xFF10B981  // Chlorine green
            )
            a == "C" && b == "H" -> BondInfo(
                compoundName = "Gas Metana (Fosil Hijau)",
                formula = "CH₄",
                type = "Ikatan Kovalen Tunggal",
                description = "Satu atom Karbon (C) menyatukan keempat elektron terluarnya bersama empat atom Hidrogen (H). Memiliki bentuk geometri molekul tetrahedral simetris nonpolar yang merupakan komponen gas alam hayati paling ramah lingkungan.",
                colorA = 0xFFF59E0B,
                colorB = 0xFF38BDF8
            )
            a == "C" && b == "O" -> BondInfo(
                compoundName = "Karbon Dioksida",
                formula = "CO₂",
                type = "Ikatan Kovalen Rangkap Dua",
                description = "Karbon (C) bersekutu dengan dua atom Oksigen. Karbon butuh 4 elektron, tiap Oksigen butuh 2 elektron. Terjadi pemakaian bersama dua pasang elektron (rangkap dua C=O) di sisi kiri dan sisi kanan secara simetris.",
                colorA = 0xFFF59E0B, // Carbon amber
                colorB = 0xFFEC4899  // Oxygen pink
            )
            else -> BondInfo(
                compoundName = "Senyawa Hipotesis",
                formula = "${a}${b}",
                type = "Ikatan Kimia",
                description = "Kedua unsur berinteraksi melintasi orbital terluarnya untuk menyelaraskan konfigurasi gas mulia.",
                colorA = 0xFF8B5CF6,
                colorB = 0xFF06B6D4
            )
        }
    }

    data class BondInfo(
        val compoundName: String,
        val formula: String,
        val type: String,
        val description: String,
        val colorA: Long,
        val colorB: Long
    )


    // --- 3. HYDROCARBON STRUCTURAL BUILDER AND MANUAL REACTOR ---
    private val _hydrocarbonType = MutableStateFlow("Alkana") // Alkana, Alkena, Alkuna, Alkohol, Benzena
    val hydrocarbonType = _hydrocarbonType.asStateFlow()

    private val _carbonCount = MutableStateFlow(2) // 1 to 6
    val carbonCount = _carbonCount.asStateFlow()

    // Fixed Bug: Manual Reactor Selection & Cleanup to prevent overlap
    private val _installedReactor = MutableStateFlow<String?>(null)
    val installedReactor = _installedReactor.asStateFlow()

    private val _reactorTemp = MutableStateFlow(25f)
    val reactorTemp = _reactorTemp.asStateFlow()

    private val _reactorPressure = MutableStateFlow(1.0f)
    val reactorPressure = _reactorPressure.asStateFlow()

    private val _reactorHydrogenRatio = MutableStateFlow(1.0f)
    val reactorHydrogenRatio = _reactorHydrogenRatio.asStateFlow()

    private val _reactorCatalyst = MutableStateFlow("Tanpa Katalis")
    val reactorCatalyst = _reactorCatalyst.asStateFlow()

    private val _reactorProgress = MutableStateFlow(0f)
    val reactorProgress = _reactorProgress.asStateFlow()

    private val _isReactorRunning = MutableStateFlow(false)
    val isReactorRunning = _isReactorRunning.asStateFlow()

    private val _reactorSuccessMessage = MutableStateFlow<String?>(null)
    val reactorSuccessMessage = _reactorSuccessMessage.asStateFlow()

    fun setHydrocarbonType(type: String) {
        _hydrocarbonType.value = type
        // Automatically adjust Carbon Range boundaries
        val min = when (type) {
            "Alkana" -> 1
            "Benzena" -> 6
            else -> 2
        }
        val max = when (type) {
            "Benzena" -> 6
            else -> 6
        }
        _carbonCount.value = _carbonCount.value.coerceIn(min, max)

        // Reset installed reactor when user changes category
        uninstallReactor()
    }

    fun setCarbonCount(count: Int) {
        val min = when (_hydrocarbonType.value) {
            "Alkana" -> 1
            "Benzena" -> 6
            else -> 2
        }
        _carbonCount.value = count.coerceIn(min, 6)
    }

    fun installReactor(type: String) {
        _installedReactor.value = type
        // Reset states to prevent bleeding
        _reactorTemp.value = if (type == "Benzena") 80f else 25f
        _reactorPressure.value = 1.0f
        _reactorHydrogenRatio.value = 1.0f
        _reactorCatalyst.value = "Tanpa Katalis"
        _reactorProgress.value = 0f
        _isReactorRunning.value = false
        _reactorSuccessMessage.value = null
    }

    fun uninstallReactor() {
        _installedReactor.value = null
        _reactorProgress.value = 0f
        _isReactorRunning.value = false
        _reactorSuccessMessage.value = null
    }

    fun setReactorTemp(temp: Float) {
        _reactorTemp.value = temp
    }

    fun setReactorPressure(press: Float) {
        _reactorPressure.value = press
    }

    fun setReactorHydrogenRatio(ratio: Float) {
        _reactorHydrogenRatio.value = ratio
    }

    fun setReactorCatalyst(cat: String) {
        _reactorCatalyst.value = cat
    }

    fun triggerReactorProgress(onCompleted: () -> Unit) {
        _isReactorRunning.value = true
        _reactorProgress.value = 0f
        _reactorSuccessMessage.value = null
    }

    fun updateReactorProgress(progress: Float) {
        _reactorProgress.value = progress.coerceIn(0f, 1f)
        if (progress >= 1f) {
            _isReactorRunning.value = false
            _reactorSuccessMessage.value = getReactorSuccessText()
        }
    }

    private fun getReactorSuccessText(): String {
        val r = _installedReactor.value ?: return ""
        val c = _carbonCount.value
        val details = getHydrocarbonDetails()

        return when (r) {
            "Alkana" -> {
                if (_reactorTemp.value > 300f) {
                    "Termolisis/Cracking Sukses! ${details.name} pecah menjadi fragmen rantai hidrokarbon pendek."
                } else {
                    "Sintesis Alkana Sukses! Terbentuk ${details.name} (${String(details.formula.map { if (it.isDigit()) toSubscript(it.toString().toInt()).first() else it }.toCharArray())}) pada fasa stabil."
                }
            }
            "Alkena" -> {
                if (_reactorCatalyst.value == "Nikel (Ni)" && _reactorHydrogenRatio.value > 1.2f) {
                    "Hidrogenasi Sukses! ${details.name} dijenuhkan menjadi Alkana sepadan melewati katalis Ni."
                } else {
                    "Simulasi Adisi Sukses! Terbentuk produk tak jenuh ganda dari ${details.name}."
                }
            }
            "Alkuna" -> {
                if (_reactorCatalyst.value == "Lindlar") {
                    "Hidrogenasi Parsial Sukses! ${details.name} tereduksi menjadi fasa cis-Alkena!"
                } else {
                    "Sintesis Alkuna Sukses! Karakteristik ikatan rangkap tiga terdeteksi pada ${details.name}."
                }
            }
            "Alkohol" -> {
                val alcoholName = when (c) {
                    1 -> "Metanol"
                    2 -> "Etanol"
                    3 -> "Propanol"
                    4 -> "Butanol"
                    5 -> "Pentanol"
                    6 -> "Heksanol"
                    else -> "Alkohol"
                }
                if (_reactorCatalyst.value == "Asam Sulfat (H₂SO₄)" && _reactorTemp.value > 140f) {
                    "Dehidrasi Sukses! Alkohol tereliminasi menghasilkan eter atau alkena yang reaktif."
                } else {
                    "Instalasi Alkohol sukses! Formula fasa cair: C${toSubscript(c)}H${toSubscript(c * 2 + 1)}OH ($alcoholName)."
                }
            }
            "Benzena" -> {
                if (_reactorTemp.value in 50f..100f && _reactorCatalyst.value == "Asam Sulfat (H₂SO₄)") {
                    "Nitrasi Benzena Berhasil! Terbentuk senyawa Nitrobenzena beraroma tajam."
                } else {
                    "Resonansi Elektron Stabil! Struktur C₆H₆ lingkar heksagonal kokoh berenergi delokalisasi tinggi."
                }
            }
            else -> "Reaksi Berhasil Diselesaikan!"
        }
    }

    private fun toSubscript(num: Int): String {
        if (num <= 1) return ""
        return num.toString().map { char ->
            when (char) {
                '0' -> '₀'
                '1' -> '₁'
                '2' -> '₂'
                '3' -> '₃'
                '4' -> '₄'
                '5' -> '₅'
                '6' -> '₆'
                '7' -> '₇'
                '8' -> '₈'
                '9' -> '₉'
                else -> char
            }
        }.joinToString("")
    }

    fun getHydrocarbonDetails(): HydrocarbonInfo {
        val type = _hydrocarbonType.value
        val c = _carbonCount.value

        val prefix = when (c) {
            1 -> "Met"
            2 -> "Et"
            3 -> "Prop"
            4 -> "But"
            5 -> "Pent"
            6 -> "Heks"
            else -> "Karbo"
        }

        val (suffix, hCount, bondType, desc) = when (type) {
            "Alkana" -> {
                val h = (c * 2) + 2
                val text = "Rantai jenuh dengan ikatan tunggal C-C secara keseluruhan."
                Quadruplet("ana", h, "C—C", text)
            }
            "Alkena" -> {
                val h = c * 2
                val text = "Rantai tak jenuh yang memiliki satu ikatan rangkap dua C=C."
                Quadruplet("ena", h, "C=C", text)
            }
            "Alkuna" -> {
                val h = (c * 2) - 2
                val text = "Rantai tak jenuh yang memiliki karakteristik ikatan rangkap tiga C≡C."
                Quadruplet("una", h, "C≡C", text)
            }
            "Alkohol" -> {
                val h = (c * 2) + 1
                val text = "Rantai jenuh alkana yang memiliki gugus fungsi hidroksil (-OH) di ujungnya."
                Quadruplet("anol", h, "C—O", text)
            }
            else -> { // Benzena
                val h = 6
                val text = "Cincin aromatik heksagonal datar dengan elektron terdelokalisasi merata."
                Quadruplet("ena", h, "C::C", text)
            }
        }

        val iupacName = if (c == 1 && (type == "Alkena" || type == "Alkuna" || type == "Benzena")) {
            "Tidak Ada"
        } else if (type == "Benzena") {
            "Benzena"
        } else {
            "${prefix}${suffix}"
        }

        val chemicalFormula = if (iupacName == "Tidak Ada") {
            "-"
        } else if (type == "Alkohol") {
            "C${if(c>1) toSubscript(c) else ""}H${toSubscript(hCount)}OH"
        } else if (type == "Benzena") {
            "C₆H₆"
        } else {
            "C${if(c>1) toSubscript(c) else ""}H${toSubscript(hCount)}"
        }

        val useDesc = when (iupacName) {
            "Metana" -> "Bahan utama gas bumi, biogas kotoran sapi."
            "Etana" -> "Bahan baku etena industri plastik."
            "Propana" -> "Bahan utama isi tabung gas LPG kompor dapur."
            "Butana" -> "Cairan korek api gas, aerosol deodoran semprot."
            "Pentana" -> "Pelarut kimia cair di laboratorium industri cat."
            "Heksana" -> "Pembersih minyak mesin, pelarut ekstraksi biji nabati."
            "Etena" -> "Hormon pematangan buah mangga/pisang pada pertanian."
            "Propena" -> "Bahan baku utama kantong plastik polipropilena awet."
            "Butena" -> "Komponen penyusun karet sintetis ban mobil balap."
            "Etuna" -> "Nama komersilnya Gas Asetilen, dipakai tukang las karbit logam."
            "Propuna" -> "Bahan bakar pendorong piringan roket mini."
            "Metanol" -> "Bahan bakar spiritus, bahan baku formaldehida fasa cair."
            "Etanol" -> "Antiseptik luka medis, pelarut parfum, bahan baku minuman fermentasi."
            "Propanol" -> "Bahan aktif hand-sanitizer pembunuh bakteri."
            "Benzena" -> "Pelarut aromatik nonpolar induktif, sintesis nilon & sterofoam."
            else -> "Bahan baku polimerisasi organik dasar."
        }

        return HydrocarbonInfo(
            name = iupacName,
            formula = chemicalFormula,
            category = type,
            carbons = if (type == "Benzena") 6 else c,
            hydrogens = if (iupacName == "Tidak Ada") 0 else hCount,
            bondSymbol = bondType,
            structuralText = desc,
            useCase = useDesc
        )
    }

    private data class Quadruplet<out A, out B, out C, out D>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D
    )

    data class HydrocarbonInfo(
        val name: String,
        val formula: String,
        val category: String,
        val carbons: Int,
        val hydrogens: Int,
        val bondSymbol: String,
        val structuralText: String,
        val useCase: String
    )


    // --- 4. ADVANCED MULTI CHEMICAL REACTOR (Titration, pH, redox, phase shifts, corrosion, combustion) ---
    private val _advReactorType = MutableStateFlow("Asam-Basa (pH)") // Asam-Basa, Elektrolisis, Pembakaran, Korosi, Stoikiometri
    val advReactorType = _advReactorType.asStateFlow()

    private val _advSliderVal = MutableStateFlow(7.0f) // pH value or oxygen flow or electrolytic power
    val advSliderVal = _advSliderVal.asStateFlow()

    private val _advTimeMultiplier = MutableStateFlow(25f) // Temp focus / timing factor
    val advTimeMultiplier = _advTimeMultiplier.asStateFlow()

    private val _advCatalyst = MutableStateFlow("Tanpa Katalis")
    val advCatalyst = _advCatalyst.asStateFlow()

    private val _advIsRunning = MutableStateFlow(false)
    val advIsRunning = _advIsRunning.asStateFlow()

    private val _advProgress = MutableStateFlow(0f)
    val advProgress = _advProgress.asStateFlow()

    fun setAdvReactorType(type: String) {
        _advReactorType.value = type
        _advIsRunning.value = false
        _advProgress.value = 0f
        when (type) {
            "Asam-Basa (pH)" -> { _advSliderVal.value = 7.0f; _advTimeMultiplier.value = 25f }
            "Elektrolisis" -> { _advSliderVal.value = 2.5f; _advTimeMultiplier.value = 10f }
            "Pembakaran" -> { _advSliderVal.value = 3.0f; _advTimeMultiplier.value = 180f }
            "Korosi" -> { _advSliderVal.value = 1.0f; _advTimeMultiplier.value = 12f }
            else -> { _advSliderVal.value = 100f; _advTimeMultiplier.value = 0f } // Stoikiometri & Fase
        }
    }

    fun setAdvSliderVal(value: Float) {
        _advSliderVal.value = value
    }

    fun setAdvTimeMultiplier(value: Float) {
        _advTimeMultiplier.value = value
    }

    fun setAdvCatalyst(cat: String) {
        _advCatalyst.value = cat
    }

    fun runAdvReactor() {
        _advIsRunning.value = true
        _advProgress.value = 0f
    }

    fun updateAdvProgress(prog: Float) {
        _advProgress.value = prog.coerceIn(0f, 1f)
        if (prog >= 1f) {
            _advIsRunning.value = false
        }
    }

    fun stopAdvReactor() {
        _advIsRunning.value = false
        _advProgress.value = 0f
    }
}
