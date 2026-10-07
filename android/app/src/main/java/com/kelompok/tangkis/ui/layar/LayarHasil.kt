package com.kelompok.tangkis.ui.layar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.data.ContohData
import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.data.Kategori
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.komponen.Pil
import com.kelompok.tangkis.ui.komponen.TombolKembali
import com.kelompok.tangkis.ui.komponen.TombolUtama
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.PermukaanLembut
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta
import kotlin.math.roundToInt

private fun saran(kategori: Kategori) = when (kategori) {
    Kategori.PENIPUAN -> listOf(
        "Jangan klik tautan di pesan ini",
        "Jangan bagikan kode OTP atau PIN",
        "Blokir dan laporkan nomor pengirim",
    )
    Kategori.PROMO -> listOf(
        "Pastikan pengirimnya akun resmi",
        "Cek kebenaran promo di aplikasi atau situs resmi",
        "Kalau nggak tertarik, abaikan saja",
    )
    Kategori.NORMAL -> listOf(
        "Pesan ini kelihatannya wajar",
        "Tetap waspada kalau diminta data pribadi atau uang",
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LayarHasil(hasil: HasilDeteksi, onKembali: () -> Unit, onCekLagi: () -> Unit) {
    val kategori = hasil.kategori

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(kategori.warnaLembut, ukuran = 360.dp, x = 0.dp, y = (-170).dp, align = Alignment.TopCenter)

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
                    "Hasil deteksi",
                    style = MaterialTheme.typography.titleMedium,
                    color = Tinta,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.width(44.dp))
            }

            Spacer(Modifier.height(20.dp))
            Meteran(hasil.keyakinan, kategori, Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(12.dp))
            Pil(
                teks = "Kemungkinan ${kategori.label.lowercase()}",
                ikon = kategori.ikon,
                warnaLatar = kategori.warnaUtama,
                warnaTeks = Permukaan,
                warnaGaris = kategori.warnaUtama,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            if (hasil.tandaBahaya.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))
                Judul("Kenapa dicurigai?")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    hasil.tandaBahaya.forEach { tanda ->
                        Pil(tanda.label, ikon = tanda.ikon, warnaTeks = kategori.warnaTua, warnaGaris = kategori.warnaLembut)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Yang sebaiknya kamu lakukan")
                saran(kategori).forEachIndexed { indeks, teks ->
                    Row(Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
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
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Skor tiap kategori")
                Kategori.entries.sortedByDescending { hasil.probabilitas.getValue(it) }.forEachIndexed { indeks, k ->
                    BarisSkor(k, hasil.probabilitas.getValue(k), tunda = 200 + indeks * 120)
                }
            }

            Spacer(Modifier.height(12.dp))
            Kartu(Modifier.fillMaxWidth()) {
                Judul("Pesan yang dicek")
                Text(hasil.pesan, style = MaterialTheme.typography.bodyMedium, color = TeksSekunder)
            }

            Spacer(Modifier.height(12.dp))
            Text(
                "Ini perkiraan AI, tetap pakai nalarmu ya.",
                style = MaterialTheme.typography.labelSmall,
                color = TeksSamar,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))
            TombolUtama("Tangkis pesan lain", R.drawable.ic_radar, onClick = onCekLagi)
        }
    }
}

// Meteran setengah lingkaran yang terisi sesuai tingkat keyakinan
@Composable
private fun Meteran(keyakinan: Float, kategori: Kategori, modifier: Modifier = Modifier) {
    val diPratinjau = LocalInspectionMode.current
    val progres = remember(keyakinan) { Animatable(if (diPratinjau) keyakinan else 0f) }
    LaunchedEffect(keyakinan) {
        progres.animateTo(keyakinan, tween(900, easing = FastOutSlowInEasing))
    }

    Box(modifier.size(width = 240.dp, height = 136.dp), contentAlignment = Alignment.BottomCenter) {
        Canvas(Modifier.fillMaxSize()) {
            val tebal = 18.dp.toPx()
            val ukuranBusur = Size(size.width - tebal, (size.width - tebal))
            val posisi = Offset(tebal / 2, tebal / 2)
            drawArc(kategori.warnaLembut, 180f, 180f, false, posisi, ukuranBusur, style = Stroke(tebal, cap = StrokeCap.Round))
            drawArc(kategori.warnaUtama, 180f, 180f * progres.value, false, posisi, ukuranBusur, style = Stroke(tebal, cap = StrokeCap.Round))
        }
        Text(
            "${(progres.value * 100).roundToInt()}%",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.displaySmall,
            color = kategori.warnaTua,
        )
    }
}

// Bar skor terisi bergantian setelah jeda, mengikuti gerak meteran di atasnya
@Composable
private fun BarisSkor(kategori: Kategori, nilai: Float, tunda: Int) {
    val diPratinjau = LocalInspectionMode.current
    val progres = remember(nilai) { Animatable(if (diPratinjau) nilai else 0f) }
    LaunchedEffect(nilai) {
        progres.animateTo(nilai, tween(800, delayMillis = tunda, easing = FastOutSlowInEasing))
    }

    Row(Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(kategori.label, style = MaterialTheme.typography.labelMedium, color = TeksSekunder, modifier = Modifier.width(76.dp))
        LinearProgressIndicator(
            progress = { progres.value },
            modifier = Modifier.weight(1f).height(8.dp).clip(CircleShape),
            color = kategori.warnaUtama,
            trackColor = PermukaanLembut,
            drawStopIndicator = {},
        )
        Text(
            "${(progres.value * 100).roundToInt()}%",
            style = MaterialTheme.typography.labelMedium,
            color = Tinta,
            textAlign = TextAlign.End,
            modifier = Modifier.width(48.dp),
        )
    }
}

@Composable
private fun Judul(teks: String) {
    Text(teks, style = MaterialTheme.typography.titleMedium, color = Tinta, modifier = Modifier.padding(bottom = 10.dp))
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarHasil() {
    TangkisTheme { LayarHasil(ContohData.riwayat().first(), onKembali = {}, onCekLagi = {}) }
}
