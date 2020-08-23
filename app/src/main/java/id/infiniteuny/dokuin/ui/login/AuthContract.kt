package id.infiniteuny.dokuin.ui.login

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.LoginModel
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.data.repository.UserRepository
import id.infiniteuny.dokuin.util.logD
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthPresenter(private val repository: UserRepository,private val view: AuthView) : BasePresenter() {
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

    fun loginUser(email:String,pass:String){
        launch {
            try{
                val result= withContext(Dispatchers.IO){repository.loginUser(email,pass)}
                if(result.success){
                    //success
                    view.showLoginResult(result)
                }else{
                    view.onError(result.message)
                }
            }
            catch (t:Throwable){
                view.onError(t.localizedMessage)
                logE(t.localizedMessage)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        cleanUp()
    }
}

interface AuthView {
    fun showUserDataResult(user: UserModel)
    fun showLoginResult(data:LoginModel)
    fun onError(msg:String)
    fun onLoading(state:Boolean)
}
