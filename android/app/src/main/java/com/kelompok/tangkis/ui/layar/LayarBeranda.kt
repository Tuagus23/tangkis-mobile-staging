package com.kelompok.tangkis.ui.layar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import com.kelompok.tangkis.ui.komponen.TombolUtama
import com.kelompok.tangkis.ui.komponen.Wordmark
import com.kelompok.tangkis.ui.theme.TangkisTheme
import com.kelompok.tangkis.ui.theme.Latar
import com.kelompok.tangkis.ui.theme.Lime
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.LimeTua
import com.kelompok.tangkis.ui.theme.PenipuanUtama
import com.kelompok.tangkis.ui.theme.PermukaanLembut
import com.kelompok.tangkis.ui.theme.TeksSamar
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta
import kotlinx.coroutines.launch

@Composable
fun LayarBeranda(
    riwayat: List<HasilDeteksi>,
    onPindai: (String) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    var pesan by remember { mutableStateOf("") }
    var galat by remember { mutableStateOf<String?>(null) }
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val jumlah = riwayat.groupingBy { it.kategori }.eachCount()

    Box(Modifier.fillMaxSize().background(Latar)) {
        Blob(LimeLembut, ukuran = 280.dp, x = (-60).dp, y = (-150).dp)

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Wordmark(ukuranLogo = 36.dp)
                Pil("Offline", ikon = R.drawable.ic_wifi_off, warnaTeks = LimeTua)
            }
            Spacer(Modifier.height(20.dp))
            Text(
                buildAnnotatedString {
                    append("Ragu sama\npesan ini?\n")
                    withStyle(SpanStyle(background = Lime)) { append(" Tangkisin aja! ") }
                },
                style = MaterialTheme.typography.headlineMedium,
                color = Tinta,
            )
            Spacer(Modifier.height(16.dp))

            Kartu(Modifier.fillMaxWidth()) {
                TextField(
                    value = pesan,
                    onValueChange = {
                        pesan = it
                        galat = null
                    },
                    modifier = Modifier.fillMaxWidth().height(130.dp),
                    placeholder = { Text("Tempel pesan yang bikin curiga di sini. SMS, WhatsApp, atau email, semua bisa.", color = TeksSamar) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Pil(
                        "Tempel",
                        ikon = R.drawable.ic_content_paste,
                        warnaLatar = PermukaanLembut,
                        warnaGaris = PermukaanLembut,
                        onClick = {
                            scope.launch {
                                val teks = clipboard.getClipEntry()?.clipData?.getItemAt(0)?.text
                                if (!teks.isNullOrBlank()) {
                                    pesan = teks.toString()
                                    galat = null
                                }
                            }
                        },
                    )
                    Pil(
                        "Hapus",
                        ikon = R.drawable.ic_close,
                        warnaLatar = PermukaanLembut,
                        warnaGaris = PermukaanLembut,
                        warnaTeks = TeksSekunder,
                        onClick = { pesan = "" },
                    )
                }
            }
            if (galat != null) {
                Text(
                    galat.orEmpty(),
                    style = MaterialTheme.typography.labelMedium,
                    color = PenipuanUtama,
                    modifier = Modifier.padding(start = 8.dp, top = 6.dp),
                )
            }
            Spacer(Modifier.height(12.dp))
            TombolUtama("Tangkisin sekarang", R.drawable.ic_radar) {
                if (pesan.isBlank()) galat = "Pesannya masih kosong, tempel dulu ya" else onPindai(pesan.trim())
            }

            Spacer(Modifier.height(24.dp))
            Text("Rekap tangkisanmu", style = MaterialTheme.typography.titleMedium, color = Tinta)
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth().height(180.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KartuStatistik(
                    kategori = Kategori.PENIPUAN,
                    jumlah = jumlah[Kategori.PENIPUAN] ?: 0,
                    keterangan = "penipuan tertangkis",
                    besar = true,
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                )
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    KartuStatistik(Kategori.PROMO, jumlah[Kategori.PROMO] ?: 0, "promo", modifier = Modifier.weight(1f).fillMaxWidth())
                    KartuStatistik(Kategori.NORMAL, jumlah[Kategori.NORMAL] ?: 0, "normal", modifier = Modifier.weight(1f).fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun KartuStatistik(
    kategori: Kategori,
    jumlah: Int,
    keterangan: String,
    modifier: Modifier = Modifier,
    besar: Boolean = false,
) {
    Kartu(
        modifier,
        warna = kategori.warnaLembut,
        garis = kategori.warnaLembut,
        jarakDalam = if (besar) 16.dp else 12.dp,
    ) {
        if (besar) {
            Icon(painterResource(kategori.ikon), contentDescription = null, tint = kategori.warnaUtama, modifier = Modifier.size(24.dp))
            Spacer(Modifier.weight(1f))
        }
        Text(
            jumlah.toString(),
            fontSize = if (besar) 40.sp else 22.sp,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.headlineMedium,
            color = kategori.warnaTua,
        )
        Text(keterangan, style = MaterialTheme.typography.labelMedium, color = kategori.warnaTua)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PratinjauLayarBeranda() {
    TangkisTheme { LayarBeranda(riwayat = ContohData.riwayat(), onPindai = {}) }
}
