package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.DialogKonfirmasi
import com.kelompok.tangkis.ui.komponen.KolomKataSandi
import com.kelompok.tangkis.ui.komponen.TombolUtama
import com.kelompok.tangkis.ui.komponen.Wordmark
import com.kelompok.tangkis.ui.theme.AmanLembut
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.Lime
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

@Composable
fun LayarKunci(onTerbuka: () -> Unit, onReset: () -> Unit) {
    var kataSandi by remember { mutableStateOf("") }
    var galat by remember { mutableStateOf<String?>(null) }
    var tanyaReset by remember { mutableStateOf(false) }

    // Verifikasi hash PBKDF2 ditambahkan pada tahap implementasi keamanan
    val buka = {
        if (kataSandi.isBlank()) galat = "Kata sandinya belum diisi nih" else onTerbuka()
    }

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 300.dp, x = 110.dp, y = (-110).dp, align = Alignment.TopEnd)
        Blob(AmanLembut, ukuran = 160.dp, x = (-70).dp, y = 170.dp)

        Column(
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Spacer(Modifier.height(32.dp))
            Wordmark(ukuranLogo = 52.dp)
            Spacer(Modifier.height(24.dp))
            Text(
                buildAnnotatedString {
                    append("Penipu makin kreatif,\nkamu makin ")
                    withStyle(SpanStyle(background = Lime)) { append(" waspada. ") }
                },
                style = MaterialTheme.typography.displaySmall,
                color = Tinta,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Masukkan kata sandi dulu, biar cuma kamu yang bisa buka riwayat pesanmu.",
                style = MaterialTheme.typography.bodyLarge,
                color = TeksSekunder,
            )

            Spacer(Modifier.weight(1f))

            KolomKataSandi(
                label = "Kata sandi",
                nilai = kataSandi,
                onNilaiBerubah = {
                    kataSandi = it
                    galat = null
                },
                galat = galat,
                onSelesai = buka,
            )
            Spacer(Modifier.height(12.dp))
            TombolUtama("Buka Tangkis", R.drawable.ic_arrow_forward, onClick = buka)
            TextButton(onClick = { tanyaReset = true }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Lupa kata sandi?", style = MaterialTheme.typography.labelLarge, color = Tinta)
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(painterResource(R.drawable.ic_wifi_off), contentDescription = null, tint = TeksSekunder, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(
                    "Offline, pesanmu nggak ke mana-mana",
                    style = MaterialTheme.typography.labelSmall,
                    color = TeksSekunder,
                )
            }
        }
    }

    // Kata sandi tidak bisa dipulihkan karena hanya hash-nya yang disimpan dan tidak ada akun online
    if (tanyaReset) {
        DialogKonfirmasi(
            judul = "Lupa kata sandi?",
            isi = "Kata sandi tidak bisa dipulihkan karena Tangkis tidak memakai akun online. " +
                "Kamu bisa mereset aplikasi, tapi seluruh riwayat pesan akan ikut terhapus.",
            teksKonfirmasi = "Reset aplikasi",
            ikon = R.drawable.ic_key,
            onKonfirmasi = {
                onReset()
                kataSandi = ""
                galat = null
                tanyaReset = false
            },
            onBatal = { tanyaReset = false },
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarKunci() {
    TangkisTheme { LayarKunci(onTerbuka = {}, onReset = {}) }
}
