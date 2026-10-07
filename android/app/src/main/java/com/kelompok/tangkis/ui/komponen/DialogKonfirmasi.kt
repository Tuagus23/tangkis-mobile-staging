package com.kelompok.tangkis.ui.komponen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kelompok.tangkis.ui.theme.PenipuanUtama
import com.kelompok.tangkis.ui.theme.Permukaan
import com.kelompok.tangkis.ui.theme.TeksSekunder
import com.kelompok.tangkis.ui.theme.Tinta

// Dialog untuk aksi yang tidak bisa dibatalkan, misalnya menghapus data
@Composable
fun DialogKonfirmasi(
    judul: String,
    isi: String,
    teksKonfirmasi: String,
    @DrawableRes ikon: Int,
    onKonfirmasi: () -> Unit,
    onBatal: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onBatal,
        icon = { Icon(painterResource(ikon), contentDescription = null, tint = PenipuanUtama) },
        title = { Text(judul, style = MaterialTheme.typography.headlineSmall, color = Tinta) },
        text = { Text(isi, style = MaterialTheme.typography.bodyMedium, color = TeksSekunder) },
        confirmButton = {
            TextButton(onClick = onKonfirmasi) {
                Text(teksKonfirmasi, style = MaterialTheme.typography.labelLarge, color = PenipuanUtama)
            }
        },
        dismissButton = {
            TextButton(onClick = onBatal) {
                Text("Batal", style = MaterialTheme.typography.labelLarge, color = Tinta)
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = Permukaan,
    )
}
