package id.infiniteuny.dokuin.ui.login

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.util.logD

class AuthPresenter(
    private val view: AuthView
) : BasePresenter() {
    private val userRef = FirebaseFirestore.getInstance().collection("user")
    fun getUserProfileData(uid: String) {
        userRef.document(uid).get().addOnSuccessListener {
            if (it != null) {
                val res = it.toObject<UserModel>()
                view.showUserDataResult(res!!)
            }
        }
            .addOnFailureListener { exception ->
                logD("failed with $exception")
            }
    }
}

interface AuthView {
    fun showUserDataResult(user: UserModel)
}
