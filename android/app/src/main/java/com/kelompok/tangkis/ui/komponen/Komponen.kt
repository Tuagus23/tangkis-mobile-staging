package com.kelompok.tangkis.ui.komponen

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.R
import com.kelompok.tangkis.data.Kategori
import com.kelompok.tangkis.ui.theme.Garis
import com.kelompok.tangkis.ui.theme.Lime
import com.kelompok.tangkis.ui.theme.LimeLembut
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

// Bentuk bulat pastel dekoratif di belakang konten layar
@Composable
fun BoxScope.Blob(warna: Color, ukuran: Dp, x: Dp, y: Dp, align: Alignment = Alignment.TopStart) {
    Box(
        Modifier
            .align(align)
            .offset(x, y)
            .size(ukuran)
            .clip(CircleShape)
            .background(warna)
    )
}

// Logo dan nama aplikasi berdampingan
@Composable
fun Wordmark(modifier: Modifier = Modifier, ukuranLogo: Dp = 44.dp) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(painterResource(R.drawable.logo_tangkis), contentDescription = null, modifier = Modifier.size(ukuranLogo))
        Spacer(Modifier.width(ukuranLogo / 4))
        Text("Tangkis", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, color = Tinta)
    }
}

@Composable
fun Kartu(
    modifier: Modifier = Modifier,
    warna: Color = Permukaan,
    garis: Color = Garis,
    jarakDalam: Dp = 16.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val bentuk = RoundedCornerShape(20.dp)
    Column(
        modifier = modifier
            .clip(bentuk)
            .background(warna)
            .border(1.dp, garis, bentuk)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(jarakDalam),
        content = content,
    )
}

// Tombol utama berbentuk pil hitam dengan bulatan lime berisi ikon
@Composable
fun TombolUtama(teks: String, @DrawableRes ikon: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(CircleShape)
            .background(Tinta)
            .clickable(onClick = onClick)
            .padding(start = 24.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(teks, style = MaterialTheme.typography.labelLarge, color = Permukaan)
        Box(
            Modifier.size(40.dp).clip(CircleShape).background(Lime),
            contentAlignment = Alignment.Center,
        ) {
            Icon(painterResource(ikon), contentDescription = null, tint = Tinta, modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
fun Pil(
    teks: String,
    modifier: Modifier = Modifier,
    @DrawableRes ikon: Int? = null,
    warnaLatar: Color = Permukaan,
    warnaTeks: Color = Tinta,
    warnaGaris: Color = Garis,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(warnaLatar)
            .border(1.dp, warnaGaris, CircleShape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (ikon != null) {
            Icon(painterResource(ikon), contentDescription = null, tint = warnaTeks, modifier = Modifier.size(16.dp))
        }
        Text(teks, style = MaterialTheme.typography.labelMedium, color = warnaTeks)
    }
}

@Composable
fun IkonKategori(kategori: Kategori, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(kategori.warnaLembut),
        contentAlignment = Alignment.Center,
    ) {
        Icon(painterResource(kategori.ikon), contentDescription = null, tint = kategori.warnaUtama, modifier = Modifier.size(22.dp))
    }
}

@Composable
fun TombolKembali(onClick: () -> Unit) {
    Box(
        Modifier.size(44.dp).clip(CircleShape).background(Permukaan).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = "Kembali", tint = Tinta)
    }
}

enum class Tab(val label: String, @param:DrawableRes val ikon: Int) {
    CEK("Cek", R.drawable.ic_radar),
    RIWAYAT("Riwayat", R.drawable.ic_history),
    ATUR("Atur", R.drawable.ic_settings),
}

// Navigasi melayang di atas konten dengan bayangan halus
@Composable
fun NavigasiBawah(aktif: Tab, onPilih: (Tab) -> Unit, modifier: Modifier = Modifier) {
    val bentuk = RoundedCornerShape(26.dp)
    // Sorotan tab aktif bergeser dengan efek pegas ke posisi tab yang dipilih
    val posisiSorotan by animateFloatAsState(
        aktif.ordinal.toFloat(),
        spring(dampingRatio = 0.75f, stiffness = Spring.StiffnessMediumLow),
        label = "sorotan",
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .shadow(16.dp, bentuk, ambientColor = Tinta.copy(alpha = 0.12f), spotColor = Tinta.copy(alpha = 0.18f))
            .clip(bentuk)
            .background(Permukaan)
            .border(1.dp, Garis, bentuk)
            .padding(6.dp)
            .drawBehind {
                val lebarTab = size.width / Tab.entries.size
                drawRoundRect(
                    LimeLembut,
                    topLeft = Offset(lebarTab * posisiSorotan, 0f),
                    size = Size(lebarTab, size.height),
                    cornerRadius = CornerRadius(18.dp.toPx()),
                )
            },
    ) {
        Tab.entries.forEach { tab ->
            val dipilih = tab == aktif
            val warna by animateColorAsState(if (dipilih) Tinta else TeksSekunder, label = "warnaTab")
            val skala by animateFloatAsState(if (dipilih) 1.1f else 1f, label = "skalaTab")
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onPilih(tab) }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painterResource(tab.ikon),
                    contentDescription = null,
                    tint = warna,
                    modifier = Modifier.graphicsLayer {
                        scaleX = skala
                        scaleY = skala
                    },
                )
                Text(tab.label, style = MaterialTheme.typography.labelSmall, color = warna)
            }
        }
    }
}
