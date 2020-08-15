package id.infiniteuny.dokuin.data.repository

import id.infiniteuny.dokuin.data.service.WebService
import id.infiniteuny.dokuin.data.service.apiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UploadRepository {
    private val service: WebService = apiService

    suspend fun uploadFile(uid:String,filename:String,formData:MultipartBody.Part) = service.uploadData(formData)
    suspend fun verifyFile(filename: String)=service.verifDocument(filename)

}