package com.example.data.dummy

import com.example.data.model.ChemicalElement

object PeriodicTableData {

    val elements = listOf(
        // Period 1
        ChemicalElement(1, "H", "Hydrogen", 1.008, "Nonlogam", 1, 1, "1s¹", 1, "Gas", "Henry Cavendish", "1766", "Bahan bakar roket, air, amonia", "Unsur paling melimpah di jagat raya, mengisi 75% massa alam semesta.", 1, 1),
        ChemicalElement(2, "He", "Helium", 4.003, "Gas mulia", 1, 18, "1s²", 2, "Gas", "Janssen & Lockyer", "1868", "Balon terbang, pendingin MRI super", "Tidak berbau, tidak berwarna, dan tidak bisa terbakar dalam kondisi normal.", 18, 1),

        // Period 2
        ChemicalElement(3, "Li", "Lithium", 6.94, "Alkali", 2, 1, "[He] 2s¹", 1, "Solid", "Johan August Arfwedson", "1817", "Baterai ponsel, obat psikiatri", "Logam paling ringan, sangat reaktif sampai bisa terbakar jika kena air.", 1, 2),
        ChemicalElement(4, "Be", "Beryllium", 9.012, "Alkali tanah", 2, 2, "[He] 2s²", 2, "Solid", "Louis Nicolas Vauquelin", "1798", "Komponen satelit, kaca sinar-X", "Sangat beracun, tapi rasanya manis yang menipu peneliti zaman dulu.", 2, 2),
        ChemicalElement(5, "B", "Boron", 10.81, "Metaloid", 2, 13, "[He] 2s² 2p¹", 3, "Solid", "Joseph Louis Gay-Lussac", "1808", "Kaca borosilikat, obat tetes mata", "Filamen boron digunakan dalam teknologi aerospace karena kuat dan ringan.", 13, 2),
        ChemicalElement(6, "C", "Carbon", 12.011, "Nonlogam", 2, 14, "[He] 2s² 2p²", 4, "Solid", "Mesir Kuno", "Kuno", "Pensil gips, bahan bakar batubara, intan", "Dasar semua kehidupan organik di bumi, bisa membentuk jutaan senyawa berbeda.", 14, 2),
        ChemicalElement(7, "N", "Nitrogen", 14.007, "Nonlogam", 2, 15, "[He] 2s² 2p³", 5, "Gas", "Daniel Rutherford", "1772", "Pupuk urea, pendingin instan", "Menyusun 78% udara yang kita hirup sehari-hari untuk meredam oksigen reaktif.", 15, 2),
        ChemicalElement(8, "O", "Oxygen", 15.999, "Nonlogam", 2, 16, "[He] 2s² 2p⁴", 6, "Gas", "Joseph Priestley", "1774", "Pernapasan medis, peleburan baja", "Bentuk cairnya berwarna biru pucat dan bersifat sangat magnetik.", 16, 2),
        ChemicalElement(9, "F", "Fluorine", 18.998, "Halogen", 2, 17, "[He] 2s² 2p⁵", 7, "Gas", "Henri Moissan", "1886", "Pasta gigi (fluoride), teflon anti lengket", "Unsur paling reaktif secara ekstrim di bumi, bisa korosif membakar kaca.", 17, 2),
        ChemicalElement(10, "Ne", "Neon", 20.180, "Gas mulia", 2, 18, "[He] 2s² 2p⁶", 8, "Gas", "Morris Travers", "1898", "Lampu neon reklame", "Jika dialiri listrik tekanan tinggi, neon memancarkan cahaya merah-oranye terang.", 18, 2),

        // Period 3
        ChemicalElement(11, "Na", "Sodium", 22.990, "Alkali", 3, 1, "[Ne] 3s¹", 1, "Solid", "Humphry Davy", "1807", "Garam dapur, lampu jalanan kuning", "Sangat lunak sampai bisa dipotong pisau mentega, langsung meledak dalam air.", 1, 3),
        ChemicalElement(12, "Mg", "Magnesium", 24.305, "Alkali tanah", 3, 2, "[Ne] 3s²", 2, "Solid", "Joseph Black", "1755", "Kembang api putih, aloi pesawat", "Lampu flash kamera jadul menggunakan pita magnesium menyala menyilaukan.", 2, 3),
        ChemicalElement(13, "Al", "Aluminum", 26.982, "Logam transisi", 3, 13, "[Ne] 3s² 3p¹", 3, "Solid", "Hans Christian Ørsted", "1825", "Kaleng minuman, badan pesawat terbang", "Logam ketiga melimpah di kerak bumi, anti karat karena membentuk lapisan oksida.", 13, 3),
        ChemicalElement(14, "Si", "Silicon", 28.085, "Metaloid", 3, 14, "[Ne] 3s² 3p²", 4, "Solid", "Jöns Jacob Berzelius", "1823", "Mikrochip komputer, kaca pecah belah", "Unsur utama di balik revolusi teknologi Silicon Valley dan penyusun pasir pantai.", 14, 3),
        ChemicalElement(15, "P", "Phosphorus", 30.974, "Nonlogam", 3, 15, "[Ne] 3s² 3p³", 5, "Solid", "Hennig Brand", "1669", "Kepala korek api, pupuk superfosfat", "Ditemukan pertama kali dari destilasi urin manusia oleh alkemis mencari batu filsuf.", 15, 3),
        ChemicalElement(16, "S", "Sulfur", 32.06, "Nonlogam", 3, 16, "[Ne] 3s² 3p⁴", 6, "Solid", "Kuno", "Kuno", "Asam sulfat aki, salep kulit anti jamur", "Berwarna kuning cerah, mengeluarkan bau belerang menyengat seperti telur busuk.", 16, 3),
        ChemicalElement(17, "Cl", "Chlorine", 35.45, "Halogen", 3, 17, "[Ne] 3s² 3p⁵", 7, "Gas", "Carl Wilhelm Scheele", "1774", "Pemutih pakaian, kaporit kolam renang", "Gas klorin beracun digunakan sebagai senjata kimia taktis pada Perang Dunia I.", 17, 3),
        ChemicalElement(18, "Ar", "Argon", 39.948, "Gas mulia", 3, 18, "[Ne] 3s² 3p⁶", 8, "Gas", "Lord Rayleigh", "1894", "Pengisi lampu pijar rumah, las logam", "Gas pelindung ideal agar komponen pijaran kawat tungsen lampu tidak gosong.", 18, 3),

        // Period 4
        ChemicalElement(19, "K", "Potassium", 39.098, "Alkali", 4, 1, "[Ar] 4s¹", 1, "Solid", "Humphry Davy", "1807", "Pupuk kalium, pisang (nutrisi sel)", "Penting menjaga detak jantung, terbakar memancarkan warna ungu lilac khas.", 1, 4),
        ChemicalElement(20, "Ca", "Calcium", 40.078, "Alkali tanah", 4, 2, "[Ar] 4s²", 2, "Solid", "Humphry Davy", "1808", "Semen beton, penguat tulang", "Penyusun utama cangkang telur, terumbu karang, kapur tulis, dan gigi mamalia.", 2, 4),
        ChemicalElement(21, "Sc", "Scandium", 44.956, "Logam transisi", 4, 3, "[Ar] 3d¹ 4s²", 3, "Solid", "Lars Fredrik Nilson", "1879", "Aloi sepeda balap ringan", "Dinamakan dari semenanjung Skandinavia tempat cadangan mineralnya ditemukan.", 3, 4),
        ChemicalElement(22, "Ti", "Titanium", 47.867, "Logam transisi", 4, 4, "[Ar] 3d² 4s²", 4, "Solid", "William Gregor", "1791", "Implan medis gigi & tulang, rudal", "Logam terkuat per berat massa, sangat tahan asam lambung atau air laut ekstrim.", 4, 4),
        ChemicalElement(23, "V", "Vanadium", 50.942, "Logam transisi", 4, 5, "[Ar] 3d³ 4s²", 5, "Solid", "Andrés Manuel del Río", "1801", "Baja perkakas mesin", "Senyawanya menghasilkan spektrum warna warni indah, dinamai dari dewi kecantikan Norse.", 5, 4),
        ChemicalElement(24, "Cr", "Chromium", 51.996, "Logam transisi", 4, 6, "[Ar] 3d⁵ 4s¹", 6, "Solid", "Louis Nicolas Vauquelin", "1797", "Pelapis bumper mobil mengkilap", "Memberikan efek warna hijau zamrud dan merah delima merah menyala pada batu permata.", 6, 4),
        ChemicalElement(25, "Mn", "Manganese", 54.938, "Logam transisi", 4, 7, "[Ar] 3d⁵ 4s²", 7, "Solid", "Johan Gottlieb Gahn", "1774", "Kereta baja tahan gesek", "Ditambahkan saat produksi baja untuk mencegah keporosan karbon berlebih.", 7, 4),
        ChemicalElement(26, "Fe", "Iron", 55.845, "Logam transisi", 4, 8, "[Ar] 3d⁶ 4s²", 8, "Solid", "Zaman Perunggu", "Kuno", "Konstruksi jembatan, helm proyek", "Inti bumi berbentuk bola besi raksasa yang menyebabkan medan magnet pelindung bumi.", 8, 4),
        ChemicalElement(27, "Co", "Cobalt", 58.933, "Logam transisi", 4, 9, "[Ar] 3d⁷ 4s²", 9, "Solid", "Georg Brandt", "1735", "Magnet kuat alnico, superaloi turbin", "Pemberi pigmen biru tua pekat nan indah pada keramik porselen Tiongkok kuno.", 9, 4),
        ChemicalElement(28, "Ni", "Nickel", 58.693, "Logam transisi", 4, 10, "[Ar] 3d⁸ 4s²", 10, "Solid", "Axel Fredrik Cronstedt", "1751", "Uang logam receh, stainless steel", "Sebagian besar meteorit besi yang jatuh ke bumi mengandung paduan nikel.", 10, 4),
        ChemicalElement(29, "Cu", "Copper", 63.546, "Logam transisi", 4, 11, "[Ar] 3d¹⁰ 4s¹", 11, "Solid", "Kuno", "Kuno", "Kabel listrik tembaga, pipa air", "Logam pertama yang dilebur manusia (5000 SM), konduktor listrik terfavorit kedua.", 11, 4),
        ChemicalElement(30, "Zn", "Zinc", 65.38, "Logam transisi", 4, 12, "[Ar] 3d¹⁰ 4s²", 12, "Solid", "India Kuno", "Kuno", "Pelapis seng anti karat, baterai ABC", "Suplemen seng esensial meningkatkan kekebalan imunitas tubuh melawan virus batuk.", 12, 4),
        ChemicalElement(31, "Ga", "Gallium", 69.723, "Logam transisi", 4, 13, "[Ar] 3d¹⁰ 4s² 4p¹", 3, "Solid", "Paul-Émile Lecoq", "1875", "Semikonduktor LED, laser biru", "Unsur ajaib yang meleleh pada suhu 29°C, bisa lumer hanya digenggam di telapak tangan.", 13, 4),
        ChemicalElement(32, "Ge", "Germanium", 72.63, "Metaloid", 4, 14, "[Ar] 3d¹⁰ 4s² 4p²", 4, "Solid", "Clemens Winkler", "1886", "Serat optik internet cepat, lensa infra", "Penting melahirkan transistor pertama pembuka era komputasi elektronik modern.", 14, 4),
        ChemicalElement(33, "As", "Arsenic", 74.922, "Metaloid", 4, 15, "[Ar] 3d¹⁰ 4s² 4p³", 5, "Solid", "Albertus Magnus", "1250", "Racun tikus legendaris, semikonduktor", "Sangat dikenal dalam sejarah kriminal abad pertengahan sebagai racun tanpa jejak.", 15, 4),
        ChemicalElement(34, "Se", "Selenium", 78.971, "Nonlogam", 4, 16, "[Ar] 3d¹⁰ 4s² 4p⁴", 6, "Solid", "Jöns Jacob Berzelius", "1817", "Shampoo anti ketombe, sel surya", "Sifat kelistrikannya berubah drastis saat disinari cahaya (fotokonduktivitas).", 16, 4),
        ChemicalElement(35, "Br", "Bromine", 79.904, "Halogen", 4, 17, "[Ar] 3d¹⁰ 4s² 4p⁵", 7, "Liquid", "Antoine J. Balard", "1826", "Bahan pemadam api, cairan fotografi", "Satu-satunya unsur nonlogam berwujud cair kemerahan pada suhu ruang, berbau pesing.", 17, 4),
        ChemicalElement(36, "Kr", "Krypton", 83.798, "Gas mulia", 4, 18, "[Ar] 3d¹⁰ 4s² 4p⁶", 8, "Gas", "Ramsay & Travers", "1898", "Lampu flash runway bandara", "Nama planet asal fiksi Superman diambil dari nama unsur mulia langka ini.", 18, 4),

        // Period 5 elements (Rb, Sr, Zr, Mo, Pd, Cd, Ag, Sn, Sb, Te, I, Xe)
        ChemicalElement(37, "Rb", "Rubidium", 85.468, "Alkali", 5, 1, "[Kr] 5s¹", 1, "Solid", "Bunsen & Kirchhoff", "1861", "Jam atom ultra akurat, riset kuantum", "Logam alkali lunak berwarna keperakan yang berekspansi memercik api ungu di air.", 1, 5),
        ChemicalElement(38, "Sr", "Strontium", 87.62, "Alkali tanah", 5, 2, "[Kr] 5s²", 2, "Solid", "Adair Crawford", "1790", "Kembang api warna merah tua menyala", "Garam nikelnya memberi kilatan warna merah spektakuler pada pertunjukan Tahun Baru.", 2, 5),
        ChemicalElement(39, "Y", "Yttrium", 88.906, "Logam transisi", 5, 3, "[Kr] 4d¹ 5s²", 3, "Solid", "Johan Gadolin", "1794", "Superkonduktor suhu tinggi, laser", "Dinamai dari Ytterby, sebuah desa di Swedia yang kaya kandungan logam tanah jarang.", 3, 5),
        ChemicalElement(40, "Zr", "Zirconium", 91.224, "Logam transisi", 5, 4, "[Kr] 4d² 5s²", 4, "Solid", "Martin Heinrich Klaproth", "1789", "Pipa tahan korosi reaktor nuklir, permata", "Zirkonia kubik sangat berkilau menyerupai berlian asli dengan harga terjangkau.", 4, 5),
        ChemicalElement(42, "Mo", "Molybdenum", 95.95, "Logam transisi", 5, 6, "[Kr] 4d⁵ 5s¹", 6, "Solid", "Carl Wilhelm Scheele", "1778", "Aloi baja mesin jet pesawat tempur", "Memiliki titik leleh yang sangat tinggi, krusial untuk pelindung panas dirgantara.", 6, 5),
        ChemicalElement(46, "Pd", "Palladium", 106.42, "Logam transisi", 5, 10, "[Kr] 4d¹⁰", 10, "Solid", "William Hyde Wollaston", "1803", "Katalis otomotif peredam polusi asap", "Logam berharga mahal yang mampu menyerap gas hidrogen hingga 900 kali volumenya sendiri.", 10, 5),
        ChemicalElement(47, "Ag", "Perak (Silver)", 107.87, "Logam transisi", 5, 11, "[Kr] 4d¹⁰ 5s¹", 11, "Solid", "Kuno", "Kuno", "Perhiasan mewah, sendok garpu, sirkuit", "Logam konduktor listrik dan panas terbaik nomor satu di seantero alam semesta.", 11, 5),
        ChemicalElement(48, "Cd", "Cadmium", 112.41, "Logam transisi", 5, 12, "[Kr] 4d¹⁰ 5s²", 12, "Solid", "Karl Samuel Leberecht", "1817", "Baterai NiCd tahan lama, pelapis logam", "Sangat efektif menyerap neutron, digunakan sebagai batang kendali reaktor nuklir.", 12, 5),
        ChemicalElement(50, "Sn", "Timah (Tin)", 118.71, "Logam transisi", 5, 14, "[Kr] 4d¹⁰ 5s² 5p²", 4, "Solid", "Kuno", "Kuno", "Solder komponen sirkuit, kaleng makanan", "Logam yang sangat lunak yang menghindarkan pembusukan jika dilapisi pada besi tipis.", 14, 5),
        ChemicalElement(51, "Sb", "Antimon (Antimony)", 121.76, "Metaloid", 5, 15, "[Kr] 4d¹⁰ 5s² 5p³", 5, "Solid", "Kuno", "Kuno", "Bahan tahan api, paduan baterai timbal", "Telah dipakai sejak zaman kuno oleh ratu Mesir sebagai celak mata hitam pekat.", 15, 5),
        ChemicalElement(52, "Te", "Tellurium", 127.6, "Metaloid", 5, 16, "[Kr] 4d¹⁰ 5s² 5p⁴", 6, "Solid", "Franz-Joseph Müller", "1782", "Panel surya film tipisCdTe, aloi baja", "Senyawa organotellurium berbau bawang putih ekstrim yang sangat menyengat pernapasan.", 16, 5),
        ChemicalElement(53, "I", "Yodium (Iodine)", 126.90, "Halogen", 5, 17, "[Kr] 4d¹⁰ 5s² 5p⁵", 7, "Solid", "Bernard Courtois", "1811", "Antiseptik luka, garam beryodium", "Kekurangan yodium memicu penyakit gondok leher membengkak secara misterius.", 17, 5),
        ChemicalElement(54, "Xe", "Xenon", 131.29, "Gas mulia", 5, 18, "[Kr] 4d¹⁰ 5s² 5p⁶", 8, "Gas", "Ramsay & Travers", "1898", "Lampu proyektor bioskop IMAX, anestesi", "Gas mulia berat pertama yang berhasil dibuat bereaksi dengan molekul fluoride fluorida.", 18, 5),

        // Period 6 elements (Cs, Ba, Pt, Au, Hg, Pb, Bi, Po, At, Rn)
        ChemicalElement(55, "Cs", "Cesium", 132.90, "Alkali", 6, 1, "[Xe] 6s¹", 1, "Solid", "Bunsen & Kirchhoff", "1860", "Jam atom standar penentu waktu sedunia", "Logam alkali lunak yang secara harfiah meleleh di tangan hangat karena titik cair 28°C.", 1, 6),
        ChemicalElement(56, "Ba", "Barium", 137.33, "Alkali tanah", 6, 2, "[Xe] 6s²", 2, "Solid", "Carl Wilhelm Scheele", "1772", "Cairan rontgen pencernaan medis, penggali", "Sangat padat dan melahap sisa gas sisa dalam tabung vakum industri.", 2, 6),
        ChemicalElement(78, "Pt", "Platina (Platinum)", 195.08, "Logam transisi", 6, 10, "[Xe] 4f¹⁴ 5d⁹ 6s¹", 10, "Solid", "Antonio de Ulloa", "1735", "Perhiasan mahal, converter emisi gas buang", "Logam mulia ksatria super kokoh tahan karat abadi, tidak bereaksi asam kuat tunggal.", 10, 6),
        ChemicalElement(79, "Au", "Emas (Gold)", 196.97, "Logam transisi", 6, 11, "[Xe] 4f¹⁴ 5d¹⁰ 6s¹", 11, "Solid", "Kuno", "Kuno", "Investasi fisik, perhiasan karat, konektor", "Satu ons emas murni bisa ditempa gepeng mulus menutupi area seluas lapangan basket.", 11, 6),
        ChemicalElement(80, "Hg", "Raksa (Mercury)", 200.59, "Logam transisi", 6, 12, "[Xe] 4f¹⁴ 5d¹0 6s²", 12, "Liquid", "Kuno", "Kuno", "Termometer cairan Celsius, tambal gigi", "Satu-satunya logam cair mengkilap pada suhu kamar, sangat beracun merusak saraf.", 12, 6),
        ChemicalElement(82, "Pb", "Timbal (Lead)", 207.2, "Logam transisi", 6, 14, "[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p²", 4, "Solid", "Kuno", "Kuno", "Aki kendaraan bermotor, tameng radiasi", "Sangat padat sehingga digunakan tameng pelindung teknisi radiologi dari bahaya rontgen.", 14, 6),
        ChemicalElement(83, "Bi", "Bismut (Bismuth)", 208.98, "Logam transisi", 6, 15, "[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p³", 5, "Solid", "Claude Geoffroy", "1753", "Obat mulas lambung Pepto-Bismol, sekring", "Kristalnya tumbuh dalam formasi tangga spiral berwarna pelangi karena oksidasi tipis.", 15, 6),
        ChemicalElement(84, "Po", "Polonium", 209.0, "Metaloid", 6, 16, "[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p⁴", 6, "Solid", "Marie & Pierre Curie", "1898", "Penghilang listrik statis industri presisi", "Sangat radioaktif tinggi pemicu maut tak terlihat, dinamakan dari tanah air Polandia.", 16, 6),
        ChemicalElement(85, "At", "Astatine", 210.0, "Halogen", 6, 17, "[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p⁵", 7, "Solid", "Dale R. Corson", "1940", "Terapi kanker alfa bertarget medis", "Unsur alami paling langka di bumi, diperkirakan kurang dari 30 gram di seluruh kerak bumi.", 17, 6),
        ChemicalElement(86, "Rn", "Radon", 222.0, "Gas mulia", 6, 18, "[Xe] 4f¹⁴ 5d¹⁰ 6s² 6p⁶", 8, "Gas", "Friedrich Ernst Dorn", "1898", "Terapi kanker radiasi terlokalisir", "Gas mulia radioaktif berat tak terlihat yang merembes pelan dari batuan granit tanah.", 18, 6),

        // Period 7 elements (Fr, Ra, U, Pu, Og)
        ChemicalElement(87, "Fr", "Francium", 223.0, "Alkali", 7, 1, "[Rn] 7s¹", 1, "Solid", "Marguerite Perey", "1939", "Penelitian fisika nuklir struktur atom", "Sangat tidak stabil, langsung larut bersuhu panas mendidih tinggi karena radioaktivitas.", 1, 7),
        ChemicalElement(88, "Ra", "Radium", 226.0, "Alkali tanah", 7, 2, "[Rn] 7s²", 2, "Solid", "Marie & Pierre Curie", "1898", "Pencahaya jarum jam kuno yang bercahaya", "Sangat bersinar hijau neon di tempat gelap, penemuannya melahirkan disiplin radioaktivitas.", 2, 7),
        ChemicalElement(92, "U", "Uranium", 238.03, "Aktinida", 7, 3, "[Rn] 5f³ 6d¹ 7s²", 6, "Solid", "Martin Heinrich Klaproth", "1789", "Reaktor listrik nuklir, alutsista", "Bahan bakar raksasa utama pembangkit listrik tenaga nuklir dan bom atom Hiroshima.", 3, 7),
        ChemicalElement(94, "Pu", "Plutonium", 244.0, "Aktinida", 7, 3, "[Rn] 5f⁶ 7s²", 6, "Synthetic", "Glenn T. Seaborg", "1940", "Baterai wahana antariksa Voyager, hulu ledak", "Bahan bakar pembangkit termolistrik radioisotop penjelajah planet luar angkasa.", 3, 7),
        ChemicalElement(118, "Og", "Oganesson", 294.0, "Gas mulia", 7, 18, "[Rn] 5f¹⁴ 6d¹⁰ 7s² 7p⁶", 8, "Synthetic", "Russia-US Collaboration", "2002", "Penelitian laboratorium lanjutan", "Unsur berbobot terberat di tabel periodik, sangat tidak stabil dan meluruh instan.", 18, 7)
    )

    fun getCategoryColor(category: String): Long {
        return when (category.lowercase()) {
            "alkali" -> 0xFF2563EB // Electric Blue
            "alkali tanah" -> 0xFF0D9488 // Cyan
            "logam transisi" -> 0xFF8B5CF6 // Neon Purple
            "metaloid" -> 0xFFD97706 // Bright Amber
            "nonlogam" -> 0xFF10B981 // Emerald Green
            "halogen" -> 0xFFF59E0B // Dark Yellow
            "gas mulia" -> 0xFFEC4899 // Shocking Pink
            "lantanida" -> 0xFF6366F1 // Indigo
            "aktinida" -> 0xFFEF4444 // Red
            else -> 0xFF6B7280 // Grey
        }
    }
}
