package id.infiniteuny.dokuin.data.model

import com.google.firebase.firestore.DocumentId

data class UserModel(
    @DocumentId
    val uid: String?,
    val email: String?,
    val role: String?,
    val name: String?,
    val schoolId: String?
) {
    constructor() : this(null, null, null, null, null)
}