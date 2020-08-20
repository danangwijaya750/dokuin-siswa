package id.infiniteuny.dokuin.data.repository

import id.infiniteuny.dokuin.data.service.WebService
import okhttp3.MultipartBody

class UploadRepository(private val service: WebService) {
    suspend fun uploadFile(uid: String, filename: String, formData: MultipartBody.Part) =
        service.uploadData(formData)

    suspend fun verifyFile(filename: String) = service.verifDocument(filename)

}