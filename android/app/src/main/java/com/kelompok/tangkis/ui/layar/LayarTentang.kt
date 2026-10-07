package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.BuildConfig
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.komponen.Pil
import com.kelompok.tangkis.ui.komponen.TombolKembali
import com.kelompok.tangkis.ui.komponen.Wordmark
import com.kelompok.tangkis.ui.theme.AmanLembut
import com.kelompok.tangkis.ui.theme.AmanTua
import com.kelompok.tangkis.ui.theme.Garis
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.LimeTua
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.PermukaanLembut
import com.kelompok.tangkis.ui.theme.PromoLembut
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

private val caraKerja = listOf(
    "Teks pesan dibersihkan, lalu setiap kata diubah menjadi angka.",
    "Jaringan Syaraf Tiruan jenis Multilayer Perceptron yang dilatih dengan Backpropagation menghitung kemungkinan pesan termasuk normal, promo, atau penipuan.",
    "Sistem pakar mencocokkan pesan dengan aturan tanda bahaya, misalnya berisi tautan atau meminta kode OTP.",
)

// Urut berdasarkan NIM
private val timMkbl = listOf(
    "Alpara Verriyanta",
    "Firman Fadilah",
    "I Putu Andika Arsana Putra",
    "I Putu Agus Wahyu Wirakusuma Putra",
    "Adika Setyadharma Susilo",
    "Anak Agung Gde Agung Pranandita",
)

private val timKsm = listOf(
    "I Gede Rama Yasa Mahendra",
    "Alpara Verriyanta",
    "I Gusti Ketut Ngurah Adi Putra",
    "Firman Fadilah",
    "I Putu Agus Putra Darmawan",
    "Fadhilah Randri Besemah",
)

@Composable
fun LayarTentang(onKembali: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 320.dp, x = 0.dp, y = (-150).dp, align = Alignment.TopCenter)

        Column(
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TombolKembali(onKembali)
                Text(
                    "Tentang",
                    style = MaterialTheme.typography.titleMedium,
                    color = Tinta,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(44.dp))
            }

            Spacer(Modifier.height(24.dp))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Wordmark(ukuranLogo = 56.dp)
                Spacer(Modifier.height(8.dp))
                Text("Tangkis penipuan sebelum terjadi.", style = MaterialTheme.typography.bodyLarge, color = TeksSekunder)
                Spacer(Modifier.height(12.dp))
                Pil("Versi ${BuildConfig.VERSION_NAME}", warnaTeks = LimeTua)
            }

            Spacer(Modifier.height(24.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Apa itu Tangkis?")
                Text(
                    "Tangkis membantu kamu mengecek pesan SMS, WhatsApp, atau email yang mencurigakan. " +
                        "Cukup tempel isi pesannya, Tangkis akan menilai apakah pesan itu normal, promo, atau penipuan, " +
                        "lengkap dengan alasan dan saran tindakan.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TeksSekunder,
                )
            }

            Spacer(Modifier.height(12.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Cara kerja")
                caraKerja.forEachIndexed { indeks, teks ->
                    Row(Modifier.padding(vertical = 6.dp)) {
                        Box(
                            Modifier.size(26.dp).clip(CircleShape).background(LimeLembut),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("${indeks + 1}", style = MaterialTheme.typography.labelMedium, color = Tinta)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(teks, style = MaterialTheme.typography.bodyMedium, color = Tinta)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Kartu(Modifier.fillMaxWidth(), warna = AmanLembut, garis = AmanLembut) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.ic_wifi_off), contentDescription = null, tint = AmanTua, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "Tangkis bekerja offline. Pesan yang kamu cek tidak pernah keluar dari HP.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AmanTua,
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            KartuTim()

            Spacer(Modifier.height(20.dp))
            Text(
                "Jangan asal klik tautan, cek dulu di Tangkis.",
                style = MaterialTheme.typography.labelSmall,
                color = TeksSamar,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun Judul(teks: String) {
    Text(teks, style = MaterialTheme.typography.titleMedium, color = Tinta, modifier = Modifier.padding(bottom = 10.dp))
}

// Kartu tim dengan pilihan mata kuliah, karena susunan anggota MKBL dan KSM berbeda
@Composable
private fun KartuTim() {
    var tampilMkbl by remember { mutableStateOf(true) }
    val anggota = if (tampilMkbl) timMkbl else timKsm

    Kartu(Modifier.fillMaxWidth()) {
        Judul("Tim pengembang")
        Row(
            Modifier.fillMaxWidth().clip(CircleShape).background(PermukaanLembut).padding(4.dp),
        ) {
            TombolSegmen("Tim MKBL", dipilih = tampilMkbl, modifier = Modifier.weight(1f)) { tampilMkbl = true }
            TombolSegmen("Tim KSM", dipilih = !tampilMkbl, modifier = Modifier.weight(1f)) { tampilMkbl = false }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            if (tampilMkbl) "Metode Kecerdasan Buatan Lanjut" else "Keamanan Sistem Mobile",
            style = MaterialTheme.typography.labelMedium,
            color = TeksSekunder,
        )
        Spacer(Modifier.height(4.dp))
        anggota.forEachIndexed { indeks, nama ->
            Row(Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(38.dp).clip(CircleShape).background(warnaAvatar[indeks % warnaAvatar.size]),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(inisial(nama), style = MaterialTheme.typography.labelMedium, color = Tinta)
                }
                Spacer(Modifier.width(12.dp))
                Text(nama, style = MaterialTheme.typography.bodyMedium, color = Tinta)
            }
        }
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = Garis)
        Spacer(Modifier.height(12.dp))
        Text("Dosen pengampu", style = MaterialTheme.typography.labelMedium, color = TeksSekunder)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(R.drawable.foto_surya),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(52.dp).clip(CircleShape),
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text("I Gede Surya Rahayuda, M.Kom.", style = MaterialTheme.typography.titleMedium, color = Tinta)
                Text("Informatika, FMIPA, Universitas Udayana", style = MaterialTheme.typography.bodyMedium, color = TeksSekunder)
            }
        }
    }
}

@Composable
private fun TombolSegmen(teks: String, dipilih: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier
            .clip(CircleShape)
            .background(if (dipilih) Tinta else PermukaanLembut)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(teks, style = MaterialTheme.typography.labelLarge, color = if (dipilih) Permukaan else TeksSekunder)
    }
}

private val warnaAvatar = listOf(LimeLembut, AmanLembut, PromoLembut)

// Dua huruf awal dari dua kata pertama nama
private fun inisial(nama: String) = nama.split(" ").take(2).joinToString("") { it.first().uppercase() }

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarTentang() {
    TangkisTheme { LayarTentang(onKembali = {}) }
}

// Seluruh isi halaman tanpa perlu digulir
@Preview(name = "Tentang - halaman penuh", widthDp = 360, heightDp = 1700)
@Composable
private fun PratinjauLayarTentangPenuh() {
    TangkisTheme { LayarTentang(onKembali = {}) }
}
