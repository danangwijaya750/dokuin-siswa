package id.infiniteuny.dokuin.data.repository


import id.infiniteuny.dokuin.data.service.WebService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadRepository(private val service: WebService) {
    suspend fun uploadFile(signator1: String,signator2: String,email:String, filename: String, formData: MultipartBody.Part) =
        service.uploadData(
            RequestBody.create(MediaType.parse("text/plain"),signator1),
            RequestBody.create(MediaType.parse("text/plain"),signator2),
            RequestBody.create(MediaType.parse("text/plain"),email),
            RequestBody.create(MediaType.parse("text/plain"),filename),
            formData)

    suspend fun uploadFile(signator1: String,email:String, filename: String, formData: MultipartBody.Part) =
        service.uploadData(
            RequestBody.create(MediaType.parse("text/plain"),signator1),
            RequestBody.create(MediaType.parse("text/plain"),email),
            RequestBody.create(MediaType.parse("text/plain"),filename),
            formData)

    suspend fun uploadFileSignator2(signator1: String,email:String, filename: String, formData: MultipartBody.Part) =
        service.uploadDataSignator2(
            RequestBody.create(MediaType.parse("text/plain"),signator1),
            RequestBody.create(MediaType.parse("text/plain"),email),
            RequestBody.create(MediaType.parse("text/plain"),filename),
            formData)

    suspend fun generateSignature(filename: String,email: String)
            = service.generateSignatureDocument(filename,email)
    suspend fun submitAction(filename: String,fullname: String,action: String)
            = service.submitAction(filename,fullname,action)

    suspend fun getMyFile(email: String)=service.getMyFile(email)
    suspend fun getMyFileSignator(email: String)=service.getMyFileSignator(email)

}