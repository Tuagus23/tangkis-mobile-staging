package com.kelompok.tangkis.data.api

import com.kelompok.tangkis.data.HasilDeteksi
import com.kelompok.tangkis.data.Kategori
import com.kelompok.tangkis.data.TandaBahaya
import java.time.Instant

fun HasilDeteksi.toCreateDetectionRequest(): CreateDetectionRequest = CreateDetectionRequest(
    pesan = pesan,
    kategori = kategori.name,
    keyakinan = keyakinan,
    probabilitas = probabilitas.mapKeys { it.key.name },
    tandaBahaya = tandaBahaya.map { it.label },
    waktu = Instant.ofEpochMilli(waktu).toString(),
)

fun DetectionDto.toHasilDeteksiOrNull(): HasilDeteksi? {
    val mappedCategory = runCatching { Kategori.valueOf(kategori) }.getOrNull() ?: return null
    val mappedProbabilities = probabilitas.mapNotNull { (key, value) ->
        runCatching { Kategori.valueOf(key) }.getOrNull()?.let { it to value }
    }.toMap()
    val waktuMillis = runCatching { waktu?.let(Instant::parse)?.toEpochMilli() }.getOrNull()
        ?: System.currentTimeMillis()
    val mappedDangerSigns = tandaBahaya.mapNotNull { label ->
        TandaBahaya.entries.firstOrNull { it.label == label }
    }

    val completedProbabilities = Kategori.entries.associateWith { category ->
        mappedProbabilities[category] ?: if (category == mappedCategory) keyakinan else 0f
    }

    return HasilDeteksi(
        pesan = pesan,
        probabilitas = completedProbabilities,
        tandaBahaya = mappedDangerSigns,
        waktu = waktuMillis,
    )
}
