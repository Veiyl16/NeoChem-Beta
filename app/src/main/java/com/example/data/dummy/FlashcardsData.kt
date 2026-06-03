package com.example.data.dummy

import com.example.ui.screens.ChemistryCard

object FlashcardsData {
    val categories = listOf(
        "Hukum Dasar Kimia", "Rumus Kimia", "Persamaan Reaksi", "Konsep Mol", "Konsentrasi",
        "Kelarutan", "Koloid", "Termokimia", "Entalpi", "Asam Basa", "Garam", "Hidrokarbon",
        "Alkana", "Alkena", "Alkuna", "Alkohol", "Benzena", "Gugus Fungsi", "Isomer",
        "Protein", "Karbohidrat", "Asam Nukleat"
    )

    fun getCards(): List<ChemistryCard> {
        val list = mutableListOf<ChemistryCard>()

        // 1. Hukum Dasar Kimia (15 cards)
        val hdkFronts = listOf(
            "Hukum Lavoisier (Kekekalan Massa)", "Hukum Proust (Perbandingan Tetap)",
            "Hukum Dalton (Perbandingan Berganda)", "Hukum Gay-Lussac (Perbandingan Volume)",
            "Hipotesis Avogadro", "Pencetus Hukum Lavoisier", "Pencetus Hukum Proust",
            "Massa Sebelum & Sesudah Reaksi", "Perbandingan Massa air H2O (H:O)",
            "Hukum Dalton pada CO & CO2", "Gay-Lussac pada Reaksi Gas",
            "Suhu & Tekanan Sama (Avogadro)", "Hukum Proust pada NaCl",
            "Hukum Kekekalan Massa di Ruang Terbuka", "Mengapa Dalton mengamati Oksigen?"
        )
        val hdkBacks = listOf(
            "Massa zat sebelum reaksi sama dengan massa zat setelah reaksi dalam sistem tertutup.",
            "Perbandingan massa unsur-unsur penyusun suatu senyawa selalu tetap dan tertentu.",
            "Jika dua unsur membentuk lebih dari satu senyawa, perbandingan massa salah satu unsur yang bergabung dengan massa tetap unsur lain berbanding sebagai bilangan bulat sederhana.",
            "Pada suhu dan tekanan sama, volume gas-gas yang bereaksi dan hasil reaksi berbanding sebagai bilangan bulat sederhana.",
            "Gas-gas dengan volume sama pada suhu dan tekanan sama mengandung jumlah molekul yang sama.",
            "Antoine Laurent Lavoisier (1785) membuktikannya lewat pembakaran merkuri oksida.",
            "Joseph Louis Proust (1799) membuktikannya lewat analisis berbagai senyawa tembaga karbonat.",
            "Sesuai hukum Lavoisier, massa akan tetap konstan selama tidak ada materi yang keluar dari sistem.",
            "Perbandingan massa H banding O adalah selalu 1 : 8. Untuk membuat 9g Air, dibutuhkan 1g H dan 8g O.",
            "Massa C tetap 12g. Massa O dalam CO = 16g, CO2 = 32g. Perbandingan O adalah 16:32 = 1:2 (bulat & sederhana).",
            "Ditemukan oleh Joseph Gay-Lussac (1808), berlaku eksklusif untuk spesi berwujud gas saja.",
            "Jumlah molekul berbanding lurus dengan volumenya: V1/n1 = V2/n2.",
            "Perbandingan massa Natrium (Na) terhadap Klorida (Cl) dalam pembentukan garam dapur murni NaCl selalu 1 : 1.54.",
            "Massa tampak berkurang/bertambah karena gas (seperti O2 masuk atau CO2 lepas) tidak terhitung.",
            "Karena Oksigen mudah bereaksi membentuk senyawa oksida ganda (seperti SO2 dan SO3) dengan rasio sederhana."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("hdk_$i", "Hukum Dasar Kimia", hdkFronts[i], hdkBacks[i]))
        }

