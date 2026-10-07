package com.kelompok.tangkis.data.api

import com.google.gson.annotations.SerializedName

/** Payload yang dikirim Android ke Tangkis API. */
data class CreateDetectionRequest(
    @SerializedName("pesan") val pesan: String,
    @SerializedName("kategori") val kategori: String,
    @SerializedName("keyakinan") val keyakinan: Float,
    @SerializedName("probabilitas") val probabilitas: Map<String, Float>,
    @SerializedName("tanda_bahaya") val tandaBahaya: List<String>,
    @SerializedName("waktu") val waktu: String,
)

data class DetectionDto(
    val id: Long,
    val pesan: String,
    val kategori: String,
    val keyakinan: Float,
    val probabilitas: Map<String, Float>,
    @SerializedName("tanda_bahaya") val tandaBahaya: List<String>,
    val waktu: String?,
)

data class DetectionListResponse(
    val data: List<DetectionDto>,
    val meta: DetectionMeta,
)

data class DetectionResponse(
    val data: DetectionDto,
)

data class DetectionMeta(
    val count: Int,
    val limit: Int,
)
