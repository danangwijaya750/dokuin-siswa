package id.infiniteuny.dokuin.data.service

import com.google.gson.annotations.SerializedName
import id.infiniteuny.dokuin.data.model.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface WebServiceBigBox{

    @PUT("email-otp/0.0.1/send/{userid}")
    suspend fun sendEmailOTP(
        @Path("userid")userId:String,
        @Body data:SendEmailOTP
        ):BigBoxResponse

    @FormUrlEncoded
    @POST("email-otp/0.0.1/verify/{userid}")
    suspend fun verifyEmailOTP(
        @Path("userid")userId:String,
        @Field("digit")
        digit: Int,
        @Field("expire")
        expire: Int,
        @Field("otpstr")
        otpstr: String
    ):BigBoxResponse

    @FormUrlEncoded
    @POST("/sms-notification/1.0.0/messages")
    suspend fun sendNotification(
        @Field("msisdn") msisdn:String,
        @Field("content")content:String
    ):SendNotifResponse


}