        // 2. Rumus Kimia (15 cards)
        val rkFronts = listOf(
            "Rumus Kimia Air", "Rumus Molekul Karbondioksida", "Rumus Gas Metana",
            "Rumus Garam Dapur", "Rumus Asam Sulfat", "Rumus Glukosa",
            "Rumus Amonia", "Rumus Silika (Pasir)", "Rumus Etanol",
            "Rumus Natrium Hidroksida", "Rumus Asam Klorida", "Rumus Asam Cuka",
            "Rumus Kimia Ozon", "Rumus Kalsium Karbonat", "Rumus Karbon Monoksida"
        )
        val rkBacks = listOf(
            "H2O • Terdiri dari dua atom Hidrogen dan satu atom Oksigen.",
            "CO2 • Terdiri dari satu atom Karbon dan dua atom Oksigen.",
            "CH4 • Hidrokarbon termuda, alkana rantai tunggal.",
            "NaCl • Kristal ionik tersusun atas ion Na+ dan Cl-.",
            "H2SO4 • Asam pekat kuat, elektrolit aki kendaraan.",
            "C6H12O6 • Monosakarida penghasil energi selular.",
            "NH3 • Gas berbau menyengat, bahan dasar pupuk urea.",
            "SiO2 • Silikon Dioksida, struktur kovalen raksasa.",
            "C2H5OH • Alkohol primer komponen utama bir & antiseptik.",
            "NaOH • Basa kuat, dikenal sebagai soda api pelarut lemak.",
            "HCl • Asam kuat lambung pembersih besi.",
            "CH3COOH • Asam asetat encer penambah cita rasa asam.",
            "O3 • Alotrop oksigen pembendung sinar radiasi UV matahari.",
            "CaCO3 • Senyawa utama penyusun marmer, kapur, & karang gigi.",
            "CO • Gas beracun tak berbau, pengikat hemoglobin darah."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("rk_$i", "Rumus Kimia", rkFronts[i], rkBacks[i]))
        }

        // 3. Persamaan Reaksi (15 cards)
        val prFronts = listOf(
            "Penyetaraan Koefisien", "Reaksi Pembakaran CH4", "Reaksi Fotosintesis",
            "Reaksi Respirasi Sel", "Reaksi Pembentukan Air", "Reaksi Korosi Besi",
            "Reaksi Netralisasi (Asam Basa)", "Reaksi Logam Na dengan Air", "Hukum Penyetaraan Reaksi",
            "Wujud Zat (s, l, g, aq)", "Arti Lambang Reversible (<=>)", "Reaksi Habera-Bosch Urea",
            "Reaksi Pengenalan Karbohidrat", "Kunci Koefisien Reaksi H2 + O2 -> H2O", "Reaksi Cracking Minyak Bumi"
        )
        val prBacks = listOf(
            "Menyamakan jumlah atom di sisi kiri (reaktan) dengan sisi kanan (produk).",
            "CH4 (g) + 2 O2 (g) -> CO2 (g) + 2 H2O (g).",
            "6 CO2 (g) + 6 H2O (l) -> C6H12O6 (s) + 6 O2 (g) (Bantuan cahaya & klorofil).",
            "C6H12O6 (aq) + 6 O2 (g) -> 6 CO2 (g) + 6 H2O (l) + Energi ATP.",
            "2 H2 (g) + O2 (g) -> 2 H2O (l).",
            "4 Fe (s) + 3 O2 (g) + x H2O (l) -> 2 Fe2O3.xH2O (s) (Karat merah besi).",
            "HCl (aq) + NaOH (aq) -> NaCl (aq) + H2O (l) (Menghasilkan garam & air).",
            "2 Na (s) + 2 H2O (l) -> 2 NaOH (aq) + H2 (g) (Eksotermik hebat & meledak).",
            "Hukum Dalton yang menyatakan atom tidak dapat diciptakan atau dimusnahkan dalam reaksi.",
            "s = solid (padat), l = liquid (murni cair), g = gas, aq = aqueous (larutan air).",
            "Menunjukkan reaksi bolak-balik (kesetimbangan dinamis) dimana reaktan dan produk terbentuk simultan.",
            "N2 (g) + 3 H2 (g) <=> 2 NH3 (g) (Katalis besi, suhu tinggi).",
            "C6H12O6 + Fehling -> Endapan Merah Bata Tembaga(I) Oksida Cu2O.",
            "Koefisiennya berturut-turut adalah 2, 1, dan 2 agar setara.",
            "Rantai panjang alkana dipanaskan tanpa oksigen agar retak menjadi alkana & alkena rantai pendek."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("pr_$i", "Persamaan Reaksi", prFronts[i], prBacks[i]))
        }

        // 4. Konsep Mol (15 cards)
        val kmFronts = listOf(
            "Partikel dalam 1 Mol", "Rumus Mol dari Partikel", "Volume Molar STP",
            "Rumus Mol Gas Ideal", "Massa Molar", "Rumus Mol dari Volume STP",
            "Bilangan Avogadro (L)", "Arti STP", "Arti RTP",
            "Mol dari Larutan", "Faktor Konversi Massa", "Definisi Hipotesis Hukum Gas",
            "Massa 1 Mol Air H2O (Ar H=1, O=16)", "Massa 0.5 Mol NaCl (Mr=58.5)", "Hubungan Volume & Suhu Gas"
        )
        val kmBacks = listOf(
            "Tepat 6.022 × 10²³ partikel (atom, molekul, atau ion).",
            "n = Jumlah Partikel / 6.022 × 10²³.",
            "22.4 Liter per mol pada suhu 0°C dan tekanan 1 atm.",
            "n = P × V / (R × T).",
            "Massa satu mol zat yang nilainya sama dengan Ar atau Mr zat tersebut dinyatakan dalam gram/mol.",
            "n = Volume (L) / 22.4 (L/mol).",
            "6.02214 × 10²³ mol⁻¹ yaitu tolok ukur kuantitas mikroskopis ilmiah.",
            "Standard Temperature and Pressure (Suhu 0°C, Tekanan 1 atm).",
            "Room Temperature and Pressure (Suhu 25°C, Tekanan 1 atm, Vmolar = 24 L/mol).",
            "n = M × V (Volume harus menggunakan satuan Liter).",
            "Massa = mol × Mr atau Mr = massa / mol.",
            "Gas bervolume sama memiliki mol sama di kondisi temperatur & tekanan yang sesuai.",
            "Mr Air = (2 × 1) + 16 = 18. Jadi, massa 1 mol Air adalah 18 gram.",
            "Massa = 0.5 × 58.5 = 29.25 gram.",
            "Hukum Charles: V1 / T1 = V2 / T2 (Tekanan konstan, Suhu dalam satuan Kelvin)."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("km_$i", "Konsep Mol", kmFronts[i], kmBacks[i]))
        }

        // 5. Konsentrasi (15 cards)
        val koFronts = listOf(
            "Definisi Molaritas (M)", "Definisi Molalitas (m)", "Fraksi Mol (X)",
            "Kadar Persen Massa (% w/w)", "Kadar Persen Volume (% v/v)", "Konsentrasi PPM (Parts Per Million)",
            "Rumus Pengenceran", "Molaritas Campuran", "Hubungan Molaritas & Massa Jenis",
            "Kelarutan Saturated", "Definisi Normalitas (N)", "Menghitung Fraksi Mol Terlarut",
            "Satuan Molalitas", "Pengaruh Volume pada Molaritas", "Rumus Fraksi Mol Pelarut (Xp)"
        )
        val koBacks = listOf(
            "Jumlah mol zat terlarut dalam 1 liter larutan (satuan: M atau mol/L).",
            "Jumlah mol zat terlarut dalam 1 kilogram (1000g) pelarut murni.",
            "Perbandingan mol zat tertentu dengan mol total dalam campuran (tanpa satuan, Xa + Xb = 1).",
            "Gram zat terlarut per 100 gram total larutan: (massa terlarut / massa total) × 100%.",
            "mL zat terlarut per 100 mL total larutan: (volume terlarut / volume total) × 100%.",
            "Kadar bagian per sejuta: (massa terlarut dalam mg) / (massa larutan dalam kg).",
            "V1 × M1 = V2 × M2 (Mol sebelum pengenceran sama dengan mol sesudah pengenceran).",
            "Mcamp = (V1.M1 + V2.M2) / (V1 + V2).",
            "M = (Karakteristik % × Massa Jenis × 10) / Mr.",
            "Larutan tepat jenuh, dimana pelarut tidak sanggup lagi melarutkan zat tambahan.",
            "Jumlah ekivalen zat terlarut per liter larutan: N = M × valensi.",
            "Xt = nt / (nt + np).",
            "mol/kg atau sering disimbolkan m.",
            "Jika volume air bertambah (pengenceran), molaritas otomatis turun secara berbanding terbalik.",
            "Xp = np / (nt + np) (mol pelarut dibagi total mol)."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("ko_$i", "Konsentrasi", koFronts[i], koBacks[i]))
        }

        // 6. Kelarutan (15 cards)
        val keFronts = listOf(
            "Arti Kelarutan (s)", "Konstanta KSP", "Hubungan s dan KSP pada AgCl",
            "Hubungan s dan KSP pada CaF2", "Efek Ion Senama", "Syarat Mengendap (Qsp > Ksp)",
            "Syarat Tepat Jenuh (Qsp = Ksp)", "Syarat Belum Mengendap (Qsp < Ksp)", "Faktor Suhu pada Kelarutan",
            "KSP dari Ag2CrO4", "Definisi Qsp", "Pelarutan Garam",
            "Kelarutan senyawa Hidroksida", "Garam Halida yang Sukar Larut", "Pengaruh pH pada Senyawa Basa Lemah"
        )
        val keBacks = listOf(
            "Konsentrasi maksimum zat terlarut yang dapat larut dalam suatu pelarut pada suhu tertentu.",
            "Hasil kali kelarutan, yaitu konstanta produk ion terlarutan dipangkatkan koefisiennya.",
            "Ksp = s² karena AgCl -> Ag+ + Cl-.",
            "Ksp = 4s³ karena CaF2 -> Ca2+ + 2F-.",
            "Menambahkan ion yang sejenis ke dalam larutan akan menurunkan tingkat kelarutan zat tersebut.",
            "Qsp > Ksp • Terbentuk endapan padat di dasar wadah.",
            "Qsp = Ksp • Kondisi larutan tepat jenuh, hampir mengendap.",
            "Qsp < Ksp • Larutan belum mengendap, zat masih larut sepenuhnya.",
            "Suhu naik umumnya meningkatkan kelarutan padatan, namun menurunkan kelarutan gas.",
            "Ksp = 4s³ karena Ag2CrO4 -> 2Ag+ + CrO4^2-.",
            "Kuosien produk ion, dinilai sama seperti rumus Ksp namun dalam kondisi konsentrasi sesaat.",
            "Proses disosiasi kisi kristal ionik padat menjadi ion berair bebas bergerak.",
            "Senyawa hidroksida logam transisi (seperti Fe(OH)3) memiliki Ksp sangat kecil (mudah mengendap).",
            "AgCl, PbCl2, dan Hg2Cl2 adalah golongan garam halida sukar larut.",
            "pH tinggi (basa banyak OH-) memperbesar ion senama OH-, otomatis kelarutan basa lemah turun."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("ke_$i", "Kelarutan", keFronts[i], keBacks[i]))
        }

        // 7. Koloid (15 cards)
        val klFronts = listOf(
            "Definisi Koloid", "Ukuran Partikel Koloid", "Efek Tyndall",
            "Gerak Brown", "Adsorpsi", "Koagulasi",
            "Koloid Pelindung", "Elektroforesis", "Emulsi",
            "Aerosol Cair", "Aerosol Padat", "Sol Padat",
            "Liofil vs Liofob", "Susu sebagai Koloid", "Dialisis Ginjal"
        )
        val klBacks = listOf(
            "Sistem dispersi berukuran antara larutan sejati dan suspensi kasar.",
            "Berkisar antara 1 nanometer (nm) hingga 100 nanometer (nm).",
            "Peristiwa penghamburan cahaya oleh partikel koloid (contoh: sorot lampu bioskop berkabut).",
            "Gerakan acak zig-zag partikel koloid akibat tumbukan tak merata dengan pelarut.",
            "Penyerapan muatan ion atau molekul asing pada permukaan koloid (contoh: Norit mengobati sakit perut).",
            "Penggumpalan partikel koloid akibat netralisasi muatan (contoh: tawas menjernihkan air sungai).",
            "Koloid yang ditambahkan untuk menjaga kesetimbangan koloid lain agar tidak mengendap.",
            "Pergerakan partikel koloid bermuatan dalam medan listrik menuju elektrode berlawanan.",
            "Sistem koloid dengan fase terdispersi cair dan medium pendispersi cair (contoh: susu, santan).",
            "Dispersi zat cair dalam medium gas (contoh: kabut awan, hairspray rapi).",
            "Dispersi zat padat dalam medium gas (contoh: asap knalpot, debu vulkanis).",
            "Dispersi zat padat dalam medium padat (contoh: kaca berwarna, logam paduan).",
            "Liofil: koloid suka pelarut (contoh: sabun, gelatin). Liofob: benci pelarut (contoh: sol belerang).",
            "Fase terdispersi lemak cair dalam medium pendispersi air murni dengan emulgator kasein.",
            "Pembersihan darah dari sisa metabolisme yang lolos penyaring alamiah menggunakan asas dialisis koloid."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("kl_$i", "Koloid", klFronts[i], klBacks[i]))
        }

        // 8. Termokimia (15 cards)
        val tkFronts = listOf(
            "Definisi Termokimia", "Asas Kekekalan Energi", "Sistem Terbuka",
            "Sistem Tertutup", "Sistem Terisolasi", "Reaksi Eksoterm",
            "Reaksi Endoterm", "Tanda Delta H (dH) Eksoterm", "Tanda Delta H (dH) Endoterm",
            "Kalor Jenis air (c)", "Kapasitas Kalor (C)", "Kalorimeter",
            "Gaya Gesek vs Kerja Energi", "Persamaan Termokimia", "Nilai dH Reaksi Balik"
        )
        val tkBacks = listOf(
            "Cabang kimia yang mempelajari perubahan kalor atau energi yang menyertai reaksi kimia.",
            "Hukum I Termodinamika: Energi tidak dapat diciptakan/dimusnahkan, hanya diubah bentuk.",
            "Sistem dimana massa materi dan energi panas dapat bertukar bebas dengan lingkungan.",
            "Sistem dimana hanya panas energi yang dapat bertukar, sedangkan materi tertahan.",
            "Sistem tanpa pertukaran materi maupun panas sama sekali dengan lingkungan (contoh: termos).",
            "Melepas kalor dari sistem menuju lingkungan, biasanya suhu wadah meningkat panas.",
            "Menyerap kalor dari lingkungan masuk sistem, suhu wadah menurun dingin.",
            "dH < 0 (bernilai negatif) karena entalpi produk lebih kecil dibanding reaktan.",
            "dH > 0 (bernilai positif) karena sistem menyerap kalor sehingga entalpi naik.",
            "Jumlah kalor yang dibutuhkan untuk menaikkan suhu 1g air sebesar 1°C (nilai: 4.18 J/g°C).",
            "Jumlah kalor yang dibutuhkan untuk menaikkan suhu komponen sebesar 1°C tanpa memandang massa.",
            "Alat spesifik berisolasi tebal untuk mengukur kuantitas transfer kalor reaksi kimia.",
            "Kalor selalu mengalir alami dari area bersuhu tinggi ke bersuhu rendah.",
            "Persamaan reaksi lengkap dengan keterangan wujud fisis zat dan nilai perubahan dH.",
            "Jika reaksi dibalik arah, maka tanda aljabar dH wajib dibalik (positif menjadi negatif & sebaliknya)."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("tk_$i", "Termokimia", tkFronts[i], tkBacks[i]))
        }

        // 9. Entalpi (15 cards)
        val enFronts = listOf(
            "Arti Entalpi (H)", "Entalpi Pembentukan Standar (dHf)", "Entalpi Penguraian Standar (dHd)",
            "Entalpi Pembakaran Standar (dHc)", "Hukum Hess", "Energi Ikatan Rata-rata",
            "Menghidupkan Hukum Hess", " dHf Unsur Bebas (O2, N2, Fe)", "Rumus dH Reaksi dari dHf",
            "Energi Aktivasi (Ea)", "Energi Atomisasi", "Metode Kalorimetri",
            "Entalpi Reaksi Penggaraman", "Kaitan Ikatan Kuat & Entalpi", "dH Reaksi dari Energi Ikatan"
        )
        val enBacks = listOf(
            "Kandungan energi panas total yang tersimpan dalam suatu zat pada tekanan tetap.",
            "dH untuk pembentukan 1 mol senyawa langsung dari unsur-unsur murni standarnya.",
            "dH untuk menguraikan 1 mol senyawa menjadi unsur murni penyusunnya (kebalikan dHf).",
            "dH untuk pembakaran sempurna 1 mol zat dengan gas oksigen melimpah.",
            "Perubahan entalpi reaksi selalu sama, tidak peduli apakah reaksi berlangsung satu atau banyak tahap.",
            "Energi yang diperlukan untuk memutuskan 1 mol ikatan kovalen berwujud gas menjadi atom gas bebas.",
            "Dirumuskan oleh Germain Hess (1840), entalpi hanya tergantung keadaan awal dan akhir.",
            "Selalu bernilai Nol (0 kJ/mol) karena tidak ada usaha pembentukan di alam bebas.",
            "dH Reaksi = Jumlah dHf (Produk) - Jumlah dHf (Reaktan).",
            "Energi rintangan minimum yang harus dilewati reaktan agar tabrakan molekul menghasilkan reaksi.",
            "Energi pemutusan semua ikatan dalam senyawa untuk membentuk atom gas netral tunggal.",
            "q = m × c × dT. dH diprediksi dari nilai q dibagi kuantitas mol reaktan.",
            "Kalor penetralan asam-basa kuat menghasilkan air murni: selalu berkisar -57 kJ/mol.",
            "Semakin kuat ikatan kimia suatu zat, semakin melimpah asupan energi dH pemutusan yang dibutuhkan.",
            "dH Reaksi = Jumlah Energi Pemutusan (Reaktan) - Jumlah Energi Pembentukan (Produk)."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("en_$i", "Entalpi", enFronts[i], enBacks[i]))
        }

        // 10. Asam Basa (15 cards)
        val abFronts = listOf(
            "Asam Arhenius", "Basa Arrhenius", "Asam Bronsted-Lowry",
            "Basa Bronsted-Lowry", "Asam Lewis", "Basa Lewis",
            "pH Skala Asam", "pH Skala Basa", "Indikator Lakmus",
            "pH air murni 25°C", "Asam Kuat vs Lemah", "Basa Kuat vs Lemah",
            "Rumus Ka Asam Lemah", "Definisi Titrasi", "Indikator Universil"
        )
        val abBacks = listOf(
            "Zat yang melepaskan ion Hidrogen (H+) ketika dilarutkan ke air murni.",
            "Zat yang melepaskan ion Hidroksida (OH-) ketika dilarutkan ke air murni.",
            "Spesi yang bertugas mendonorkan proton (ion H+) dalam pertukaran kimia.",
            "Spesi yang bertugas menerima (akseptor) proton (ion H+) dalam pertukaran kimia.",
            "Akseptor atau penerima pasangan elektron sunyi bebas (PEB).",
            "Donor atau pemberi pasangan elektron sunyi bebas (PEB) penyusun ikatan koordinasi.",
            "Sangat asam bila pH < 7. Semakin kecil nilainya, keasaman kian pekat.",
            "Sangat basa bila pH > 7. Semakin besar nilainya, kebasaan kian pekat.",
            "Lakmus biru berubah merah oleh asam. Lakmus merah berubah biru oleh basa.",
            "Tepat bernilai 7 (netral alami) karena kesetimbangan Kw = 10^-14.",
            "Kuat terionisasi 100% (seperti HCl, HNO3, H2SO4). Lemah terionisasi sebagian.",
            "Basa kuat larut sempurna (seperti NaOH, KOH, Ca(OH)2). Basa lemah terionisasi mini (seperti NH3).",
            "Ka = [H+]² / Ma. pH asam lemah ditentukan dari nilai tetapan disosiasi Ka tersebut.",
            "Metode penentuan kadar asam atau basa murni menggunakan larutan standar yang diketahui tepat kadarnya.",
            "Campuran zat pewarna kimia yang sensitif berubah warna melambangkan gradasi pH 1-14."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("ab_$i", "Asam Basa", abFronts[i], abBacks[i]))
        }

        // 11. Garam (15 cards)
        val gaFronts = listOf(
            "Garam Netral", "Garam Asam", "Garam Basa",
            "Hidrolisis Sebagian (Parsial)", "Hidrolisis Total", "Tidak Terhidrolisis",
            "Contoh Garam Netral, NaCl", "Contoh Garam Asam, NH4Cl", "Contoh Garam Basa, CH3COONa",
            "Rumus pH Garam Asam", "Rumus pH Garam Basa", "Ion yang Menghidrolisis Air",
            "Asal Mula Garam Dapur", "Sifat Larutan Garam Alum", "Pupuk Amonium Sulfat"
        )
        val gaBacks = listOf(
            "Terbentuk dari asam kuat dan basa kuat, pH larutan tepat 7 (tidak terhidrolisis).",
            "Terbentuk dari asam kuat dan basa lemah, pH larutan < 7 (mengalami hidrolisis kation).",
            "Terbentuk dari asam lemah dan basa kuat, pH larutan > 7 (mengalami hidrolisis anion).",
            "Terjadi bila salah satu ion penyusun (lemah) bereaksi memecah air sedangkan ion kuat aman.",
            "Terjadi bila asam penyusun lemah dan basa penyusun juga lemah, pH dipengaruhi perbandingan Ka & Kb.",
            "Garam bentukan komponen kuat sepenuhnya tidak bereaksi karena hidrat ion stabil.",
            "NaCl tidak mengubah warna kertas lakmus merah maupun biru tetap murni.",
            "NH4Cl berasal dari NH3 (lemah) dan HCl (kuat), terhidrolisis parsial menghasilkan H+.",
            "CH3COONa berasal dari CH3COOH (lemah) dan NaOH (kuat), menghasilkan ion OH+.",
            "[H+] = akar( (Kw/Kb) × Mgaram × valensi ).",
            "[OH-] = akar( (Kw/Ka) × Mgaram × valensi ).",
            "Hanya kation atau anion yang berasal dari komponen lemah (asam/basa lemah) saja.",
            "Reaksi asam klorida HCl dengan natrium hidroksida NaOH menghasilkan garam murni & air.",
            "Bersifat asam karena tawas melepaskan kation aluminium terhidrolisis menyerap hidroksida air.",
            "ZA (Zwavelzure Amoniak) berkarakter pupuk asam karena hidrolisis NH4+ melepaskan proton."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("ga_$i", "Garam", gaFronts[i], gaBacks[i]))
        }

        // 12. Hidrokarbon (15 cards)
        val hcFronts = listOf(
            "Keistimewaan Karbon (C)", "Hidrokarbon Jenuh", "Hidrokarbon Tak Jenuh",
            "Karbon Primer (C1)", "Karbon Sekunder (C2)", "Karbon Tersier (C3)",
            "Karbon Kuartener (C4)", "Alifatik vs Siklik", "Aromatis",
            "Reaksi Substitusi Hidrokarbon", "Reaksi Adisi Hidrokarbon", "Reaksi Eliminasi Hidrokarbon",
            "Deret Homolog", "Fraksi Minyak Bumi Elpiji", "Pembakaran Sempurna Hidrokarbon"
        )
        val hcBacks = listOf(
            "Memiliki 4 elektron valensi yang memungkinkannya mengikat 4 atom lain menyusun rantai panjang.",
            "Hanya memiliki ikatan tunggal C-C (golongan Alkana). Stabil dan tidak mudah adisi.",
            "Memiliki ikatan rangkap dua C=C (Alkena) atau rangkap tiga C///C (Alkuna). Reaktif.",
            "Atom C yang terikat langsung pada 1 atom karbon tetangga lainnya.",
            "Atom C yang mengikat langsung 2 atom karbon tetangga lainnya.",
            "Atom C yang mengikat langsung 3 atom karbon tetangga lainnya.",
            "Atom C yang dikelilingi dan mengikat langsung 4 atom karbon tetangga lainnya.",
            "Alifatik berbentuk rantai terbuka (lurus/bercabang). Siklik rantai melingkar tertutup.",
            "Senyawa lingkar datar dengan ikatan rangkap berseling bersonansi (seperti Benzena).",
            "Reaksi penggantian atom H oleh atom/gugus lain tanpa merusak ikatan tunggal.",
            "Reaksi pemutusan ikatan rangkap (jenuhkan) akibat masuknya atom baru pembuka jalur.",
            "Reaksi pembentukan ikatan rangkap dari tunggal (penjenuhan terbalik) dengan melepaskan molekul kecil.",
            "Keluarga senyawa karbon dengan rumus umum sama, selisih antar anggotanya adalah CH2.",
            "Didominasi gas propana (C3H8) dan butana (C4H10) yang dipadatkan.",
            "Menghasilkan gas CO2 dan uap air H2O serta energi panas kalor tinggi."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("hc_$i", "Hidrokarbon", hcFronts[i], hcBacks[i]))
        }

        // 13. Alkana (15 cards)
        val alkFronts = listOf(
            "Rumus Umum Alkana", "Metana", "Etana",
            "Propana", "Butana", "Pentana",
            "Heksana", "Heptana", "Oktana",
            "Sifat Fisik Alkana Rantai Pendek", "Alkana Rantai Panjang", "Gugus Alkil",
            "Gaya London Alkana", "Kegunaan Utama Alkana", "Tata Nama Cabang Alkana"
        )
        val alkBacks = listOf(
            "C_n H_2n+2 • Ikatan tunggal jenuh.",
            "CH4 • Anggota alkana paling sederhana, penyusun utama gas alam.",
            "C2H6 • Alkana dengan 2 atom karbon pembentuk senyawa.",
            "C3H8 • Komponen kunci bahan bakar tabung gas LPG.",
            "C4H10 • Gas cair korek api saku butana.",
            "C5H12 • Alkana cair berdensitas sangat ringan.",
            "C6H14 • Pelarut nonpolar laboratorium yang ampuh.",
            "C7H16 • Indikator ketukan rendah pada kalibrasi bensin.",
            "C8H18 • Isomernya Isooktana adalah acuan mutu kualitas angka oktan bensin.",
            "C1 sampai C4 berwujud gas pada temperatur kamar.",
            "C5 sampai C17 berwujud cair, C18 ke atas berwujud padat keras lilin parafin.",
            "Alkana kehilangan satu atom H, dirumuskan C_n H_2n+1 (contoh: Metil -CH3, Etil -C2H5).",
            "Gaya tarik van der Waals lemah antarmolekul nonpolar yang meningkat bersama panjang rantai.",
            "Bahan bakar termal generator pembangkit, kompor gas, dan pelumas otomotif.",
            "Tentukan rantai terpanjang, nomor terkecil dimulai dari ujung terdekat dengan cabang utama."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("alk_$i", "Alkana", alkFronts[i], alkBacks[i]))
        }

        // 14. Alkena (15 cards)
        val alkenFronts = listOf(
            "Rumus Umum Alkena", "Etena (Etilena)", "Propena",
            "Butena", "Ikatan Rangkap Dua C=C", "Adisi Hidrogen Alkena",
            "Adisi Halogen Alkena", "Pembuatan Plastik PP/PE", "Aturan Markovnikov",
            "Isomer Posisi Alkena", "Bentuk Cis-Trans Alkena", "Alkena dari Dehidrasi Alkohol",
            "Sifat Ikatan Pi (π)", "Pengembangan Gas Etena Alami", "Uji Air Bromin Alkena"
        )
        val alkenBacks = listOf(
            "C_n H_2n • Hidrokarbon tidak jenuh dengan satu ikatan rangkap dua.",
            "C2H4 • Bahan baku penting pembuatan plastik polietilena.",
            "C3H6 • Senyawa dasar untuk produksi polimer polipropilena wadah makanan.",
            "C4H8 • Alkena yang memiliki isomer cis-trans spasial.",
            "Tersusun dari satu ikatan sigma kuat dan satu ikatan pi lemah mudah putus.",
            "Reaksi hidrogenasi mengubah alkena menjadi alkana murni dibantu katalis Ni.",
            "Reaksi pembukaan rangkap oleh halogen (Cl2/Br2) menghasilkan alkana dihalida.",
            "Menggabungkan ribuan monometer etena lewat polimerisasi adisi menghasilkan kantong plastik.",
            "Saat asam halida H-X masuk, atom H akan terikat pada atom C rangkap yang mengikat H lebih banyak.",
            "Perbedaan lokasi ikatan rangkap dalam rantai utama (seperti 1-butena vs 2-butena).",
            "Isomer geometri: Cis (searah bersisian) dan Trans (berseberangan diagonal).",
            "Etanol dipanaskan bersama katalis asam sulfat H2SO4 menghasilkan gas Etena.",
            "Ikatan hasil tumpang tindih orbital p sejajar, lemah melengkung reaktif terhadap elektrofil.",
            "Hormon tumbuhan pembantu matangkan buah-buahan hijau secara cepat.",
            "Alkena akan menghilangkan warna cokelat kemerahan air bromin (proses brominasi)."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("alken_$i", "Alkena", alkenFronts[i], alkenBacks[i]))
        }

        // 15. Alkuna (15 cards)
        val alkunFronts = listOf(
            "Rumus Umum Alkuna", "Etuna (Asetilena)", "Propuna",
            "Butuna", "Ikatan Rangkap Tiga C///C", "Pembuatan Gas Asetilena",
            "Adisi Gas Klorida Alkuna", "Suhu Nyala Las Karbit", "Geometri Alkuna (Linier)",
            "Tata Nama Alkuna", "Asam Karbida", "Alkuna Terminal",
            "Polaritas Alkuna", "Isomer Rantai Propuna", "Metode Adisi Klorida Berlebih"
        )
        val alkunBacks = listOf(
            "C_n H_2n-2 • Hidrokarbon tak jenuh dengan satu ikatan rangkap tiga.",
            "C2H2 • Gas pengisi tabung las karbit yang sangat mudah terbakar.",
            "C3H4 • Alkuna berbentuk gas yang sering dipakai dalam sintesis organik.",
            "C4H6 • Struktur Alkuna yang berisomer posisi.",
            "Terdiri dari satu ikatan sigma yang kuat dan dua ikatan pi yang lemah.",
            "Mereaksikan batu karbit (Kalsium Karbida CaC2) dengan air murni H2O.",
            "Reaksi adisi HX bertahap: pertama menghasilkan alkena halida, lalu alkana dihalida.",
            "Gas etuna dibakar oksigen mengeluarkan panas ekstrim hingga 3000°C pemotong lembaran baja.",
            "Atom C berhibridisasi sp dengan sudut ikatan lurus tepat 180 derajat.",
            "Nama diakhiri '-una'. Posisi ikatan rangkap tiga diberi nomor sekecil mungkin.",
            "Batu kristal CaC2 penghancur air yang melepaskan asetilena.",
            "Alkuna yang memiliki ikatan rangkap tiga di ujung rantai, bersifat sedikit asam.",
            "Sedikit lebih polar dibanding alkana/alkena karena elektronegativitas tinggi C hibrida sp.",
            "Propuna tidak memiliki isomer posisi karena posisi rangkap selalu di nomor 1.",
            "Adisi 2 mol Cl2 menghasilkan alkana tetrahalida (seperti 1,1,2,2-tetrakloroetana)."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("alkun_$i", "Alkuna", alkunFronts[i], alkunBacks[i]))
        }

        // 16. Alkohol (15 cards)
        val alkoFronts = listOf(
            "Rumus Umum Alkohol", "Metanol (CH3OH)", "Etanol (C2H5OH)",
            "Gliserol (Gliserin)", "Alkohol Primer", "Alkohol Sekunder",
            "Alkohol Tersier", "Oksidasi Alkohol Primer", "Oksidasi Alkohol Sekunder",
            "Alkohol Tersier Teroksidasi?", "Esterifikasi", "Ikatan Hidrogen Alkohol",
            "Destilasi Alkohol", "Alkohol Denaturasi", "Glukosa Direfmentasi"
        )
        val alkoBacks = listOf(
            "C_n H_2n+1 OH • Memiliki gugus fungsi Hidroksil (-OH) terikat alkil.",
            "Alkohol kayu, sangat beracun keras penyebab kebutaan jika diminum.",
            "Alkohol gandum berasa hangat, desinfektan medis dan antiseptik luar.",
            "Alkohol trihidroksi (3 gugus -OH), bahan baku pelembab kulit kosmetik.",
            "Gugus -OH terikat pada atom C primer (contoh: 1-propanol).",
            "Gugus -OH terikat pada atom C sekunder (contoh: 2-propanol).",
            "Gugus -OH terikat pada atom C tersier (contoh: 2-metil-2-propanol).",
            "Menghasilkan Aldehida (Alkanal), jika teroksidasi lanjut membentuk Asam Karboksilat.",
            "Menghasilkan Keton (Alkanon) yang stabil terhadap oksidasi lanjutan.",
            "Tidak dapat dioksidasi oleh oksidator lemah/sedang karena tidak mengikat hidrogen alfa.",
            "Reaksi alkohol dengan asam karboksilat menghasilkan ester wangi buah-buahan.",
            "Gaya tarik antar hidroksil polar membuat titik didih alkohol jauh lebih tinggi dibanding eter.",
            "Memisahkan campuran alkohol & air bersandar pada selisih titik didih etanol (78°C) & air.",
            "Etanol murni Sengaja ditambah zat racun rasa pahit agar tidak dikonsumsi ilegal.",
            "Ragi khamir merombak karbohidrat monosakarida menghasilkan etanol & pelepasan CO2."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("alko_$i", "Alkohol", alkoFronts[i], alkoBacks[i]))
        }

        // 17. Benzena (15 cards)
        val bzFronts = listOf(
            "Rumus Kimia Benzena", "Model Benzena Kekule", "Resonansi Elektron Benzena",
            "Kestabilan Kimia Benzena", "Substitusi Elektrofili Benzena", "Toluena",
            "Anilina", "Fenol", "Aspirin obat sakit kepala",
            "TNT Peledak Dahsyat", "Metil Salisilat", "Asam Benzoat",
            "Karsinogenik Benzena", "Substituen Orto (1,2)", "Substituen Meta (1,3) & Para (1,4)"
        )
        val bzBacks = listOf(
            "C6H6 • Struktur cincin aromatis segi enam beraturan.",
            "Friedrich Kekule mengusulkan struktur cincin segi enam ikatan tunggal ganda berseling.",
            "Awan elektron pi terdelokalisasi merata di seluruh cincin, terwakili lingkaran di dalam heksagon.",
            "Sangat stabil, tangguh tidak mudah adisi meskipun memiliki ikatan rangkap formal.",
            "Reaksi khas benzena, mengganti atom H dengan elektrofil demi jaga resonansi cincin tetap utuh.",
            "Benzena dengan gugus metil (-CH3), bahan baku pewarna tawas.",
            "Benzena bergugus amina (-NH2), senyawa dasar industri obat & zat warna azo.",
            "Benzena bergugus hidroksil (-OH), pelarut antiseptik kuat korosif.",
            "Asam asetilsalisilat, obat penurun panas sintesis turunan asam salisilat fenolik.",
            "Tri-Nitro-Toluena (TNT), fusi toluena dengan tiga gugus nitro eksplosif tinggi.",
            "Kondensasi asam salisilat dengan metanol menghasilkan krim oles pereda nyeri otot balm.",
            "Pengawet makanan asam organik penahan jamur pembusuk saus kecap.",
            "Sifat racun uap murni benzena dalam tubuh pemicu kerusakan sumsum & leukemia.",
            "Tata letak dua cabang bersebelahan di karbon benzena nomor 1 dan 2.",
            "Meta terletak di nomor 1 dan 3. Para terletak berseberangan tegak lurus nomor 1 dan 4."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("bz_$i", "Benzena", bzFronts[i], bzBacks[i]))
        }

        // 18. Gugus Fungsi (15 cards)
        val gfFronts = listOf(
            "Eter (Alkoksi Alkana)", "Aldehida (Alkanal)", "Keton (Alkanon)",
            "Asam Karboksilat", "Ester (Alkil Alkanoat)", "Gugus Amina",
            "Gugus Amida", "Gugus Halida (Haloalkana)", "Uji Fehling & Toleran Aldehida",
            "Aroma Buah Karakter Ester", "Bahan Kuku Pembersih Aseton", "Obat Bius Eter Tradisional",
            "Formalin Pengawet Hewan", "Sabun dari Ester Trigliserida", "Reaksi Saponifikasi"
        )
        val gfBacks = listOf(
            "-O- • Terikat di sela dua rantai alkil, tidak memiliki hidrogen asam.",
            "-CHO • Mengandung karbonil di ujung rantai dengan ikatan C=O.",
            "-CO- • Karbonil terletak di tengah diapit dua rantai alkil.",
            "-COOH • Gugus asam organik terpolar kuat.",
            "-COOR • Turunan asam karboksilat berbau harum buah-buahan.",
            "-NH2 • Berbau amis anyir senyawa basa organik nitrogen.",
            "-CONH2 • Unit ikatan penghubung polimer nilon dan protein alami.",
            "-F, -Cl, -Br, -I • Karbon mengikat satu/lebih unsur halogen beracun.",
            "Membedakan dengan Keton: Aldehida menyingkap cermin perak mengilap pereduksi.",
            "Pisang (amil asetat), Apel (metil butirat), Jeruk (oktil asetat) tercium harum.",
            "Dimetil keton (Aseton), pelarut cat kuku kuteks cair super cepat menguap.",
            "Dietil eter pernah populer untuk mengobati bedah bedah tanpa rasa sakit.",
            "Formaldehida gas berkadar 37% dilarutkan air pencegah dekomposisi jaringan protein.",
            "Minyak kelapa direaksikan KOH/NaOH memecah rantai gliserida melarutkan lemak kotor.",
            "Hidrolisis basa minyak tumbuhan memanen gliserol bebas & sabun garam karboksilat."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("gf_$i", "Gugus Fungsi", gfFronts[i], gfBacks[i]))
        }

        // 19. Isomer (15 cards)
        val isoFronts = listOf(
            "Definisi Isomer", "Isomer Rantai (Kerangka)", "Isomer Posisi",
            "Isomer Fungsi", "Isomer Geometri", "Isomer Optis Aktif",
            "Pasangan Isomer Eter & Alkohol", "Pasangan Isomer Aldehida & Keton", "Pasangan Isomer Asam & Ester",
            "Kiralitas Karbon (C Kiral)", "Enantiomer", "Cahaya Terpolarisasi Isomer",
            "Kuantitas Isomer Butana", "Isomer Pentana C5H12", "Suhu Didih Isomer Cabang"
        )
        val isoBacks = listOf(
            "Senyawa-senyawa yang memiliki rumus molekul sama, namun berbeda rumus struktur fisis.",
            "Perbedaan kerangka utama karbon (seperti N-Butana dengan 2-metilpropana).",
            "Perbedaan lokasi gugus fungsi atau ikatan rangkap (seperti 1-propanol vs 2-propanol).",
            "Rumus molekul sama tapi memiliki gugus fungsi yang berbeda kelas.",
            "Isomer ruang cis-trans akibat rotasi terhalang ikatan rangkap dua karbon.",
            "Senyawa yang dapat memutar bidang cahaya karena memiliki atom karbon asimetris.",
            "Rumus umum C_n H_2n+2 O dimiliki alkohol dan eter secara setara bersama.",
            "Rumus umum C_n H_2n O dimiliki aldehida dan keton secara setara bersama.",
            "Rumus umum C_n H_2n O2 dimiliki asam karboksilat dan ester secara setara bersama.",
            "Atom C pengikat 4 gugus yang berbeda-beda sama sekali (tidak simetris).",
            "Pasangan isomer optis bayangan cermin yang tidak saling tumpang tindih.",
            "Uji polarimeter menentukan rotasi isomer berlawanan (dextro memutar kanan, levo kiri).",
            "Hanya memiliki 2 isomer fungsional: n-butana lurus dan isobutana bercabang.",
            "Memiliki 3 isomer struktur: n-pentana, isopentana, dan neopentana bulat.",
            "Semakin banyak cabang, titik didih makin rendah karena luas kontak van der waals menciut."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("iso_$i", "Isomer", isoFronts[i], isoBacks[i]))
        }

        // 20. Protein (15 cards)
        val prtFronts = listOf(
            "Monometrik Protein", "Ikatan Peptida", "Asam Amino Esensial",
            "Asam Amino Non-Esensial", "Struktur Primer Protein", "Struktur Sekunder Protein",
            "Struktur Tersier Protein", "Struktur Kuartener Protein", "Denaturasi Protein",
            "Uji Biuret", "Uji Xantoproteat", "Fungsi Enzim",
            "Hemoglobin Zat Besi", "Koagulasi Putih Telur", "Protein Keratin Rambut"
        )
        val prtBacks = listOf(
            "Asam Amino, tersusun atas gugus amina dan gugus asam karboksilat dwi-polar.",
            "Ikatan kovalen antara gugus karboksilat satu asam amino dengan gugus amina tetangga.",
            "Asam amino penyusun tubuh yang tidak dapat disintesis organ mandiri, wajib ada dari asupan makan.",
            "Asam amino pendukung yang diproduksi mandiri oleh hati tubuh manusia gratis.",
            "Urutan linear sekuens rantai asam amino murni tak berlipat.",
            "Kerangka lipatan lokal akibat jembatan hidrogen membentuk alfa-helix atau beta-sheet.",
            "Lipatan tiga dimensi menyeluruh membentuk globular fungsional terpilin.",
            "Gabungan beberapa rantai polipeptida tersier menjadi kompleks fungsional multipel.",
            "Kerusakan struktur tersier/sekunder protein akibat panas ekstrim, asam pekat, atau guncangan.",
            "Uji warna ungu indikator adanya ikatan peptida protein dalam cairan.",
            "Uji bercak jingga menandakan protein mengandung cincin benzena aromatik asam amino.",
            "Katalis biologi protein spesifik untuk percepat jutaan metabolisme sel tubuh.",
            "Protein kuartener pengangkut gas pernapasan berpusat pada zat besi heme merah.",
            "Suhu panas meregangkan ikatan hidrogen ovalbumin putih telur cair mengeras padat.",
            "Protein struktural tangguh kaya ikatan disulfida penyusun rambut, kuku, dan tanduk."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("prt_$i", "Protein", prtFronts[i], prtBacks[i]))
        }

        // 21. Karbohidrat (15 cards)
        val khFronts = listOf(
            "Monosakarida", "Disakarida", "Polisakarida",
            "Glukosa", "Fruktosa", "Galaktosa",
            "Sukrosa (Gula Meja)", "Laktosa", "Maltosa",
            "Amilum (Pati)", "Selulosa", "Glikogen",
            "Isomer Karbohidrat D & L", "Uji Benedict Reduksi", "Ikatan Glikosidik"
        )
        val khBacks = listOf(
            "Karbohidrat paling sederhana, tidak dapat dihidrolisis lagi (seperti glukosa, fruktosa).",
            "Karbohidrat bentukan dua unit monosakarida lewat eliminasi air H2O murni.",
            "Karbohidrat rantai polimer panjang ribuan glukosa pembangun energi (pati) & sel (selulosa).",
            "Alkoheksosa monosakarida darah paling melimpah penggerak utama sel metabolisme.",
            "Ketoheksosa termanis madu buah, isomer fungsi dari glukosa.",
            "Monosakarida penyusun gula susu hewani galaktosa.",
            "Glukosa + Fruktosa • Gula meja rumah non-pereduksi.",
            "Glukosa + Galaktosa • Gula khas susu mamalia.",
            "Glukosa + Glukosa • Gula gandum hasil kecambah biji.",
            "Polisakarida cadangan energi utama tumbuhan, subur di dalam kentang padi.",
            "Polisakarida dinding sel tumbuhan berserat kuat liat, sukar dicerna saku lambung manusia.",
            "Pati hati & otot cadangan karbohidrat darurat manusia dan hewan mamalia.",
            "Penggolongan letak gugus hidroksil karbon asimetris ujung: Dextro (kanan) & Levo (kiri).",
            "Mereduksi tembaga biru benedict menjadi endapan merah bata Cu2O kecuali gula sukrosa.",
            "Ikatan eter yang menautkan antar unit cincin monosakarida menjadi disakarida."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("kh_$i", "Karbohidrat", khFronts[i], khBacks[i]))
        }

        // 22. Asam Nukleat (15 cards)
        val anFronts = listOf(
            "Komponen Nukleotida", "DNA vs RNA", "Basa Purin",
            "Basa Pirimidin DNA", "Basa Pirimidin RNA", "Ikatan Fosfodiester",
            "Pasangan Adenin (A)", "Pasangan Sitosin (C)", "Gula Deoksiribosa",
            "Gula Ribosa", "Bentuk Double Helix DNA", "Kodogen DNA",
            "Transkripsi", "Translasi", "Mutasi Genetik DNA"
        )
        val anBacks = listOf(
            "Tersusun dari gugus fosfat berpangkat, gula pentosa, dan basa nitrogen organik.",
            "DNA menyimpan pustaka genetik seluler. RNA pelaksana sintesis protein penjelajah.",
            "Basa berstruktur cincin ganda: Adenin (A) dan Guanin (G) yang identik.",
            "Sitosin (C) dan Timin (T) pelengkap purin untai ganda.",
            "Sitosin (C) dan Urasil (U), dimana Urasil menggantikan peran Timin DNA.",
            "Ikatan kuat yang merantai gugus fosfat dengan karbon 3' dan 5' gula pentosa.",
            "Adenin selalu mengikat Timin (A=T) lewat dua jembatan hidrogen lemah.",
            "Sitosin selalu mengikat Guanin (C///G) lewat tiga jembatan hidrogen stabil.",
            "Gula pentosa DNA yang kehilangan satu atom oksigen pada cincin karbon nomor 2.",
            "Gula pentosa murni RNA dengan gugus hidroksil -OH lengkap di seluruh karbon.",
            "Rancang struktur untai ganda sejajar berlawanan yang dipublikasikan Watson & Crick (1953).",
            "Untai cetakan DNA berkode spesifik pemandu transkripsi pembentuk mRNA.",
            "Proses penyalinan kode nukleotida DNA inti sel ke dalam utas molekul baru mRNA.",
            "Penerjemahan kodon triplet mRNA di dalam ribosom menjadi sekuens asam amino protein.",
            "Kesalahan urutan pasang basa nukleotida akibat paparan radiasi pengubah sifat protein."
        )
        for (i in 0..14) {
            list.add(ChemistryCard("an_$i", "Asam Nukleat", anFronts[i], anBacks[i]))
        }

        return list
    }
}
