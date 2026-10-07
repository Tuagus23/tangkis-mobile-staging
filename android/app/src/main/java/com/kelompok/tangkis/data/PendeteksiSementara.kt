package com.kelompok.tangkis.data

// Pengganti sementara model JST untuk prototype UTS; diganti inferensi TensorFlow Lite setelah UTS
object PendeteksiSementara {

    private val polaPromo = Regex("""\b(diskon|promo|cashback|voucher|potongan|gratis ongkir)\b""")

    fun deteksi(pesan: String, waktu: Long = System.currentTimeMillis()): HasilDeteksi {
        val tanda = TandaBahaya.periksa(pesan)
        val probabilitas = when {
            tanda.size >= 2 -> skor(penipuan = 0.92f, promo = 0.06f, normal = 0.02f)
            tanda.size == 1 -> skor(penipuan = 0.64f, promo = 0.21f, normal = 0.15f)
            polaPromo.containsMatchIn(pesan.lowercase()) -> skor(penipuan = 0.08f, promo = 0.81f, normal = 0.11f)
            else -> skor(penipuan = 0.03f, promo = 0.05f, normal = 0.92f)
        }
        return HasilDeteksi(pesan, probabilitas, tanda, waktu)
    }

    private fun skor(penipuan: Float, promo: Float, normal: Float) = mapOf(
        Kategori.PENIPUAN to penipuan,
        Kategori.PROMO to promo,
        Kategori.NORMAL to normal,
    )
}
