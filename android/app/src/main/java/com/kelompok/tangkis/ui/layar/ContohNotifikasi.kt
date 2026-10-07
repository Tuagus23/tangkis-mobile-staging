package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.data.Kategori
import com.kelompok.tangkis.ui.komponen.Pil
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.PermukaanLembut
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

// Desain notifikasi deteksi SMS otomatis; notifikasi sungguhan dibuat setelah UTS
@Composable
private fun KartuNotifikasi(
    judul: String,
    pengirim: String,
    cuplikan: String,
    kategori: Kategori,
    keyakinan: Int,
    waktu: String,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Permukaan)
            .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(R.drawable.logo_tangkis), contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("Tangkis", style = MaterialTheme.typography.labelMedium, color = Tinta)
            Text("  ·  $waktu", style = MaterialTheme.typography.labelMedium, color = TeksSekunder)
        }
        Spacer(Modifier.height(10.dp))
        Text(judul, style = MaterialTheme.typography.titleMedium, color = Tinta)
        Text(
            "Dari $pengirim: $cuplikan",
            style = MaterialTheme.typography.bodyMedium,
            color = TeksSekunder,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.height(10.dp))
        Pil(
            "${kategori.label} $keyakinan%",
            ikon = kategori.ikon,
            warnaLatar = kategori.warnaLembut,
            warnaTeks = kategori.warnaTua,
            warnaGaris = kategori.warnaLembut,
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Pil("Lihat detail", warnaLatar = Tinta, warnaTeks = Permukaan, warnaGaris = Tinta)
            Pil("Abaikan", warnaLatar = PermukaanLembut, warnaGaris = PermukaanLembut)
        }
    }
}

@Composable
private fun ContohNotifikasi() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF22261E))
            .padding(horizontal = 16.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("09.41", fontSize = 56.sp, fontWeight = FontWeight.Medium, color = Permukaan, modifier = Modifier.padding(start = 8.dp))
        Text("Jumat, 3 Oktober", style = MaterialTheme.typography.titleMedium, color = Permukaan.copy(alpha = 0.8f), modifier = Modifier.padding(start = 8.dp, bottom = 16.dp))
        KartuNotifikasi(
            judul = "Awas, ini kemungkinan penipuan",
            pengirim = "0812-xxxx-xxxx",
            cuplikan = "Selamat! Nomor Anda menang undian Rp50.000.000. Segera kirim kode OTP untuk klaim hadiah.",
            kategori = Kategori.PENIPUAN,
            keyakinan = 92,
            waktu = "sekarang",
        )
        KartuNotifikasi(
            judul = "Pesan promo masuk",
            pengirim = "TELKOMSEL",
            cuplikan = "Kuota 30GB cuma Rp50 ribu, aktifkan sekarang di *363#.",
            kategori = Kategori.PROMO,
            keyakinan = 85,
            waktu = "5 menit lalu",
        )
    }
}

@Preview(name = "Contoh notifikasi", showSystemUi = true)
@Composable
private fun PratinjauContohNotifikasi() {
    TangkisTheme { ContohNotifikasi() }
}
