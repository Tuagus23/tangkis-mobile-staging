package com.kelompok.tangkis.data

// Contoh pesan fiktif untuk mengisi riwayat pada prototype
object ContohData {

    private const val MENIT = 60_000L
    private const val HARI = 24 * 60 * MENIT

    fun riwayat(sekarang: Long = System.currentTimeMillis()): List<HasilDeteksi> = listOf(
        "Selamat! Nomor Anda menang undian Rp50.000.000. Segera kirim kode OTP yang masuk ke nomor ini untuk klaim hadiah di bit.ly/klaim-hadiah" to 12 * MENIT,
        "Paket Anda tertahan di gudang. Lakukan pembayaran ulang segera melalui www.cek-paket-kurir.xyz" to 3 * 60 * MENIT,
        "Diskon 50% semua menu kopi hari ini. Tunjukkan pesan ini di kasir." to HARI,
        "Nanti sore jadi kerja kelompok di perpustakaan ya, jam 4." to 2 * HARI,
        "Cashback 20% untuk isi pulsa minggu ini, cek di aplikasi resmi kami." to 3 * HARI,
    ).map { (pesan, selisih) -> PendeteksiSementara.deteksi(pesan, sekarang - selisih) }
}
