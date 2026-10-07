package com.kelompok.tangkis.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.data.api.ApiClient
import com.kelompok.tangkis.data.api.toCreateDetectionRequest
import com.kelompok.tangkis.data.api.toHasilDeteksiOrNull
import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.ui.komponen.NavigasiBawah
import com.kelompok.tangkis.ui.komponen.Tab
import com.kelompok.tangkis.ui.layar.LayarBeranda
import com.kelompok.tangkis.ui.layar.LayarGantiSandi
import com.kelompok.tangkis.ui.layar.LayarHasil
import com.kelompok.tangkis.ui.layar.LayarKunci
import com.kelompok.tangkis.ui.layar.LayarMemindai
import com.kelompok.tangkis.ui.layar.LayarPengaturan
import com.kelompok.tangkis.ui.layar.LayarRiwayat
import com.kelompok.tangkis.ui.layar.LayarTentang
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.TangkisTheme

sealed interface Layar {
    data object Kunci : Layar
    data class Utama(val tab: Tab) : Layar
    data class Memindai(val pesan: String) : Layar
    data class Hasil(val hasil: HasilDeteksi) : Layar
    data object Tentang : Layar
    data object GantiSandi : Layar
}

// Tinggi area navigasi melayang beserta jaraknya, dan tinggi gradasi pudar di atasnya
private val TinggiNavigasi = 92.dp
private val TinggiPudar = 32.dp
private val RuangNavigasi = TinggiNavigasi + TinggiPudar

// Navigasi sederhana berbasis tumpukan layar, tanpa library tambahan
@Composable
fun TangkisApp(layarAwal: Layar = Layar.Kunci) {
    val tumpukan = remember { mutableStateListOf(layarAwal) }
    val riwayat = remember { mutableStateListOf<HasilDeteksi>() }
    val scope = rememberCoroutineScope()

    // Staging: riwayat bersama dimuat dari server. Jika jaringan gagal, UI tetap berjalan lokal.
    LaunchedEffect(Unit) {
        runCatching {
            ApiClient.service.getDetections(limit = 100).data.mapNotNull { it.toHasilDeteksiOrNull() }
        }.onSuccess { remoteHistory ->
            riwayat.clear()
            riwayat.addAll(remoteHistory)
        }
    }

    // Pengecekan ini mencegah layar terbuka atau tertutup dua kali bila tombol ditekan saat animasi
    fun buka(layar: Layar) {
        if (tumpukan.last() != layar) tumpukan.add(layar)
    }
    fun ganti(layar: Layar) {
        tumpukan.removeAt(tumpukan.lastIndex)
        tumpukan.add(layar)
    }
    fun kembali() {
        if (tumpukan.size > 1) tumpukan.removeAt(tumpukan.lastIndex)
    }
    fun mulaiDari(layar: Layar) {
        tumpukan.clear()
        tumpukan.add(layar)
    }

    BackHandler(enabled = tumpukan.size > 1) { kembali() }

    AnimatedContent(
        targetState = Halaman(tumpukan.last(), tumpukan.size),
        modifier = Modifier.fillMaxSize().background(Latar),
        transitionSpec = { transisiHalaman(initialState, targetState) },
        // Perpindahan tab ditangani di dalam layar utama, bukan sebagai pergantian halaman
        contentKey = { if (it.layar is Layar.Utama) "utama" else it.layar },
        label = "halaman",
    ) { halaman ->
        when (val layar = halaman.layar) {
            Layar.Kunci -> LayarKunci(
                onTerbuka = { ganti(Layar.Utama(Tab.CEK)) },
                // Penghapusan hash kata sandi tersimpan ditambahkan bersama implementasi PBKDF2
                onReset = { riwayat.clear() },
            )

            is Layar.Utama -> Box(Modifier.fillMaxSize().background(Latar)) {
                val sistem = WindowInsets.systemBars.asPaddingValues()
                // Ruang bawah tambahan agar konten terakhir tidak tertutup navigasi yang melayang
                val padding = PaddingValues(
                    top = sistem.calculateTopPadding(),
                    bottom = sistem.calculateBottomPadding() + RuangNavigasi,
                )
                AnimatedContent(
                    targetState = layar.tab,
                    transitionSpec = { transisiTab(initialState, targetState) },
                    label = "tab",
                ) { tab ->
                    when (tab) {
                        Tab.CEK -> LayarBeranda(riwayat, onPindai = { buka(Layar.Memindai(it)) }, contentPadding = padding)
                        Tab.RIWAYAT -> LayarRiwayat(
                            riwayat,
                            onPilih = { buka(Layar.Hasil(it)) },
                            onCekPesan = { ganti(Layar.Utama(Tab.CEK)) },
                            contentPadding = padding,
                        )
                        Tab.ATUR -> LayarPengaturan(
                            jumlahRiwayat = riwayat.size,
                            onGantiSandi = { buka(Layar.GantiSandi) },
                            onKunci = { mulaiDari(Layar.Kunci) },
                            onHapusRiwayat = { riwayat.clear() },
                            onTentang = { buka(Layar.Tentang) },
                            contentPadding = padding,
                        )
                    }
                }
                // Konten yang digulir memudar ke warna latar sebelum mencapai navigasi
                val tinggiGradasi = sistem.calculateBottomPadding() + RuangNavigasi
                Box(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(tinggiGradasi)
                        .background(
                            Brush.verticalGradient(
                                0f to Latar.copy(alpha = 0f),
                                TinggiPudar / tinggiGradasi to Latar,
                            ),
                        ),
                )
                NavigasiBawah(
                    aktif = layar.tab,
                    onPilih = { ganti(Layar.Utama(it)) },
                    modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding(),
                )
            }

            is Layar.Memindai -> LayarMemindai(layar.pesan) { hasil ->
                // Optimistic update: hasil langsung terlihat, lalu disinkronkan ke staging API.
                riwayat.add(0, hasil)
                scope.launch {
                    runCatching {
                        ApiClient.service.createDetection(hasil.toCreateDetectionRequest())
                    }
                }
                ganti(Layar.Hasil(hasil))
            }

            is Layar.Hasil -> LayarHasil(
                hasil = layar.hasil,
                onKembali = { kembali() },
                onCekLagi = { mulaiDari(Layar.Utama(Tab.CEK)) },
            )

            Layar.Tentang -> LayarTentang(onKembali = { kembali() })

            Layar.GantiSandi -> LayarGantiSandi(onKembali = { kembali() })
        }
    }
}

