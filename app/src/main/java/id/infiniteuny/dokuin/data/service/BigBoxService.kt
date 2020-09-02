package id.infiniteuny.dokuin.data.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BigBoxService {
    private fun createClient():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val req = chain.request()
                    .newBuilder()
                    .addHeader("x-api-key","gY3ksYymDkGVUA1DzK3r8c8mtb5HDeE5")
                    .build()
                return@addInterceptor chain.proceed(req)
            }
            .build()
    }
    private fun createRetorfit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BIG_BOX_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
    }
    fun createService():WebServiceBigBox{
        return createRetorfit().create(WebServiceBigBox::class.java)
    }
    const val BASE_URL="http://bnpb.divistant.com:3000/document/"
    const val BIG_BOX_URL="https://api.thebigbox.id/"
}