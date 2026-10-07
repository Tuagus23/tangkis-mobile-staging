package com.kelompok.tangkis.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.kelompok.tangkis.R
import com.kelompok.tangkis.ui.theme.AmanLembut
import com.kelompok.tangkis.ui.theme.AmanTua
import com.kelompok.tangkis.ui.theme.AmanUtama
import com.kelompok.tangkis.ui.theme.PenipuanLembut
import com.kelompok.tangkis.ui.theme.PenipuanTua
import com.kelompok.tangkis.ui.theme.PenipuanUtama
import com.kelompok.tangkis.ui.theme.PromoLembut
import com.kelompok.tangkis.ui.theme.PromoTua
import com.kelompok.tangkis.ui.theme.PromoUtama

// Tiga kelas keluaran model, urutannya sama dengan label dataset
enum class Kategori(
    val label: String,
    @param:DrawableRes val ikon: Int,
    val warnaUtama: Color,
    val warnaTua: Color,
    val warnaLembut: Color,
) {
    NORMAL("Normal", R.drawable.ic_check_circle, AmanUtama, AmanTua, AmanLembut),
    PENIPUAN("Penipuan", R.drawable.ic_warning, PenipuanUtama, PenipuanTua, PenipuanLembut),
    PROMO("Promo", R.drawable.ic_sell, PromoUtama, PromoTua, PromoLembut),
}