// Layar beserta kedalamannya di tumpukan, untuk menentukan arah animasi maju atau mundur
private data class Halaman(val layar: Layar, val kedalaman: Int)

private const val DurasiHalaman = 320

private fun transisiHalaman(awal: Halaman, akhir: Halaman): ContentTransform {
    // Membuka kunci dan menampilkan hasil pindaian memakai efek muncul, bukan geser
    if (awal.layar is Layar.Kunci || akhir.layar is Layar.Kunci || awal.layar is Layar.Memindai) {
        return (fadeIn(tween(DurasiHalaman)) + scaleIn(tween(DurasiHalaman), initialScale = 0.94f)) togetherWith
            fadeOut(tween(DurasiHalaman / 2))
    }
    val maju = akhir.kedalaman >= awal.kedalaman
    val arah = if (maju) 1 else -1
    return (slideInHorizontally(tween(DurasiHalaman)) { arah * it / 4 } + fadeIn(tween(DurasiHalaman))) togetherWith
        (slideOutHorizontally(tween(DurasiHalaman)) { -arah * it / 8 } + fadeOut(tween(DurasiHalaman / 2)))
}

private fun transisiTab(awal: Tab, akhir: Tab): ContentTransform {
    val arah = if (akhir.ordinal > awal.ordinal) 1 else -1
    return (slideInHorizontally(tween(260)) { arah * it / 12 } + fadeIn(tween(260))) togetherWith
        (slideOutHorizontally(tween(260)) { -arah * it / 12 } + fadeOut(tween(140)))
}

@Preview(name = "Aplikasi - Cek", showSystemUi = true)
@Composable
private fun PratinjauAplikasiCek() {
    TangkisTheme { TangkisApp(Layar.Utama(Tab.CEK)) }
}

@Preview(name = "Aplikasi - Riwayat", showSystemUi = true)
@Composable
private fun PratinjauAplikasiRiwayat() {
    TangkisTheme { TangkisApp(Layar.Utama(Tab.RIWAYAT)) }
}

@Preview(name = "Aplikasi - Atur", showSystemUi = true)
@Composable
private fun PratinjauAplikasiAtur() {
    TangkisTheme { TangkisApp(Layar.Utama(Tab.ATUR)) }
}
