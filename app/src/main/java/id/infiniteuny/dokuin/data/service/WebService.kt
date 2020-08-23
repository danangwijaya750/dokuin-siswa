package id.infiniteuny.dokuin.data.service

import id.infiniteuny.dokuin.data.model.*
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

    @FormUrlEncoded
    @POST("/document/verify")
    suspend fun verifDocument(@Field("filename")filename:String):VerifyResponse

    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun loginUser(
        @Field("email")email:String,
        @Field("password")password:String
    ):LoginModel
}
public const val BASE_URL="http://bnpb.divistant.com:3000/document/"

