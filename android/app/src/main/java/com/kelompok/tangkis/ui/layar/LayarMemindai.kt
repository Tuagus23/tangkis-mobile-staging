package com.kelompok.tangkis.ui.layar

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.data.PendeteksiSementara
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.Lime
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.LimeTua
import com.kelompok.tangkis.ui.theme.PenipuanUtama
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta
import kotlinx.coroutines.delay

private val tahapan = listOf(
    "Membersihkan teks",
    "Mengubah kata menjadi angka",
    "Jaringan syaraf menghitung skor",
)

@Composable
fun LayarMemindai(pesan: String, onSelesai: (HasilDeteksi) -> Unit) {
    var tahapAktif by remember { mutableIntStateOf(0) }

    // Deteksi dijalankan sekali, tahapan ditampilkan bergantian selama kurang dari satu detik
    LaunchedEffect(pesan) {
        val hasil = PendeteksiSementara.deteksi(pesan)
        tahapan.indices.forEach { indeks ->
            tahapAktif = indeks
            delay(300)
        }
        onSelesai(hasil)
    }

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 340.dp, x = 0.dp, y = 90.dp, align = Alignment.TopCenter)

        Column(
            Modifier.fillMaxSize().systemBarsPadding().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Memindai pesan",
                style = MaterialTheme.typography.labelMedium,
                color = TeksSekunder,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(56.dp))
            Radar(Modifier.size(240.dp))
            Spacer(Modifier.height(36.dp))
            Text(
                "Sebentar ya,\nAI lagi baca pesanmu",
                style = MaterialTheme.typography.headlineSmall,
                color = Tinta,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(24.dp))
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                tahapan.forEachIndexed { indeks, teks ->
                    BarisTahap(teks, selesai = indeks < tahapAktif, aktif = indeks == tahapAktif)
                }
            }
        }
    }
}

// Lingkaran radar dengan sapuan yang berputar terus
@Composable
private fun Radar(modifier: Modifier = Modifier) {
    val putaran = rememberInfiniteTransition(label = "radar")
    val sudut by putaran.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Restart),
        label = "sudut",
    )
    Canvas(modifier) {
        val pusat = center
        val jari = size.minDimension / 2
        drawCircle(Permukaan, radius = jari)
        listOf(1f, 0.68f, 0.36f).forEach { skala ->
            drawCircle(LimeLembut, radius = jari * skala, style = Stroke(width = 2.dp.toPx()))
        }
        drawArc(
            color = Lime.copy(alpha = 0.5f),
            startAngle = sudut - 90f,
            sweepAngle = 60f,
            useCenter = true,
        )
        drawCircle(PenipuanUtama, radius = 6.dp.toPx(), center = Offset(pusat.x + jari * 0.45f, pusat.y - jari * 0.55f))
        drawCircle(LimeTua, radius = 4.dp.toPx(), center = Offset(pusat.x - jari * 0.38f, pusat.y + jari * 0.42f))
        drawCircle(LimeTua, radius = 4.dp.toPx(), center = Offset(pusat.x + jari * 0.55f, pusat.y + jari * 0.3f))
        drawCircle(Tinta, radius = 7.dp.toPx())
    }
}

@Composable
private fun BarisTahap(teks: String, selesai: Boolean, aktif: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when {
            selesai -> Icon(
                painterResource(R.drawable.ic_check_circle),
                contentDescription = null,
                tint = LimeTua,
                modifier = Modifier.size(22.dp),
            )
            aktif -> CircularProgressIndicator(Modifier.size(20.dp), color = Tinta, strokeWidth = 2.dp)
            else -> Box(Modifier.size(22.dp))
        }
        Spacer(Modifier.width(12.dp))
        Text(
            teks,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (aktif) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selesai || aktif) Tinta else TeksSamar,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarMemindai() {
    TangkisTheme { LayarMemindai(pesan = "Contoh pesan", onSelesai = {}) }
}
