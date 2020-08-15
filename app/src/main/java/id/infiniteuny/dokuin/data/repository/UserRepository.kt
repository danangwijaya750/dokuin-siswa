package id.infiniteuny.dokuin.data.repository

import id.infiniteuny.dokuin.data.service.WebService
import id.infiniteuny.dokuin.data.service.apiService

class UserRepository {
    private val service: WebService = apiService

    suspend fun getUser(id:String) = service.getUser(id)
    suspend fun getKey()=service.getKey()
}