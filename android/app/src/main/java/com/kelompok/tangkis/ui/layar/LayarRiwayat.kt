package com.kelompok.tangkis.ui.layar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.data.ContohData
import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.data.Kategori
import com.kelompok.tangkis.ui.komponen.Blob
import com.kelompok.tangkis.ui.komponen.IkonKategori
import com.kelompok.tangkis.ui.komponen.Kartu
import com.kelompok.tangkis.ui.komponen.Pil
import com.kelompok.tangkis.ui.theme.AmanLembut
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.Garis
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.Lime
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.PermukaanLembut
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun LayarRiwayat(
    riwayat: List<HasilDeteksi>,
    onPilih: (HasilDeteksi) -> Unit,
    onCekPesan: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    var filter by remember { mutableStateOf<Kategori?>(null) }
    val tampil = riwayat.filter { filter == null || it.kategori == filter }
    val jumlah = riwayat.groupingBy { it.kategori }.eachCount()

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(AmanLembut, ukuran = 260.dp, x = 90.dp, y = (-120).dp, align = Alignment.TopEnd)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = contentPadding.calculateTopPadding() + 16.dp,
                bottom = contentPadding.calculateBottomPadding() + 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Column {
                    Text("Riwayat", style = MaterialTheme.typography.headlineMedium, color = Tinta)
                    Text(
                        "${riwayat.size} pesan sudah kamu cek",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TeksSekunder,
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        PilFilter("Semua", riwayat.size, dipilih = filter == null) { filter = null }
                        Kategori.entries.forEach { k ->
                            PilFilter(k.label, jumlah[k] ?: 0, dipilih = filter == k) { filter = k }
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                }
            }
            tampil.groupBy { tanggalDari(it.waktu) }.forEach { (tanggal, daftar) ->
                item(key = tanggal.toString()) {
                    Text(
                        judulHari(tanggal),
                        style = MaterialTheme.typography.labelMedium,
                        color = TeksSekunder,
                        modifier = Modifier.animateItem().padding(top = 8.dp, start = 4.dp),
                    )
                }
                items(daftar, key = { it.waktu }) { hasil ->
                    KartuRiwayat(hasil, Modifier.animateItem()) { onPilih(hasil) }
                }
            }
            if (tampil.isEmpty()) {
                item { KosongRiwayat(filter, onCekPesan) }
            }
        }
    }
}

// Pil filter dengan lencana jumlah pesan; warnanya berganti halus saat dipilih
@Composable
private fun PilFilter(teks: String, jumlah: Int, dipilih: Boolean, onClick: () -> Unit) {
    val latar by animateColorAsState(if (dipilih) Tinta else Permukaan, label = "latar")
    val warnaTeks by animateColorAsState(if (dipilih) Permukaan else Tinta, label = "teks")
    val latarLencana by animateColorAsState(if (dipilih) Lime else PermukaanLembut, label = "lencana")
    Row(
        Modifier
            .clip(CircleShape)
            .background(latar)
            .border(1.dp, if (dipilih) latar else Garis, CircleShape)
            .clickable(onClick = onClick)
            .padding(start = 14.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(teks, style = MaterialTheme.typography.labelMedium, color = warnaTeks)
        Spacer(Modifier.width(8.dp))
        Box(
            Modifier
                .defaultMinSize(minWidth = 24.dp)
                .height(24.dp)
                .clip(CircleShape)
                .background(latarLencana)
                .padding(horizontal = 6.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(jumlah.toString(), style = MaterialTheme.typography.labelSmall, color = Tinta)
        }
    }
}

// Tampilan saat tidak ada pesan, dibedakan antara riwayat kosong dan filter tanpa hasil
@Composable
private fun KosongRiwayat(filter: Kategori?, onCekPesan: () -> Unit) {
    Kartu(Modifier.fillMaxWidth().padding(top = 8.dp), jarakDalam = 24.dp) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.size(64.dp).clip(RoundedCornerShape(20.dp)).background(filter?.warnaLembut ?: LimeLembut),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painterResource(filter?.ikon ?: R.drawable.ic_history),
                    contentDescription = null,
                    tint = filter?.warnaUtama ?: Tinta,
                    modifier = Modifier.size(30.dp),
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                if (filter == null) "Riwayatnya masih kosong" else "Belum ada pesan ${filter.label.lowercase()}",
                style = MaterialTheme.typography.titleMedium,
                color = Tinta,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                if (filter == null) "Pesan yang kamu cek akan muncul di sini." else "Coba pilih kategori lain.",
                style = MaterialTheme.typography.bodyMedium,
                color = TeksSekunder,
                textAlign = TextAlign.Center,
            )
            if (filter == null) {
                Spacer(Modifier.height(16.dp))
                Pil(
                    "Cek pesan",
                    ikon = R.drawable.ic_radar,
                    warnaLatar = Tinta,
                    warnaTeks = Permukaan,
                    warnaGaris = Tinta,
                    onClick = onCekPesan,
                )
            }
        }
    }
}

@Composable
private fun KartuRiwayat(hasil: HasilDeteksi, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Kartu(modifier.fillMaxWidth(), onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IkonKategori(hasil.kategori)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row {
                    Text(
                        "${hasil.kategori.label} ${(hasil.keyakinan * 100).roundToInt()}%",
                        style = MaterialTheme.typography.labelLarge,
                        color = hasil.kategori.warnaTua,
                        modifier = Modifier.weight(1f),
                    )
                    Text(jamDari(hasil.waktu), style = MaterialTheme.typography.labelSmall, color = TeksSamar)
                }
                Text(
                    hasil.pesan,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TeksSekunder,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

private val formatJam = DateTimeFormatter.ofPattern("HH.mm")
private val formatTanggal = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.forLanguageTag("id"))

private fun tanggalDari(waktu: Long): LocalDate = Instant.ofEpochMilli(waktu).atZone(ZoneId.systemDefault()).toLocalDate()

private fun jamDari(waktu: Long): String = Instant.ofEpochMilli(waktu).atZone(ZoneId.systemDefault()).format(formatJam)

// Judul kelompok: "Hari ini", "Kemarin", atau nama hari dan tanggal untuk yang lebih lama
private fun judulHari(tanggal: LocalDate): String {
    val hariIni = LocalDate.now(ZoneId.systemDefault())
    return when (tanggal) {
        hariIni -> "Hari ini"
        hariIni.minusDays(1) -> "Kemarin"
        else -> tanggal.format(formatTanggal)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarRiwayat() {
    TangkisTheme { LayarRiwayat(ContohData.riwayat(), onPilih = {}, onCekPesan = {}) }
}

@Preview(name = "Riwayat - kosong", showSystemUi = true)
@Composable
private fun PratinjauRiwayatKosong() {
    TangkisTheme { LayarRiwayat(emptyList(), onPilih = {}, onCekPesan = {}) }
}
