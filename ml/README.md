# ml

Ruang kerja tim AI untuk melatih model pendeteksi pesan penipuan.

Metode: Jaringan Syaraf Tiruan jenis Multilayer Perceptron yang dilatih dengan algoritma Backpropagation, dibuat dengan Python dan TensorFlow/Keras.

## Notebook

| Notebook | Isi | Buka |
|---|---|---|
| `notebooks/01_eksplorasi_dan_preprocessing.ipynb` | Memuat data, eksplorasi, preprocessing, dan menyimpan dataset bersih | [Buka di Colab](https://colab.research.google.com/github/manrandomside/tangkis/blob/main/ml/notebooks/01_eksplorasi_dan_preprocessing.ipynb) |
| `notebooks/02_pelatihan_model.ipynb` | Pelatihan, evaluasi, dan ekspor model (menyusul) | |

## Data

| File | Isi |
|---|---|
| [SMS Spam Indonesia](https://gist.github.com/agtbaskara/a1a7017027cc1df9d35cf06e1e5575b7) | Dataset utama, 1.143 SMS berlabel `normal`, `penipuan`, `promo`. Dimuat langsung dari internet oleh notebook |
| `data/data_tambahan.csv` | Contoh pesan dari anggota kelompok, kolom `teks` dan `label` |

Aturan data tambahan: nama, nomor telepon, dan nomor rekening wajib disamarkan sebelum disimpan di repository, misalnya `081234567890` ditulis `08xxxxxxxxxx`.

## Hasil notebook

File berikut dibuat di folder `hasil` saat notebook dijalankan dan tidak disimpan di repository:

| File | Dipakai untuk |
|---|---|
| `dataset_bersih.csv` | Pelatihan model |
| `kamus_normalisasi.json` | Disalin ke aplikasi Android agar preprocessing sama |
| `*.png` | Grafik untuk slide |
