package com.example.data.dummy

import com.example.data.model.MateriCategory
import com.example.data.model.SubMateri

object ChemistryData {

    val categories = listOf(
        MateriCategory(
            id = "struktur-atom",
            title = "Struktur Atom",
            iconName = "blur_on",
            difficulty = "Pemula",
            estimatedMinutes = 15,
            subMateriList = listOf(
                SubMateri(
                    id = "proton-neutron-elektron",
                    title = "Proton, Neutron, & Elektron",
                    summary = "Mengenal bagian penyusun atom terkecil secara santai.",
                    fullText = "Atom itu bukan bagian paling kecil loh! Di dalam atom ada tiga subpartikel utama:\n\n1. Proton (bermuatan positif (+), bersemayam di pusat inti atom)\n2. Neutron (netral (0), bertugas menstabilkan inti menemani proton)\n3. Elektron (bermuatan negatif (-), berukuran sangat ringan dan melesat super cepat mengitari inti atom)\n\nNomor atom di tabel periodik menunjukkan jumlah proton di dalam inti. Jika atom dalam kondisi netral, jumlah elektron luar akan persis sama dengan jumlah proton.",
                    challengeQuestion = "Jika suatu atom netral memiliki Nomor Atom 6 dan Massa 12, berapa jumlah elektron yang mengitarinya?",
                    challengeOptions = listOf("6 elektron", "12 elektron", "18 elektron", "0 elektron/habis"),
                    challengeAnswerIndex = 0,
                    explanation = "Pada atom stabil/netral, jumlah Elektron terluar selalu sama dengan Nomor Atom (Jumlah Proton), yaitu 6."
                ),
                SubMateri(
                    id = "konfigurasi-elektron",
                    title = "Konfigurasi Elektron Bohr",
                    summary = "Aturan orbital penyebaran elektron di kulit atom.",
                    fullText = "Elektron tidak menyebar acak, melainkan melayang di lintasan bertingkat mirip tangga yang disebut kulit atom.\n\nKulit pertama (Kulit K) paling dekat inti: maksimal diisi 2 elektron.\nKulit kedua (Kulit L): maksimal diisi 8 elektron.\nKulit ketiga (Kulit M): maksimal berisi 18 elektron.\nKulit paling luar disebut elektron valensi (maksimal 8). Dialah aktor utama di balik segala reaksi kimia!",
                    challengeQuestion = "Berapakah konfigurasi elektron kulit Bohr untuk atom Natrium yang memiliki Nomor Atom 11?",
                    challengeOptions = listOf("2, 9", "2, 8, 1", "2, 7, 2", "10, 1"),
                    challengeAnswerIndex = 1,
                    explanation = "Nomor atom 11 diisi teratur mengisi kulit: Kulit K (2), Kulit L (8), sisanya 1 elektron melompat ke Kulit M. Konfigurasi yang tepat adalah 2, 8, 1."
                ),
                SubMateri(
                    id = "aturan-oktet",
                    title = "Mengapa Atom Berpasangan? (Aturan Oktet)",
                    summary = "Mencari rahasia di balik atom jomblo yang ingin stabil berpasangan.",
                    fullText = "Atom juga ingin hidup tenteram! Agar stabil seperti kelompok Gas Mulia yang 'mager' bereaksi (karena kulit luarnya sudah pas diisi 8 elektron-aturan oktet), atom-atom lain rela berbagi atau melepas elektron mereka.\n\nContohnya: Oksigen punya 6 elektron terluar, butuh 2 lagi biar jadi 8. Hidrogen punya 1 elektron terluar, ditarik deh biar stabil bareng. Pencarian kestabilan inilah alasan kenapa seluruh zat di dunia ini saling bereaksi membentuk senyawa!",
                    challengeQuestion = "Berapakah jumlah elektron terluar (elektron valensi) yang diincar oleh kebanyakan atom agar dianggap stabil?",
                    challengeOptions = listOf("2 elektron", "6 elektron", "8 elektron (Sempurna/Oktet)", "10 elektron"),
                    challengeAnswerIndex = 2,
                    explanation = "Aturan Oktet menyatakan bahwa atom paling stabil dan pas ketika kulit terluarnya terisi penuh dengan 8 elektron."
                ),
                SubMateri(
                    id = "isotop-isobar-isoton",
                    title = "Isotop, Isobar, & Isoton",
                    summary = "Cara cepat membedakan tipe kembaran atom di alam liar.",
                    fullText = "Kembaran atom terbagi menjadi:\n1. Isotop: Protonnya sama, massanya beda. Contoh: Karbon-12 dan Karbon-14.\n2. Isobar: Massanya sama, unsur & protonnya beda.\n3. Isoton: Jumlah neutronnya (massa - proton) persis sama.",
                    challengeQuestion = "Dua atom bernama atom C-12 (proton 6) dan C-14 (proton 6) dikategorikan sebagai...",
                    challengeOptions = listOf("Isobar", "Isoton", "Isotop", "Isomer"),
                    challengeAnswerIndex = 2,
                    explanation = "Sama-sama karbon dengan jumlah proton sama (6) namun massanya berbeda (12 dan 14), maka disebut Isotop."
                ),
                SubMateri(
                    id = "mekanika-kuantum",
                    title = "Model Atom Mekanika Kuantum",
                    summary = "Melihat pergerakan elektron modern berbasis probabilitas.",
                    fullText = "Bohr menganggap lintasan elektron bulat mutlak. Namun teori Modern menyatakan kita cuma bisa menebak posisi probabilitas elektron melalui 'Awan Elektron' (Orbital). Tokohnya adalah Werner Heisenberg dengan asas ketidakpastian.",
                    challengeQuestion = "Siapakah ilmuwan fisika yang mengemukakan asas ketidakpastian dalam menentukan posisi elektron?",
                    challengeOptions = listOf("Niels Bohr", "Werner Heisenberg", "John Dalton", "Ernest Rutherford"),
                    challengeAnswerIndex = 1,
                    explanation = "Asas ketidakpastian dikemukakan oleh Werner Heisenberg, menandai lahirnya model atom mekanika kuantum."
                ),
                SubMateri(
                    id = "bilangan-kuantum",
                    title = "4 Bilangan Kuantum Utama",
                    summary = "Membaca alamat rumah elektron secara akurat.",
                    fullText = "Setiap elektron punya alamat unik (4 bilangan kuantum):\n1. Utama (n): menentukan tingkat kulit / energi.\n2. Azimut (l): menentukan bentuk subkulit (s=0, p=1, d=2, f=3).\n3. Magnetik (m): orientasi orbital dalam ruang.\n4. Spin (s): arah putaran elektron (+1/2 atau -1/2).",
                    challengeQuestion = "Bila elektron terakhir berada pada subkulit 2p, berapakah nilai bilangan kuantum azimut (l) nya?",
                    challengeOptions = listOf("l = 0", "l = 1", "l = 2", "l = 3"),
                    challengeAnswerIndex = 1,
                    explanation = "Subkulit p memiliki nilai azimut (l) tetap bernilai 1. (s=0, p=1, d=2, f=3)."
                ),
                SubMateri(
                    id = "bentuk-orbital",
                    title = "Bentuk Orbital Atom",
                    summary = "Mengenal bentuk awan elektron s, p, d, f.",
                    fullText = "Orbital s berbentuk bulat bola simetris ke segala arah. Orbital p berbentuk menyerupai balon terpilin dua lobus. Orbital d dan f berpenampilan lebih kompleks dengan banyak lobus.",
                    challengeQuestion = "Subkulit manakah yang memiliki orbital berbentuk bola simetris?",
                    challengeOptions = listOf("s", "p", "d", "f"),
                    challengeAnswerIndex = 0,
                    explanation = "Orbital s (sharp) selalu berbentuk bola simetris, sehingga tidak bermagnetik arah ruang (m=0)."
                ),
                SubMateri(
                    id = "perkembangan-teori-atom",
                    title = "Sejarah Perkembangan Teori Atom",
                    summary = "Perjalanan dari bola pejal Dalton hingga model modern.",
                    fullText = "Teori atom berkembang:\n1. Dalton: Bola pejal tak terpisahkan.\n2. Thomson: Roti kismis (elektron menempel pada bola positif).\n3. Rutherford: Inti atom padat positif dikitari elektron penyeimbang.\n4. Bohr: Elektron di lintasan stasioner kulit atom.",
                    challengeQuestion = "Model atom manakah yang pertama kali mengenalkan adanya inti atom (nucleus)?",
                    challengeOptions = listOf("Dalton", "Thomson", "Rutherford", "Bohr"),
                    challengeAnswerIndex = 2,
                    explanation = "Rutherford menembakkan sinar alfa ke lempeng emas dan membuktikan adanya inti atom padat bermuatan positif di tengah."
                ),
                SubMateri(
                    id = "massa-atom-relatif",
                    title = "Massa Atom Relatif (Ar)",
                    summary = "Bagaimana standar berat atom dihitung ilmuwan.",
                    fullText = "Massa atom itu super kecil. Maka dibuat patokan pembanding yaitu 1/12 massa atom Karbon-12 isotop standar. Jadi diperoleh nilai Massa Atom Relatif (Ar) di tabel periodik.",
                    challengeQuestion = "Isotop atom apakah yang dijadikan patokan dalam menentukan standar massa atom relatif (Ar)?",
                    challengeOptions = listOf("Hidrogen-1", "Oksigen-16", "Karbon-12", "Nitrogen-14"),
                    challengeAnswerIndex = 2,
                    explanation = "Isotop Karbon-12 dipilih secara internasional karena kelimpahan dan kestabilannya sebagai pembanding utama."
                ),
                SubMateri(
                    id = "elektron-valensi-inti",
                    title = "Hubungan Valensi & Kestabilan",
                    summary = "Kenapa kulit terluar sangat menentukan takdir suatu zat.",
                    fullText = "Gaya tarik inti terhadap elektron valensi makin melemah seiring bertambahnya kulit atom. Oleh karena itu, elektron valensi sangat fleksibel dilepas atau dibagi untuk berikatan kimia.",
                    challengeQuestion = "Elektron manakah yang paling berperan dalam pembentukan ikatan kimia antar atom?",
                    challengeOptions = listOf("Elektron inti paling dalam", "Elektron kulit paling luar (valensi)", "Proton inti", "Neutron netral"),
                    challengeAnswerIndex = 1,
                    explanation = "Hanya elektron kulit terluar (valensi) yang dapat berinteraksi langsung atau dibagi dengan atom tetangganya."
                )
            )
        ),
        MateriCategory(
            id = "sistem-periodik",
            title = "Sistem Periodik Unsur",
            iconName = "grid_view",
            difficulty = "Pemula",
            estimatedMinutes = 10,
            subMateriList = listOf(
                SubMateri(
                    id = "golongan-periode",
                    title = "Golongan & Periode",
                    summary = "Cepat membaca tabel periodik modern.",
                    fullText = "Tabel periodik itu peta harta karun kimia! Dibagi menjadi koordinat rapi:\n\n1. Golongan (Kolom Vertikal dari atas ke bawah). Golongan mendefinisikan elektron valensi (jumlah elektron terluar). Unsur se-golongan punya kesamaan sifat yang mirip banget!\n2. Periode (Baris Horizontal dari kiri ke kanan). Periode mendefinisikan jumlah total kulit lintasan atom yang dimiliki unsur tersebut.",
                    challengeQuestion = "Jika unsur X berada di Golongan IVA dan Periode 3, maka konfigurasi elektron terluarnya berakhir di...",
                    challengeOptions = listOf("Elektron valensi 3, kulit ke-4", "Elektron valensi 4, kulit ke-3", "Elektron valensi 7, kulit ke-3", "Elektron valensi 4, kulit ke-4"),
                    challengeAnswerIndex = 1,
                    explanation = "Golongan IVA berarti mempunyai 4 elektron valensi. Periode 3 berarti memiliki 3 kulit atom. Jadi konfigurasi berakhir di kulit ke-3 dengan angka 4."
                ),
                SubMateri(
                    id = "sifat-keperiodikan",
                    title = "Tren Sifat Tabel Periodik",
                    summary = "Mengetahui sifat unik jari-jari dan energi atom dari kiri ke kanan.",
                    fullText = "Unsur-unsur di tabel periodik tidak disusun sembarangan. Ada tren tersembunyi:\n\n1. Jari-jari Atom: Dari kiri ke kanan makin KECIL, karena proton bertambah banyak menarik elektron luar lebih kuat. Dari atas ke bawah makin BESAR karena lintasan kulit bertambah.\n2. Energi Ionisasi (Gaya memperebutkan elektron): Dari kiri ke kanan makin BESAR karena atom makin enggan melepas elektron terluar mereka.",
                    challengeQuestion = "Ketika kita bergerak dari atas ke bawah dalam satu golongan tabel periodik, apa yang terjadi pada ukuran (jari-jari) atom?",
                    challengeOptions = listOf("Semakin Kecil", "Semakin Besar", "Tetap Sama", "Tidak Menentu"),
                    challengeAnswerIndex = 1,
                    explanation = "Dari atas ke bawah, jumlah kulit lintasan atom terus bertambah banyak, sehingga jarak inti ke elektron terluar semakin lebar dan membesar."
                ),
                SubMateri(
                    id = "logam-nonlogam-metalloid",
                    title = "Kasta Unsur: Logam & Nonlogam",
                    summary = "Memisahkan bahan berkilau dari gas rapuh.",
                    fullText = "Tabel periodik memisahkan:\n- Logam: Pengantar listrik baik, mengkilap, ulet, cenderung melepas elektron positif.\n- Nonlogam: Isolator, rapuh dalam bentuk solid, cenderung mencari elektron.\n- Metalloid: Berada di perbatasan tangga diagonal, punya sifat ganda semikonduktor (misal Silikon).",
                    challengeQuestion = "Unsur yang memiliki sifat semikonduktor dan digolongkan sebagai metaloid batas adalah...",
                    challengeOptions = listOf("Besi (Fe)", "Silikon (Si)", "Oksigen (O)", "Helium (He)"),
                    challengeAnswerIndex = 1,
                    explanation = "Silikon (Si) adalah metalloid legendaris pembuat chip mikroprosesor di dunia elektronika komputer bertenaga tinggi."
                ),
                SubMateri(
                    id = "jari-jari-atom-tren",
                    title = "Kemangapan Jari-jari Kiri ke Kanan",
                    summary = "Menjawab teka-teki mengapa proton besar menciutkan ukuran.",
                    fullText = "Dalam satu periode (kiri ke kanan), jumlah kulit atom sama, namun jumlah proton (muatan positif inti) bertambah banyak. Tarikan inti luar makin dahsyat mengepres awan elektron sehingga jari-jarinya mengecil.",
                    challengeQuestion = "Mengapa jari-jari atom dalam satu periode semakin kecil dari kiri ke kanan?",
                    challengeOptions = listOf("Jumlah kulit menyusut", "Tarikan inti atom membesar akibat jumlah proton bertambah", "Elektron terhempas keluar", "Inti atom mendingin"),
                    challengeAnswerIndex = 1,
                    explanation = "Bertambahnya proton meningkatkan gaya tarik elektrostatik inti ke seluruh kulit elektron luar, menciutkan diameter atom."
                ),
                SubMateri(
                    id = "afinitas-elektron",
                    title = "Afinitas Elektron",
                    summary = "Mengerti seberapa berhasrat suatu atom mencaplok elektron.",
                    fullText = "Afinitas elektron adalah besarnya energi yang dibebaskan sewaktu atom netral menangkap elektron asing untuk menjadi ion negatif. Golongan Halogen VIIA punya afinitas super tinggi mendambakan kestabilan.",
                    challengeQuestion = "Golongan unsur manakah di tabel periodik yang menduduki nilai afinitas elektron tertinggi dan sangat reaktif mencuri elektron bebas?",
                    challengeOptions = listOf("Alkali (IA)", "Alkali Tanah (IIA)", "Halogen (VIIA)", "Gas Mulia (VIIIA)"),
                    challengeAnswerIndex = 2,
                    explanation = "Halogen VIIA kekurangan satu elektron saja untuk oktet sempurna, sehingga bersedia membebaskan energi besar untuk mencuri elektron."
                ),
                SubMateri(
                    id = "keelektronegatifan",
                    title = "Skala Keelektronegatifan",
                    summary = "Kecenderungan menarik elektron dalam pasangan ikatan.",
                    fullText = "Keelektronegatifan (pencetus: Linus Pauling) adalah kemampuan atom menarik pasangan elektron ikatan ke sisinya. Fluorin (F) didapuk sebagai raja absolut keelektronegatifan dengan nilai tertinggi 4,0.",
                    challengeQuestion = "Unsur tunggal terkuat yang memiliki nilai keelektronegatifan tertinggi di jagat tabel periodik adalah...",
                    challengeOptions = listOf("Oksigen (O)", "Natrium (Na)", "Fluorin (F)", "Klorin (Cl)"),
                    challengeAnswerIndex = 2,
                    explanation = "Fluorin (F) memiliki keelektronegatifan 4.0 karena jari-jarinya sangat mungil didorong inti muatan efektif tinggi."
                ),
                SubMateri(
                    id = "sejarah-tabel-periodik",
                    title = "Evolusi Penyusunan Tabel Periodik",
                    summary = "Dari Triade Dobereiner, Oktaf Newlands, hingga Mendeleev.",
                    fullText = "Dmitri Mendeleev menyusun unsur berdasarkan kenaikan massa dan meramalkan unsur misterius yang belum ditemukan. Tabel modern kita sekarang disempurnakan Henry Moseley berdasarkan Kenaikan Nomor Atom.",
                    challengeQuestion = "Tabel Periodik Modern yang dipakai sedunia sekarang didasarkan atas penyusunan kenaikan...",
                    challengeOptions = listOf("Massa Atom", "Nomor Atom (Jumlah Proton)", "Jumlah Neutron", "Titik Lebur"),
                    challengeAnswerIndex = 1,
                    explanation = "Henry Moseley membuktikan sifat periodik elemen lebih akurat diatur berdasarkan Kenaikan Nomor Atom (proton) setelah eksperimen sinar-X."
                ),
                SubMateri(
                    id = "golongan-gas-mulia",
                    title = "Nobel Gases (Gas Mulia VIIIA)",
                    summary = "Mengenal golongan ningrat yang antireaksi kimia.",
                    fullText = "Helium, Neon, Argon, Kripton, Xenon, Radon. Golongan ini disebut mulia karena tidak butuh elektron dari siapapun. Elektron valensinya sudah penuh 8 (oktet) atau 2 (duplet) menyebabkannya sangat tidak reaktif (inert).",
                    challengeQuestion = "Mengapa kelompok unsur Gas Mulia (VIIIA) sangat malas dan sukar sekali bereaksi dengan unsur lain?",
                    challengeOptions = listOf("Tidak punya elektron kuantum", "Elektron valensinya sudah jenuh penuh dan stabil", "Titik didihnya super dingin", "Memiliki radioaktif tinggi"),
                    challengeAnswerIndex = 1,
                    explanation = "Konfigurasi kulit terluar mereka sudah mapan terisi penuh (duplet/oktet), meniadakan dorongan energetik untuk berikatan."
                ),
                SubMateri(
                    id = "golongan-alkali",
                    title = "Alami Ledakan Bersama Alkali (IA)",
                    summary = "Zat korosif super reaktif yang hobi meluncur ke air.",
                    fullText = "Alkali (Litium, Natrium, Kalium, dll.) sangat lunak hingga bisa diiris pisau dapur. Hanya punya 1 elektron valensi yang hobi sekali dilepaskan. Jika ditaruh di air murni, reaksinya eksotermik meledak dahsyat melahirkan gas H2 peluru gas ideal!",
                    challengeQuestion = "Logam alkali manakah yang jika dimasukkan ke dalam air murni akan langsung memicu nyala api ledakan hebat?",
                    challengeOptions = listOf("Tembaga (Cu)", "Natrium (Na)", "Emas (Au)", "Aluminium (Al)"),
                    challengeAnswerIndex = 1,
                    explanation = "Natrium (Na) bereaksi dahsyat eksoterm dengan air membentuk NaOH korosif kuat serta gas hidrogen yang terbakar spontan."
                ),
                SubMateri(
                    id = "unsur-transisi",
                    title = "Lembah Logam Transisi (Golongan B)",
                    summary = "Penghasil senyawa berwarna-warni nan menakjubkan.",
                    fullText = "Unsur transisi (blok d) pengisi bagian tengah tabel memiliki keunikan berupa tingkat bilangan oksidasi yang bervariasi banyak. Hal inilah yang melahirkan senyawa kaya warna pelangi yang cantik, beda dengan logam alkali yang polos.",
                    challengeQuestion = "Karakteristik khas apakah yang dipunyai oleh kelompok unsur logam transisi (Golongan B)?",
                    challengeOptions = listOf("Sangat rapuh seperti kaca", "Mempunyai bilangan oksidasi ganda dan senyawanya berwarna-warni", "Berwujud gas bebas pada suhu ruangan", "Membenci magnet besi"),
                    challengeAnswerIndex = 1,
                    explanation = "Transisi memiliki elektron d yang belum penuh, melahirkan transisi energi menghasilkan warna-warna senyawa yang khas."
                )
            )
        ),
        MateriCategory(
            id = "ikatan-kimia",
            title = "Ikatan Kimia",
            iconName = "link",
            difficulty = "Menengah",
            estimatedMinutes = 20,
            subMateriList = listOf(
                SubMateri(
                    id = "ikatan-ion",
                    title = "Ikatan Ion",
                    summary = "Serah terima elektron antar logam-nonlogam.",
                    fullText = "Ikatan Ion terjadi karena ada serah terima elektron agar mencapai kestabilan (aturan oktet / 8 elektron terluar).\n\nSatu atom melepas elektron (menjadi ion positif / kation, biasanya Logam seperti Na), lalu atom yang lain menangkap elektron itu (menjadi ion negatif / anion, biasanya Nonlogam seperti Cl).\nGaya tarik elektrostatis kuat antar kation (+) and anion (-) ini membentuk kristal kokoh dengan titik leleh sangat tinggi seperti garam dapur (NaCl)!",
                    challengeQuestion = "Manakah pasangan senyawa di bawah ini yang memiliki ikatan ion murni?",
                    challengeOptions = listOf("HCl (Asam Klorida)", "CO2 (Karbon Dioksida)", "NaCl (Garam Dapur)", "H2O (Air Murni)"),
                    challengeAnswerIndex = 2,
                    explanation = "NaCl (Natrium Klorida) terbentuk dari Na (logam alkali) yang melepas 1 elektron kepada Cl (gas halogen nonlogam)."
                ),
                SubMateri(
                    id = "ikatan-kovalen",
                    title = "Ikatan Kovalen",
                    summary = "Sharing (pemakaian bersama) elektron nonlogam.",
                    fullText = "Gak semua atom mau melepas elektron. Cara lain stabil adalah patungan / sharing elektron bersama!\n\nHanya terjadi antar atom Nonlogam. Contohnya Air (H2O). Oksigen butuh 2 elektron, Hidrogen butuh 1 elektron. Mereka bersekutu memakai bersama elektron tersebut secara adil.\n\nIkatan kovalen bisa tunggal (sepasang elektron), rangkap 2 (dua pasang), atau rangkap 3 (tiga pasang elektron bersama).",
                    challengeQuestion = "Senyawa manakah yang terbentuk melalui penggunaan bersama elektron (ikatan kovalen)?",
                    challengeOptions = listOf("KBr", "H2O (Air)", "MgO", "LiF"),
                    challengeAnswerIndex = 1,
                    explanation = "H2O adalah air, dibentuk dari Hidrogen dan Oksigen yang keduanya nonlogam dan saling berbagi elektron kovalen."
                ),
                SubMateri(
                    id = "ikatan-logam",
                    title = "Ikatan Logam & Lautan Elektron",
                    summary = "Rahasia di balik logam yang kokoh tapi lentur menghantar listrik.",
                    fullText = "Pernah heran kenapa besi sangat kokoh dan perak bisa menghantarkan listrik dengan luar biasa? Itu karena Ikatan Logam!\n\nDi dalam logam, atom-atomnya merapat erat dan merelakan elektron valensinya mengalir bebas ke segala arah, membentuk 'Lautan Elektron'. Lautan elektron negatif inilah yang mengikat ion-ion kation logam positif tetap menyatu erat. Saat ditekuk, lautan elektron ikut bergeser lentur tanpa patah!",
                    challengeQuestion = "Apa yang menyebabkan logam seperti besi sangat kokoh namun sanggup menghantarkan arus listrik dengan sangat baik?",
                    challengeOptions = listOf("Adanya ikatan kovalen kaku", "Pergerakan bebas lautan elektron valensi", "Inti atom mudah hancur", "Tekanan air di dalam logam"),
                    challengeAnswerIndex = 1,
                    explanation = "Lautan elektron valensi bebas mengalir membawa muatan listrik dengan cepat, dan sekaligus bertindak sebagai lem lentur yang mengikat atom logam."
                ),
                SubMateri(
                    id = "ikatan-kovalen-polar",
                    title = "Kovalen Polar vs Nonpolar",
                    summary = "Melihat ketidakadilan pembagian sepasang jalinan elektron jembatan.",
                    fullText = "Jika dua nonlogam sejenis berikatan (misal H2), pembagian elektron adil rata tanpa kutub (Nonpolar). Namun, jika berikatan dengan nonlogam beda elektronegatifitas (misal HCl), elektron akan ditarik condong ke salah satu sisi membentuk kutub momen dipol listrik (Polar).",
                    challengeQuestion = "Senyawa kovalen manakah di bawah ini yang bersifat polar akibat bentuk asimetris dan perbedaan elektronegatifitas yang tinggi?",
                    challengeOptions = listOf("CH4", "O2", "H2O (Air)", "H2"),
                    challengeAnswerIndex = 2,
                    explanation = "Air H2O memiliki bentuk asimetris bengkok huruf V serta tarikan elektron bias kuat ke Oksigen, menjadikannya pelarut polar handal."
                ),
                SubMateri(
                    id = "ikatan-kovalen-koordinasi",
                    title = "Kovalen Koordinasi (Semihomogen)",
                    summary = "Bila salah satu atom bertindak sebagai donatur tunggal.",
                    fullText = "Ikatan kovalen koordinasi (datif) terjadi bila pasangan elektron yang digunakan bersama hanya dipasok oleh satu atom saja (si donatur tajir), sedangkan atom pasangannya cuma bergabung menumpang gratis tanpa memberikan sumbangan.",
                    challengeQuestion = "Ciri khas dari pembentukan kovalen koordinasi adalah...",
                    challengeOptions = listOf("Ada kutub positif negatif ekstrem", "Pasangan elektron disumbang oleh salah satu atom saja", "Ada perpindahan elektron logam", "Rantai karbon melingkar"),
                    challengeAnswerIndex = 1,
                    explanation = "Hanya salah satu atom yang menyuplai pasangan elektron bebas untuk digunakan bersama. Contohnya pada senyawa ion amonium NH4+."
                ),
                SubMateri(
                    id = "gaya-antar-molekul",
                    title = "Gaya Antar Molekul",
                    summary = "Memahami mengapa air mendidih lambat di suhu tinggi.",
                    fullText = "Ikatan kimia (ion/kovalen) terjadi di DALAM molekul, mengikat atom menyatu. Namun, gaya antar molekul bekerja di LUAR molekul, menentukan sifat fisik seperti titik leleh, kekentalan, dan perubahan wujud cair gas zat.",
                    challengeQuestion = "Mana yang dipecah sewaktu air menguap mendidih di atas kompor gas?",
                    challengeOptions = listOf("Ikatan kovalen kaku O-H", "Gaya tarik antar molekul air (gaya antarmolekul)", "Atom Hidrogen hancur", "Inti atom Oksigen meleleh"),
                    challengeAnswerIndex = 1,
                    explanation = "Mendidih hanya merenggangkan jarak antarmolekul sekelilingnya tanpa merusak molekul air H2O itu sendiri secara kimia."
                ),
                SubMateri(
                    id = "ikatan-hidrogen",
                    title = "Ikatan Hidrogen Dahsyat",
                    summary = "Interaksi terkuat penyusun struktur air di bumantara.",
                    fullText = "Terjadi bila atom Hidrogen ditarik erat oleh atom super elektronegatif tetangganya yaitu F, O, atau N (Singkatan mudah ingat: FON). Ikatan ini berkali-kali lipat lebih kuat dari gaya interaksi molekul biasa, membuat titik didih air (H2O) melonjak tinggi dibanding H2S.",
                    challengeQuestion = "Senyawa manakah di bawah ini yang saling terikat kuat oleh Ikatan Hidrogen antarmolekulnya?",
                    challengeOptions = listOf("HCl", "HF (Asam Fluorida)", "H2S", "CH4"),
                    challengeAnswerIndex = 1,
                    explanation = "Atom H terikat langsung pada Fluorin (F) yang sangat elektronegatif melahirkan Ikatan Hidrogen yang sangat tangguh."
                ),
                SubMateri(
                    id = "gaya-london-dispersi",
                    title = "Gaya London / Dispersi Lemah",
                    summary = "Gaya interaksi instan penembus gas nonpolar.",
                    fullText = "Di dalam senyawa nonpolar murni (seperti gas Nitrogen N2), tidak ada kutub tetap. Namun, pergerakan elektron kilat sesaat terkadang menimbulkan kutub temporer (dipol sesaat) yang menginduksi gaya dispersi London yang lemah.",
                    challengeQuestion = "Gaya antarmolekul yang bekerja pada gas mulia Helium cair pada suhu yang mendekati nol mutlak adalah...",
                    challengeOptions = listOf("Ikatan Hidrogen", "Ikatan Ion", "Gaya London / Dispersi Sesaat", "Kovalen Koordinasi"),
                    challengeAnswerIndex = 2,
                    explanation = "Gas nonpolar dan gas mulia hanya dapat memadat/mencair dibantu Gaya London temporal berenergi rendah."
                ),
                SubMateri(
                    id = "bentuk-molekul-vsepr",
                    title = "Teori Bentuk Molekul VSEPR",
                    summary = "Tolakan pasangan elektron penentu posisi geometris 3D.",
                    fullText = "Teori VSEPR (Valence Shell Electron Pair Repulsion) menyatakan bahwa arah ikatan menyesuaikan gaya tolak minimum antar pasangan elektron. Pasangan Elektron Bebas (PEB) menolak lebih galak daripada pasangan berikatan, menekuk sudut geometri.",
                    challengeQuestion = "Bentuk geometri molekul Metana (CH4) yang memiliki 4 pasangan elektron ikatan tanpa elektron bebas sisa adalah...",
                    challengeOptions = listOf("Linear datar", "Plana segitiga", "Tetrahedral", "Oktahedral"),
                    challengeAnswerIndex = 2,
                    explanation = "4 pasang elektron ikatan simetris mengarah ke sudut-sudut bidang empat melahirkan bentuk 3D Tetrahedral."
                ),
                SubMateri(
                    id = "gaya-dipol-dipol",
                    title = "Interaksi Dipol-Dipol",
                    summary = "Tarikan listrik awet antar senyawa polar sejati.",
                    fullText = "Pada senyawa polar (seperti HCl atau HBr), kutub positif parsial satu molekul berpaut permanen ke kutub negatif parsial molekul sebelahnya secara konstan, lebih tegap dibanding gaya London sesaat.",
                    challengeQuestion = "Senyawa-senyawa manakah yang didominasi oleh interaksi gaya tarik permanen dipol-dipol?",
                    challengeOptions = listOf("Antar molekul nonpolar saja", "Senyawa-senyawa polar sejati sejenis", "Antar atom gas inert mulia", "Campuran besi cair"),
                    challengeAnswerIndex = 1,
                    explanation = "Dipol permanen positif-negatif hanya dimiliki oleh senyawa-senyawa berikatan polar."
                )
            )
        ),
        MateriCategory(
            id = "hidrokarbon",
            title = "Kimia Hidrokarbon",
            iconName = "bubble_chart",
            difficulty = "Menengah",
            estimatedMinutes = 25,
            subMateriList = listOf(
                SubMateri(
                    id = "alkana-alkena-alkuna",
                    title = "Alkana, Alkena, & Alkuna",
                    summary = "Mengenal ikatan karbon jenuh dan tak jenuh.",
                    fullText = "Hidrokarbon adalah senyawa berisi atom Karbon (C) dan Hidrogen (H) saja. Dibagi tiga jenis berdasarkan jenis ikatan rantai C-C:\n\n1. Alkana (Ikatan Tunggal, rumus: C_n H_{2n+2}). Contoh: Metana (CH4), Propana (C3H8).\n2. Alkena (Memiliki Ikatan Rangkap Dua, rumus: C_n H_{2n}). Contoh: Etena (C2H4).\n3. Alkuna (Memiliki Ikatan Rangkap Tiga, rumus: C_n H_{2n-2}). Contoh: Etuna/Asetilen (C2H2).\n\nAlkana disebut jenuh karena sudah dipenuhi Hidrogen, sedangkan Alkena dan Alkuna tak jenuh karena bisa berekspansi mengikat lebih banyak atom.",
                    challengeQuestion = "Jika senyawa Alkana memiliki 4 buah atom Karbon, berapakah jumlah atom Hidrogennya?",
                    challengeOptions = listOf("8 H", "10 H", "12 H", "6 H"),
                    challengeAnswerIndex = 1,
                    explanation = "Rumus Alkana: C_n H_{2n+2}. Jika n=4, maka jumlah H = 2(4)+2 = 10. Senyawa ini dinamai Butana."
                ),
                SubMateri(
                    id = "aplikasi-hidrokarbon",
                    title = "Hidrokarbon di Kehidupan Nyata",
                    summary = "Melihat langsung pemanfaatan gas bumi, elpiji, dan aspal jalanan.",
                    fullText = "Senyawa hidrokarbon ada di mana-mana! Kita memanfaatkannya berdasarkan panjang rantai sirkuit karbonnya:\n\n- Rantai pendek (C1 - C4): Gas elpiji untuk kompor dapur (propane & butane) serta gas bumi instan.\n- Rantai sedang (C5 - C12): Bahan bakar bensi kendaraan bermotor.\n- Rantai panjang (C20+): Oli pelumas mesin dan aspal padat pelapis jalan raya.\n\nSemakin panjang rantai karbonnya, senyawanya akan berbentuk semakin kental bahkan menjadi padat, dan titik didihnya naik pesat!",
                    challengeQuestion = "Senyawa hidrokarbon rantai pendek yang merupakan komponen utama gas elpiji (LPG) di dapur kita adalah...",
                    challengeOptions = listOf("Metana (CH4)", "Propana & Butana", "Bensin Oktana", "Lilin Parafin"),
                    challengeAnswerIndex = 1,
                    explanation = "Gas LPG (Liquefied Petroleum Gas) utamanya tersusun dari gas Propana (C3H8) dan Butana (C4H10) karena mudah dicairkan pada tekanan sedang."
                ),
                SubMateri(
                    id = "tata-nama-alkana",
                    title = "Aturan IUPAC Alkana",
                    summary = "Langkah menamai rantai bercabang secara reguler.",
                    fullText = "Menurut IUPAC:\n1. Cari rantai karbon lurus terpanjang (rantai utama).\n2. Beri penomoran dimulai dari ujung ujung yang paling dekat cabang alkil.\n3. Urutan nama: posisi cabang - nama cabang - rantai utama. (Metil, Etil, dll.)",
                    challengeQuestion = "Rantai utama terpanjang dari senyawa alkana dengan nama 2,2-dimetilpentana memiliki berapa atom Karbon?",
                    challengeOptions = listOf("2 Karbon", "3 Karbon", "5 Karbon", "7 Karbon"),
                    challengeAnswerIndex = 2,
                    explanation = "Nama akhir 'pentana' menunjukkan rantai utama terpanjang terdiri atas 5 atom karbon jenuh lurus."
                ),
                SubMateri(
                    id = "reaksi-adisi",
                    title = "Reaksi Adisi (Pemutusan Ikatan)",
                    summary = "Menyerang ikatan rangkap jenuh gas tak jenuh.",
                    fullText = "Reaksi Adisi adalah reaksi penggabungan/penjenuhan di mana ikatan rangkap dua (alkena) atau tiga (alkuna) dirusak dipotong menjadi ikatan tunggal (alkana) dengan menambahkan atom reagen (halogen, asam halida, hidrogen).",
                    challengeQuestion = "Kategori reaksi yang mengubah gas Etena (C2H4) menjadi Etana (C2H6) dengan gas hidrogen H2 adalah...",
                    challengeOptions = listOf("Eliminasi", "Substitusi", "Adisi", "Kondensasi"),
                    challengeAnswerIndex = 2,
                    explanation = "Pemutusan ikatan rangkap dua alkena menjadi ikatan tunggal alkana adalah definisi murni Reaksi Adisi."
                ),
                SubMateri(
                    id = "reaksi-substitusi",
                    title = "Reaksi Substitusi (Pertukaran)",
                    summary = "Saling bertukar atom penyeimbang pada hidrokarbon jenuh.",
                    fullText = "Reaksi substitusi terjadi pada alkana jenuh. Di mana atom Hidrogen dilepas digantikan/ditukarkan oleh atom reagen pendatang baru (biasanya halogen gas klorin Cl2 dibantu sinar UV matahari).",
                    challengeQuestion = "Reaksi pengubahan gas Metana CH4 menjadi CH3Cl menggunakan halogen klorin disebut reaksi...",
                    challengeOptions = listOf("Adisi", "Eliminasi", "Substitusi", "Polimerisasi"),
                    challengeAnswerIndex = 2,
                    explanation = "Satu atom H pada metana digantikan secara langsung oleh satu atom Cl, berciri khas penukaran stabil (substitusi)."
                ),
                SubMateri(
                    id = "reaksi-eliminasi",
                    title = "Reaksi Eliminasi (Pencipta Rangkap)",
                    summary = "Membuang atom sampingan sekunder menciptakan rantai rangkap.",
                    fullText = "Reaksi Eliminasi merupakan kebalikan dari adisi. Dua atom sampingan disingkirkan/dibuang, mengubah ikatan tunggal (alkana) kembali menjadi ikatan rangkap alkena/alkuna yang tegap tinggi.",
                    challengeQuestion = "Reaksi pelepasan air atau asam dari alkil halida menghasilkan ikatan rangkap alkena digolongkan sebagai...",
                    challengeOptions = listOf("Adisi", "Eliminasi", "Substitusi", "Hidrasi"),
                    challengeAnswerIndex = 1,
                    explanation = "Pelepasan molekul kecil untuk melahirkan struktur ikatan rangkap (dua/tiga) dinamakan Reaksi Eliminasi."
                ),
                SubMateri(
                    id = "sifat-fisik-hidrokarbon",
                    title = "Sifat Fisik Deret Homolog",
                    summary = "Membedakan wujud materi senyawa searah.",
                    fullText = "Laju pertambahan rantai karbon meningkatkan gaya dispersi London. Akibatnya titik didih homolog naik selaras dengan panjang rantai karbon. Butana berwujud gas, sedangkan oktana berwujud cair bensin.",
                    challengeQuestion = "Manakah senyawa hidrokarbon berikut yang berada dalam wujud padat lunak pada suhu ruang standar?",
                    challengeOptions = listOf("Metana (CH4)", "Butana (C4H10)", "Propana (C3H8)", "Parafin / Lilin Padat (C25H52)"),
                    challengeAnswerIndex = 3,
                    explanation = "Lilin parafin berantai sangat panjang (C25), menghasilkan gaya London yang sangat kuat menyatukannya menjadi padat."
                ),
                SubMateri(
                    id = "fraksi-minyak-bumi",
                    title = "Destilasi Bertingkat Minyak Bumi",
                    summary = "Bagaimana cairan mentah hitam pekat disuling berguna.",
                    fullText = "Minyak bumi mentah dipisahkan dalam kolom destilasi bertingkat berdasarkan perbedaan titik didih molekul. Fraksi teringan menguap paling atas (gas elpiji), bensin di tengah, oli pelumas di bawah, aspal residu di dasar.",
                    challengeQuestion = "Metode pemisahan minyak bumi berdasarkan perbedaan suhu didih komponen dikerjakan lewat proses...",
                    challengeOptions = listOf("Kromatografi kertas", "Filtrasi saring biasa", "Destilasi Bertingkat (Fraksionasi)", "Sentrifugasi cepat"),
                    challengeAnswerIndex = 2,
                    explanation = "Penyulingan bertingkat memisahkan gas, bensin, kerosin, solar, pelumas, dan aspal bersih berlandaskan trayek titik didih."
                ),
                SubMateri(
                    id = "isomery-rantai",
                    title = "Isomer Rangka dan Cabang",
                    summary = "Satu rumus molekul, banyak variasi kerangka.",
                    fullText = "Isomer rangka adalah senyawa berumus molekul persis sama namun kerangka rantai utamanya berbeda. Pentana lurus berisomer dengan 2-metilbutana (cabang) dan 2,2-dimetilpropana (cabang ganda).",
                    challengeQuestion = "Berapakah isomer kerangka yang dipunyai oleh gas Butana (C4H10)?",
                    challengeOptions = listOf("1", "2 (n-butana & isobutana)", "3", "4"),
                    challengeAnswerIndex = 1,
                    explanation = "C4H10 hanya dapat dibentuk menjadi rantai lurus (n-butana) atau rantai bercabang (isobutana/2-metilpropana)."
                ),
                SubMateri(
                    id = "pembakaran-sempurna",
                    title = "Pembakaran Hidrokarbon",
                    summary = "Bahaya gas monoksida CO hasil pembakaran tak sempurna.",
                    fullText = "Pembakaran sempurna bensin melepaskan gas CO2 yang tidak beracun dan uap air H2O. Namun, pembakaran tidak sempurna (miskin oksigen) melepaskan gas Karbon Monoksida (CO) beracun penjerat sel darah merah.",
                    challengeQuestion = "Gas mematikan hasil pembakaran bensin kendaraan bermotor yang tidak sempurna adalah...",
                    challengeOptions = listOf("Oksigen (O2)", "Karbon Monoksida (CO)", "Uap Air (H2O)", "Nitrogen (N2)"),
                    challengeAnswerIndex = 1,
                    explanation = "Gas CO beracun karena berikatan jauh lebih kuat dengan hemoglobin sel darah merah dibandingkan oksigen murni biasa."
                )
            )
        ),
        MateriCategory(
            id = "kimia-organik",
            title = "Kimia Organik",
            iconName = "hive",
            difficulty = "Mahir",
            estimatedMinutes = 30,
            subMateriList = listOf(
                SubMateri(
                    id = "alkohol-gugus-fungsi",
                    title = "Alkohol & Gugus Fungsi",
                    summary = "Gugus penentu sifat senyawa organik.",
                    fullText = "Gugus fungsi adalah atom penentu kelakuan utama molekul organik. Contoh utamanya adalah Alkohol / Alkanol yang memiliki gugus aktif hidroksil (-OH) terikat pada rantai alkil.\n\nSifat Alkohol:\n- Mudah larut air untuk rantai pendek.\n- Titik didih tinggi karena ikatan hidrogen.\n- Bereaksi dengan logam aktif melepas gas H2.\n\nGugus fungsi lain: Eter (-O-), Aldehid (-CHO), Keton (-CO-), Asam Karboksilat (-COOH), dan Ester (-COOR).",
                    challengeQuestion = "Gugus fungsi manakah yang menjadi ciri khas dari senyawa kelompok Alkohol?",
                    challengeOptions = listOf("-COOH", "-OH", "-CHO", "-COO-"),
                    challengeAnswerIndex = 1,
                    explanation = "Gugus fungsi -OH (hidroksil) adalah ciri khas alkohol / alkanol."
                ),
                SubMateri(
                    id = "isomer-cis-trans",
                    title = "Isomer Geometri: Cis & Trans",
                    summary = "Beda posisi ruang, beda total sifat fisisnya!",
                    fullText = "Isomer geometri terjadi pada senyawa Alkena akibat hambatan rotasi pada ikatan rangkap dua C=C yang kaku.\n\n- Isomer CIS: Atom/gugus sejenis berada di sisi yang sama (atas-atas / bawah-bawah).\n- Isomer TRANS: Atom/gugus sejenis bersebelahan secara diagonal melintasi ikatan rangkap (atas-bawah).\n\nPerbedaan ini vital! Isomer Cis biasanya memiliki titik didih lebih tinggi karena kepolaran yang kuat dibanding trans.",
                    challengeQuestion = "Kondisi apakah yang wajib ada agar suatu senyawa memiliki isomer geometri Cis-Trans?",
                    challengeOptions = listOf("Ikatan tunggal C-C biasa", "Ikatan rangkap dua C=C dengan masing-masing mengikat 2 gugus berbeda", "Adanya gugus Alkohol", "Cincin Benzena melingkar"),
                    challengeAnswerIndex = 1,
                    explanation = "Isomer cis-trans hanya terjadi pada ikatan rangkap dua C=C di mana setiap atom C mengikat dua atom/gugus yang berbeda satu sama lain."
                ),
                SubMateri(
                    id = "orto-meta-para",
                    title = "Orto, Meta, Para pada Benzena",
                    summary = "Aturan posisi substituen cincin Benzena (C6H6).",
                    fullText = "Benzena (C6H6) memiliki struktur cincin segienam dengan ikatan rangkap terdelokalisasi (resonansi).\n\nBila ada dua substituen nempel di cincin ini, posisinya dinamai:\n1. Orto (o-): Posisi berdampingan (nomor karbon 1, 2)\n2. Meta (m-): Posisi diselingi satu karbon (nomor karbon 1, 3)\n3. Para (p-): Posisi saling berhadapan bersebrangan (nomor karbon 1, 4)",
                    challengeQuestion = "Jika gugus klorin terikat di atom karbon nomor 1 dan karbon nomor 4 pada cincin benzena, sebutan posisinya adalah...",
                    challengeOptions = listOf("Orto (o-dichlorobenzene)", "Meta (m-dichlorobenzene)", "Para (p-dichlorobenzene)", "Trans (t-dichlorobenzene)"),
                    challengeAnswerIndex = 2,
                    explanation = "Posisi 1,4 disebut posisi Para (p-), letaknya berseberangan simetris secara sempurna."
                ),
                SubMateri(
                    id = "asam-karboksilat-ester-wangi",
                    title = "Ester: Pembuat Aroma Buah Buatan",
                    summary = "Bagaimana senyawa asam menciptakan parfum dan keharuman buah segar.",
                    fullText = "Tahukah kamu dari mana wangi esens pisang, stroberi, atau parfum jeruk manis buatan berasal? Mereka adalah senyawa Ester / Alkil Alkanoat!\n\nSenyawa ester dibuat melalui reaksi Esterifikasi: mereaksikan Asam Karboksilat (seperti asam cuka) dengan Alkohol dibantu katalis asam. \nSangat ajaib melihat cairan asam berbau menyengat bercampur alkohol bisa bertransformasi menjadi molekul harum manis layaknya buah matang!",
                    challengeQuestion = "Reaksi kimia pencampuran senyawa apakah yang dapat menghasilkan Ester (alkil alkanoat) beraroma wangi buah-buahan?",
                    challengeOptions = listOf("Alkohol dilarutkan ke air garam dapur", "Asam Karboksilat direaksikan dengan Alkohol", "Logam Natrium dibakar dalam tabung oksigen", "Benzena dipanaskan bersama klorin murni"),
                    challengeAnswerIndex = 1,
                    explanation = "Reaksi esterifikasi menyatukan rantai Asam Karboksilat dan Alkohol, membebaskan molekul air dan melahirkan Ester berbau harum."
                ),
                SubMateri(
                    id = "eter-gugus-fungsi",
                    title = "Eter (Alkoksi Alkana)",
                    summary = "Mengenal obat bius anestesi masa silam.",
                    fullText = "Eter adalah kerabat dekat alkohol dengan rumus C_n H_{2n+2} O (Isomer fungsi). Bedanya, Eter tidak memiliki gugus -OH murni melainkan gugus penghubung jembatan oksigen (-O-) mengimpit dua cabang alkil. Eter bersifat nonpolar, mudah terbakar, dan tidak larut air.",
                    challengeQuestion = "Kelompok senyawa eter (alkoksi alkana) dicirikan oleh gugus fungsi hubung...",
                    challengeOptions = listOf("-OH", "-O-", "-CO-", "-CHO"),
                    challengeAnswerIndex = 1,
                    explanation = "Gugus fungsi -O- (oksi) adalah penanda mutlak persenyawaan kelompok Eter."
                ),
                SubMateri(
                    id = "aldehid-dan-keton",
                    title = "Aldehid & Keton (Karbonil)",
                    summary = "Pecahan formalin pemicu reaksi Fehling.",
                    fullText = "Aldehid (Alkanal, -CHO) dan Keton (Alkanon, -CO-) sama-sama mengidap gugus Karbonil (C=O). Aldehid di ujung rantai, sedangkan Keton di jepitan tengah rantai. Aldehid reaktif mereduksi larutan Tollens menjadi cermin perak murni, sedangkan keton tidak.",
                    challengeQuestion = "Cairan formalin yang digunakan mengawetkan preparat biologi tersusun dari senyawa...",
                    challengeOptions = listOf("Metanol (alkohol)", "Metanal (Aldehid/Formaldehida)", "Propanon (Aseton)", "Asam Asetat"),
                    challengeAnswerIndex = 1,
                    explanation = "Formalin dilarutkan dari gas Formaldehida / Metanal (kelompok senyawa Aldehid)."
                ),
                SubMateri(
                    id = "makromolekul-karbohidrat",
                    title = "Makromolekul Karbohidrat",
                    summary = "Memahami glukosa pembakar sel tubuh.",
                    fullText = "Karbohidrat tersusun atas polimer gula. Karbohidrat teringan adalah Monosakarida (Glukosa, Fruktosa). Disakarida (Maltosa, Sukrosa), dan polisakarida rantai raksasa pati (Selulosa, Amilum) yang menyusun serat makanan kita.",
                    challengeQuestion = "Gula sederhana berkarbon 6 yang mengalir konstan di dalam darah manusia sebagai bensin energi utama adalah...",
                    challengeOptions = listOf("Sukrosa", "Laktosa", "Glukosa", "Amilum"),
                    challengeAnswerIndex = 2,
                    explanation = "Glukosa adalah monosakarida penyedia respirasi seluler mutlak penopang aktivitas biologis utama tubuh."
                ),
                SubMateri(
                    id = "protein-asam-amino",
                    title = "Protein & Asam Amino",
                    summary = "Ikatan peptida pembangun struktur daging makhluk hidup.",
                    fullText = "Protein dibangun dari gabungan polimerisasi kondensasi monomer Asam Amino dibantu penyatuan Ikatan Peptida (-CO-NH-). Asam amino mengidap amina (-NH2) sekaligus karboksilat (-COOH) yang bersifat amfoter ganda masam-basa.",
                    challengeQuestion = "Ikatan kimia spesifik yang mengikat erat monomer-monomer asam amino pembentuk rantai protein disebut...",
                    challengeOptions = listOf("Ikatan Kovalen Polar", "Ikatan Peptida", "Ikatan Logam", "Ikatan Eter"),
                    challengeAnswerIndex = 1,
                    explanation = "Ikatan peptida mengawinkan gugus karboksil asam amino pertama dengan gugus amina asam amino kedua melepas air."
                ),
                SubMateri(
                    id = "polimer-sintetis",
                    title = "Polimer Sintetis Plastik",
                    summary = "Melihat polimerisasi adisi kresek PVC.",
                    fullText = "Polimer sintetis seperti Polietilena (kresek harian) dan PVC (pipa air paralon) dibuat melalui polimerisasi adisi: menggabungkan ribuan monomer berikatan rangkap membuka ritsleting ikatan menjadi silinder panjang solid stabil.",
                    challengeQuestion = "Bahan pembuat pipa air rumah tangga kokoh bermerek PVC dibentuk dari monomer bernama...",
                    challengeOptions = listOf("Etena", "Stirena", "Vinil Klorida", "Tetrafluoroetena"),
                    challengeAnswerIndex = 2,
                    explanation = "PVC singkatan dari Polyvinyl Chloride, digabung dari monomer Vinil Klorida secara serempak berurutan."
                ),
                SubMateri(
                    id = "reaksi-pembentukan-ester",
                    title = "Reaksi Hidrolisis Ester",
                    summary = "Sabun mandi hasil saponifikasi minyak lemak.",
                    fullText = "Ester lemak jika dipanaskan bersama basa kuat (seperti NaOH) akan terhidrolisis menggariskan reaksi Penyabunan (Saponifikasi), melahirkan molekul Sabun (garam natrium asam lemak) yang bagian ekornya larut minyak dan kepalanya larut air.",
                    challengeQuestion = "Reaksi kimia pengolahan minyak kelapa kelapa dengan KOH/NaOH untuk membuat sabun dinamakan...",
                    challengeOptions = listOf("Esterifikasi", "Saponifikasi (Penyabunan)", "Eliminasi", "Polimerisasi"),
                    challengeAnswerIndex = 1,
                    explanation = "Saponifikasi (Penyabunan) memecah trigliserida ester lemak dengan alkali melahirkan sabun penjerat daki kotoran tubuh."
                )
            )
        )
    )
}
