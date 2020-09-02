package id.infiniteuny.dokuin.di

import id.infiniteuny.dokuin.data.service.BigBoxService.BASE_URL
import id.infiniteuny.dokuin.data.service.WebService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module{
    single{
            OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
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
    single{
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        }
    single{
        createService<WebService>(get())
    }
}
inline fun<reified T> createService(retrofit: Retrofit):T=retrofit.create(T::class.java)
