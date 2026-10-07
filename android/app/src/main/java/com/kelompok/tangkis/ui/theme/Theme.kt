package com.kelompok.tangkis.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SkemaWarna = lightColorScheme(
    primary = Tinta,
    onPrimary = Permukaan,
    secondary = Lime,
    onSecondary = Tinta,
    background = Latar,
    onBackground = Tinta,
    surface = Permukaan,
    onSurface = Tinta,
    surfaceVariant = PermukaanLembut,
    onSurfaceVariant = TeksSekunder,
    outline = GarisTegas,
    outlineVariant = Garis,
    error = PenipuanUtama,
)

// Tema hanya mode terang dan tanpa warna dinamis agar tampilan sama di semua HP
@Composable
fun TangkisTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SkemaWarna,
        typography = Typography,
        content = content,
    )
}
