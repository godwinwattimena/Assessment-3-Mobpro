package org.d3if3126.assessment3mobpro1.network

import com.squareup.moshi.Moshi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3126.assessment3mobpro1.model.Danus
import org.d3if3126.assessment3mobpro1.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface DanusApiService {

    @GET("api_rihansen.php")
    suspend fun getDanus(
        @Header("Authorization") userId: String
    ):List<Danus>

    @Multipart
    @POST("api_rihansen.php")
    suspend fun postDanus(
        @Header("Authorization") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("posisi") posisi: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("api_rihansen.php")
    suspend fun deleteDanus(
        @Header("Authorization") userId: String,
        @Query("id") id: String
    ) : OpStatus
}

object DanusApi{
    val service: DanusApiService by lazy {
        retrofit.create(DanusApiService::class.java)
    }
    fun getDanusUrl(imageId: String): String{
        return "${BASE_URL}image.php?id=$imageId"
    }
}

enum class ApiStatus{
    LOADING, SUCCESS, FAILED
}