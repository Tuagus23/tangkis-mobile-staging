package com.kelompok.tangkis.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TangkisApiService {
    @GET("health")
    suspend fun health(): Map<String, Any>

    @GET("detections")
    suspend fun getDetections(
        @Query("limit") limit: Int = 100,
        @Query("category") category: String? = null,
    ): DetectionListResponse

    @POST("detections")
    suspend fun createDetection(
        @Body request: CreateDetectionRequest,
    ): DetectionResponse

    @GET("stats")
    suspend fun getStats(): Map<String, Any>
}
