package id.infiniteuny.dokuin.data.service

import id.infiniteuny.dokuin.data.model.UserModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService{
    @GET("/todos/{id}")
    suspend fun getUser(@Path("id")id:String): UserModel
}
val apiService : WebService by lazy {
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WebService::class.java)
}