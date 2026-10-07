package com.kelompok.tangkis.data

import androidx.annotation.DrawableRes
import com.kelompok.tangkis.R

// Basis aturan sistem pakar: JIKA pola ditemukan di pesan MAKA tanda bahaya berlaku
enum class TandaBahaya(
    val label: String,
    @param:DrawableRes val ikon: Int,
    private val pola: Regex,
) {
    TAUTAN("Ada tautan", R.drawable.ic_link, Regex("""https?://|www\.|bit\.ly|\.(com|id|xyz|net|info)\b""")),
    MINTA_KODE("Minta kode OTP/PIN", R.drawable.ic_key, Regex("""\b(otp|kode verifikasi|kode rahasia|pin)\b""")),
    HADIAH("Iming-iming hadiah", R.drawable.ic_redeem, Regex("""\b(hadiah|undian|menang|cek tunai)\b""")),
    DESAKAN("Desakan waktu", R.drawable.ic_schedule, Regex("""\b(segera|sekarang juga|hari ini juga|batas waktu)\b"""));

    companion object {
        // Forward chaining: mencocokkan fakta (isi pesan) dengan setiap aturan
        fun periksa(pesan: String): List<TandaBahaya> {
            val teks = pesan.lowercase()
            return entries.filter { it.pola.containsMatchIn(teks) }
        }
    }
}
