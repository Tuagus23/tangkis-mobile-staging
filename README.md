<p align="center">
  <img src="docs/brand/tangkis-logo.svg" alt="Logo Tangkis" width="120">
</p>

<h1 align="center">Tangkis</h1>

<p align="center"><b>Tangkis penipuan sebelum terjadi.</b></p>

Tangkis adalah aplikasi Android untuk mendeteksi pesan penipuan dari SMS, WhatsApp, maupun email. Pengguna cukup menempelkan isi pesan, lalu aplikasi menampilkan kategori **normal**, **promo**, atau **penipuan** beserta tingkat keyakinan, tanda bahaya yang ditemukan, dan saran tindakan. Seluruh proses berjalan di dalam perangkat tanpa koneksi internet, sehingga isi pesan tidak pernah keluar dari HP.

## Fitur

| Fitur | Status |
|---|---|
| Kunci aplikasi dengan kata sandi | Tampilan selesai, verifikasi hash menyusul |
| Cek pesan dengan menempelkan teks | Selesai |
| Hasil deteksi: kategori, keyakinan, tanda bahaya, saran | Tampilan selesai, model JST menyusul |
| Riwayat pengecekan dengan filter kategori | Tampilan selesai, enkripsi menyusul |
| Deteksi SMS otomatis dan notifikasi | Direncanakan |

## Cara kerja

1. Teks pesan dibersihkan (case folding, pembersihan simbol, tokenisasi) lalu diubah menjadi vektor angka berdasarkan kosakata.
2. Model **Jaringan Syaraf Tiruan jenis Multilayer Perceptron** yang dilatih dengan algoritma **Backpropagation** menghasilkan probabilitas tiga kelas melalui fungsi aktivasi softmax.
3. Aturan **tanda bahaya** berbasis sistem pakar (forward chaining) menjelaskan alasan sebuah pesan dicurigai, misalnya berisi tautan atau meminta kode OTP.

> Prototype saat ini memakai pendeteksi sementara berbasis aturan sampai model TensorFlow Lite selesai dilatih.

## Keamanan

- Aplikasi tidak meminta izin INTERNET.
- Kata sandi disimpan sebagai hash PBKDF2 dengan salt (direncanakan).
- Riwayat dienkripsi AES dengan kunci dari Android Keystore (direncanakan).
- Kode build rilis disamarkan dengan R8 (direncanakan).
- Pengujian keamanan dengan MobSF mengacu pada OWASP MASVS (direncanakan).

## Teknologi

| Bagian | Teknologi |
|---|---|
| Aplikasi | Kotlin, Jetpack Compose, Material 3, minimum Android 8.0 (API 26) |
| Model AI | Python, TensorFlow/Keras, TensorFlow Lite, Google Colab |

## Struktur repository

```
tangkis/
├── android/   Aplikasi Android (buka folder ini di Android Studio)
├── ml/        Notebook, dataset, dan model untuk tim AI
└── docs/      Dokumen requirement, aset logo, dan lisensi pihak ketiga
```

## Menjalankan aplikasi

1. Clone repository ini.
2. Buka folder `android/` di Android Studio.
3. Tunggu Gradle Sync selesai, lalu tekan **Run**.

## Tentang project

Project kelompok mata kuliah **Metode Kecerdasan Buatan Lanjut** dan **Keamanan Sistem Mobile**, Program Studi Informatika, FMIPA, Universitas Udayana, 2026.

## Lisensi dan atribusi

- Kode sumber: [MIT License](LICENSE).
- Font [Poppins](https://github.com/itfoundry/Poppins): SIL Open Font License 1.1, lihat [docs/licenses/Poppins-OFL.txt](docs/licenses/Poppins-OFL.txt).
- Ikon [Material Symbols](https://github.com/google/material-design-icons): Apache License 2.0.
- Dataset [SMS Spam Indonesia](https://gist.github.com/agtbaskara/a1a7017027cc1df9d35cf06e1e5575b7) oleh agtbaskara.
