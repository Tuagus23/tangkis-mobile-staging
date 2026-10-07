package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.komponen.KolomKataSandi
import com.kelompok.tangkis.ui.komponen.TombolKembali
import com.kelompok.tangkis.ui.komponen.TombolUtama
import com.kelompok.tangkis.ui.theme.AmanLembut
import com.kelompok.tangkis.ui.theme.AmanTua
import com.kelompok.tangkis.ui.theme.AmanUtama
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

private const val PANJANG_MINIMAL = 8

@Composable
fun LayarGantiSandi(onKembali: () -> Unit) {
    var lama by remember { mutableStateOf("") }
    var baru by remember { mutableStateOf("") }
    var konfirmasi by remember { mutableStateOf("") }
    var galatLama by remember { mutableStateOf<String?>(null) }
    var galatBaru by remember { mutableStateOf<String?>(null) }
    var galatKonfirmasi by remember { mutableStateOf<String?>(null) }
    var berhasil by remember { mutableStateOf(false) }

    // Validasi tampilan; pencocokan dan penyimpanan hash PBKDF2 ditambahkan pada tahap implementasi keamanan
    val simpan = {
        galatLama = if (lama.isBlank()) "Isi kata sandi lama dulu" else null
        galatBaru = when {
            baru.length < PANJANG_MINIMAL -> "Kata sandi baru minimal $PANJANG_MINIMAL karakter"
            baru == lama -> "Kata sandi baru harus beda dari yang lama"
            else -> null
        }
        galatKonfirmasi = if (konfirmasi != baru) "Konfirmasinya belum sama" else null
        berhasil = galatLama == null && galatBaru == null && galatKonfirmasi == null
        if (berhasil) {
            lama = ""
            baru = ""
            konfirmasi = ""
        }
    }

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 260.dp, x = 100.dp, y = (-120).dp, align = Alignment.TopEnd)

        Column(
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TombolKembali(onKembali)
                Text(
                    "Ganti kata sandi",
                    style = MaterialTheme.typography.titleMedium,
                    color = Tinta,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(44.dp))
            }

            Spacer(Modifier.height(24.dp))
            Text("Bikin kata sandi\nyang susah ditebak.", style = MaterialTheme.typography.headlineMedium, color = Tinta)
            Spacer(Modifier.height(8.dp))
            Text(
                "Minimal $PANJANG_MINIMAL karakter. Campur huruf dan angka biar lebih kuat.",
                style = MaterialTheme.typography.bodyMedium,
                color = TeksSekunder,
            )

            Spacer(Modifier.height(24.dp))
            KolomKataSandi(
                label = "Kata sandi lama",
                nilai = lama,
                onNilaiBerubah = {
                    lama = it
                    galatLama = null
                    berhasil = false
                },
                galat = galatLama,
                aksiKeyboard = ImeAction.Next,
            )
            Spacer(Modifier.height(8.dp))
            KolomKataSandi(
                label = "Kata sandi baru",
                nilai = baru,
                onNilaiBerubah = {
                    baru = it
                    galatBaru = null
                    berhasil = false
                },
                galat = galatBaru,
                aksiKeyboard = ImeAction.Next,
            )
            Spacer(Modifier.height(8.dp))
            KolomKataSandi(
                label = "Ulangi kata sandi baru",
                nilai = konfirmasi,
                onNilaiBerubah = {
                    konfirmasi = it
                    galatKonfirmasi = null
                    berhasil = false
                },
                galat = galatKonfirmasi,
                onSelesai = simpan,
            )

            if (berhasil) {
                Spacer(Modifier.height(8.dp))
                Kartu(Modifier.fillMaxWidth(), warna = AmanLembut, garis = AmanLembut) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.ic_check_circle), contentDescription = null, tint = AmanUtama, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(10.dp))
                        Text("Kata sandi berhasil diganti", style = MaterialTheme.typography.labelLarge, color = AmanTua)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            TombolUtama("Simpan kata sandi", R.drawable.ic_lock, onClick = simpan)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarGantiSandi() {
    TangkisTheme { LayarGantiSandi(onKembali = {}) }
}
