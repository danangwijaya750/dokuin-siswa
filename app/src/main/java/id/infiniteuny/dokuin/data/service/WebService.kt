package id.infiniteuny.dokuin.data.service

import id.infiniteuny.dokuin.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface WebService {
    @GET("/todos/{id}")
    suspend fun getUser(@Path("id") id: String): UserModel

    @Multipart
    @POST("/file-upload")
    suspend fun uploadData(
        @Part("signator1")
        signator1: RequestBody,
        @Part("signator2")
        signator2: RequestBody,
        @Part("email")
        email: RequestBody,
        @Part("file_title")
        file_title: RequestBody,
        @Part file: MultipartBody.Part
    ): ResponseModel

    @Multipart
    @POST("/file-upload")
    suspend fun uploadData(
        @Part("signator1")
        signator1: RequestBody,
        @Part("email")
        email: RequestBody,
        @Part("file_title")
        file_title: RequestBody,
        @Part file: MultipartBody.Part
    ): ResponseModel

    @Multipart
    @POST("/file-upload")
    suspend fun uploadDataSignator2(
        @Part("signator2")
        signator2: RequestBody,
        @Part("email")
        email: RequestBody,
        @Part("file_title")
        file_title: RequestBody,
        @Part file: MultipartBody.Part
    ): ResponseModel

    @GET("/document/key")
    suspend fun getKey(): RSAKeyModel

    @GET("/my-file")
    suspend fun getMyFile(
        @Query("email")
        email: String
    ): ResponseModel

    @GET("/signator-file")
    suspend fun getMyFileSignator(
        @Query("email")
        email: String
    ): ResponseModel

    @FormUrlEncoded
    @POST("/{filename}/signature")
    suspend fun generateSignatureDocument(
        @Path("filename") filename: String,
        @Field("email") email: String
    ): VerifyResponse
    @FormUrlEncoded
    @POST("/{filename}/event-show")
    suspend fun submitAction(
        @Path("filename") filename: String,
        @Field("fullname") fullname: String,
        @Field("action") action: String
    ): ResponseModel

    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginModel


}



