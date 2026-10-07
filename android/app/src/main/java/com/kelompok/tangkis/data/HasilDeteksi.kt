package com.kelompok.tangkis.data

data class HasilDeteksi(
    val pesan: String,
    val probabilitas: Map<Kategori, Float>,
    val tandaBahaya: List<TandaBahaya>,
    val waktu: Long,
) {
    val kategori: Kategori get() = probabilitas.maxBy { it.value }.key
    val keyakinan: Float get() = probabilitas.getValue(kategori)
}
