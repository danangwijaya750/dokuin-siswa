package id.infiniteuny.dokuin.data.service

import id.infiniteuny.dokuin.data.model.RSAKeyModel
import id.infiniteuny.dokuin.data.model.ResponseModel
import id.infiniteuny.dokuin.data.model.UserModel
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface WebService{
    @GET("/todos/{id}")
    suspend fun getUser(@Path("id")id:String): UserModel

    @Multipart
    @POST("/document")
    suspend fun uploadData(
        @Part file : MultipartBody.Part
    ):ResponseModel

    @GET("/document/key")
    suspend fun getKey():RSAKeyModel

}

val client:OkHttpClient by lazy {
    OkHttpClient.Builder()
        .connectTimeout(60L,TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val req = chain.request()
                .newBuilder()
                .build()
            return@addInterceptor chain.proceed(req)
        }
        .build()
}

val apiService : WebService by lazy {
    Retrofit.Builder()
        .baseUrl("https://a64a2e07d0ef.ngrok.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(WebService::class.java)
}