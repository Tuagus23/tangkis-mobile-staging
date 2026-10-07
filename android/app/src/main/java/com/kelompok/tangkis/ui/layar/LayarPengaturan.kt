package com.kelompok.tangkis.ui.layar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.BuildConfig
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.DialogKonfirmasi
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.PenipuanLembut
import com.kelompok.tangkis.ui.theme.PenipuanUtama
import com.kelompok.tangkis.ui.theme.PermukaanLembut
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

@Composable
fun LayarPengaturan(
    jumlahRiwayat: Int,
    onGantiSandi: () -> Unit,
    onKunci: () -> Unit,
    onHapusRiwayat: () -> Unit,
    onTentang: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    var tanyaHapus by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 280.dp, x = 100.dp, y = (-130).dp, align = Alignment.TopEnd)

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Text("Pengaturan", style = MaterialTheme.typography.headlineMedium, color = Tinta)
            Text("Atur keamanan dan data Tangkis-mu", style = MaterialTheme.typography.bodyMedium, color = TeksSekunder)

            JudulBagian("Keamanan")
            BarisMenu(
                ikon = R.drawable.ic_lock,
                judul = "Kunci Tangkis",
                keterangan = "Kembali ke layar kunci sekarang",
                onClick = onKunci,
            )
            Spacer(Modifier.height(8.dp))
            BarisMenu(
                ikon = R.drawable.ic_key,
                judul = "Ganti kata sandi",
                keterangan = "Ubah kata sandi untuk membuka Tangkis",
                onClick = onGantiSandi,
            )

            JudulBagian("Data")
            BarisMenu(
                ikon = R.drawable.ic_delete,
                judul = "Hapus semua riwayat",
                keterangan = if (jumlahRiwayat > 0) "$jumlahRiwayat pesan tersimpan di HP ini" else "Riwayat sudah kosong",
                warnaIkon = PenipuanUtama,
                latarIkon = PenipuanLembut,
                onClick = if (jumlahRiwayat > 0) ({ tanyaHapus = true }) else null,
            )

            JudulBagian("Deteksi otomatis")
            BarisMenu(
                ikon = R.drawable.ic_notifications,
                judul = "Cek SMS masuk otomatis",
                keterangan = "Segera hadir. Tangkis akan memberi notifikasi bila ada SMS mencurigakan.",
                ujung = {
                    Switch(
                        checked = false,
                        onCheckedChange = null,
                        enabled = false,
                        colors = SwitchDefaults.colors(disabledUncheckedTrackColor = PermukaanLembut),
                    )
                },
            )

            JudulBagian("Lainnya")
            BarisMenu(
                ikon = R.drawable.ic_info,
                judul = "Tentang Tangkis",
                keterangan = "Cara kerja dan tim pengembang",
                onClick = onTentang,
            )

            Spacer(Modifier.height(24.dp))
            Text(
                "Tangkis versi ${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.labelSmall,
                color = TeksSamar,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    if (tanyaHapus) {
        DialogKonfirmasi(
            judul = "Hapus semua riwayat?",
            isi = "Semua $jumlahRiwayat pesan yang pernah kamu cek akan dihapus dari HP ini dan tidak bisa dikembalikan.",
            teksKonfirmasi = "Hapus",
            ikon = R.drawable.ic_delete,
            onKonfirmasi = {
                onHapusRiwayat()
                tanyaHapus = false
            },
            onBatal = { tanyaHapus = false },
        )
    }
}

@Composable
private fun JudulBagian(teks: String) {
    Text(
        teks,
        style = MaterialTheme.typography.labelMedium,
        color = TeksSekunder,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp, start = 4.dp),
    )
}

// Satu baris menu: ikon, judul, keterangan, dan elemen di ujung kanan
@Composable
private fun BarisMenu(
    @DrawableRes ikon: Int,
    judul: String,
    keterangan: String,
    warnaIkon: Color = Tinta,
    latarIkon: Color = LimeLembut,
    onClick: (() -> Unit)? = null,
    ujung: @Composable () -> Unit = {
        if (onClick != null) {
            Icon(painterResource(R.drawable.ic_chevron_right), contentDescription = null, tint = TeksSekunder)
        }
    },
) {
    Kartu(Modifier.fillMaxWidth(), jarakDalam = 14.dp, onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(latarIkon),
                contentAlignment = Alignment.Center,
            ) {
                Icon(painterResource(ikon), contentDescription = null, tint = warnaIkon, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(judul, style = MaterialTheme.typography.titleMedium, color = Tinta)
                Text(keterangan, style = MaterialTheme.typography.bodyMedium, color = TeksSekunder)
            }
            Spacer(Modifier.width(8.dp))
            ujung()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarPengaturan() {
    TangkisTheme { LayarPengaturan(jumlahRiwayat = 5, onGantiSandi = {}, onKunci = {}, onHapusRiwayat = {}, onTentang = {}) }
}
