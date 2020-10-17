package id.infiniteuny.dokuin.ui.otp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.VerifyResponse
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.data.repository.UserRepository
import id.infiniteuny.dokuin.data.service.BigBoxService
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class VerifyOtpPresenter(private val userRepository: UserRepository
                         ,private val uploadRepository: UploadRepository
                         ,private val view:VerifyOtpView):BasePresenter(){

    fun verifOtpSignUp(email:String,otp:String,password:String,noHp:String){
        launch {
            try {
                val result=withContext(Dispatchers.IO){BigBoxService.createService()
                    .verifyEmailOTP(email,4,10,otp)}
                if(result.status==200){
                    view.verifSignUp()
                }
                logE(result.message)
            }
            catch (t:Throwable){
                logE(t.localizedMessage)
            }
        }
    }
    fun verifOtpDoc(email:String,otp:String,password:String,noHp:String){
        launch {
            try {
                val result=withContext(Dispatchers.IO){BigBoxService.createService()
                    .verifyEmailOTP(email,4,10,otp)}
                if(result.status==200){
                    sendNotif()
                }
                logE(result.message)
            }
            catch (t:Throwable){
                logE(t.localizedMessage)
            }
        }
    }
    private fun sendNotif(){
        launch {
            try {
                val result=withContext(Dispatchers.IO){
                    BigBoxService.createService()
                        .sendNotification("082310487958", "Document anda sudah terverifikasi")
                }
                if(result.status=="SUCCESS"){
                    view.verifDoc()
                }
                logE(result.message)
            }
            catch (t:Throwable){
                logE(t.localizedMessage)
            }
        }
    }
    fun generateSignature(filename: String,email:String) {
        view.onLoading(true)
        launch {
            try {
                val resVerif = withContext(Dispatchers.IO) {
                    uploadRepository.generateSignature(filename,email) }
                if (resVerif.status!!) {
                    logE("ress ${resVerif.message.toString()}")
                    view.showResult(resVerif)
                }
            } catch (t: Throwable) {
                //view.onError(t.localizedMessage)
                logE(t.localizedMessage)
            }
        }
    }
    private fun updateFireStore(data: VerifyResponse, id: String){
        val update= hashMapOf(
            "status" to "approved"
        )
        FirebaseFirestore.getInstance()
            .collection("documents").document(id)
            .update("status","approved")
            .addOnSuccessListener {
                view.onLoading(false)
                view.showResult(data)
            }
            .addOnFailureListener {
                view.onError(it.localizedMessage)
            }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        cleanUp()
    }

}

interface VerifyOtpView{
    fun onLoading(state:Boolean)
    fun onError(msg:String)
    fun verifSignUp()
    fun verifDoc()
    fun showResult(data: VerifyResponse)
}