package id.infiniteuny.dokuin.ui.detail

import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.SendEmailOTP
import id.infiniteuny.dokuin.data.model.VerifyResponse
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.data.service.BigBoxService
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFilePresenter(
    private val repository: UploadRepository,
    private val view: DetailFileView
) : BasePresenter() {

    fun sendOtp(email:String){
        launch {
            try{
                val data= SendEmailOTP(4,10,1,"Kode OTP untuk Verifikasi Dokumen : {{otp}}",email,"Kode OTP Verifikasi DOKUIN.ID" )
                val result= withContext(Dispatchers.IO){ BigBoxService.createService().sendEmailOTP(email,data)}
                if(result.status==200){
                    view.otpSended()
                }
                else{
                    view.onError(result.message!!)
                }
            }
            catch (t:Throwable){
                logE(t.localizedMessage)
            }
        }
    }

     fun doVerify(filename: String,id:String) {
        view.onLoading(true)
        launch {
            try {
                val resVerif = withContext(Dispatchers.IO) { repository.verifyFile(filename) }
                if (resVerif.status!!) {
                    logE("ress ${resVerif.message.toString()}")
                    updateFireStore(resVerif,id)
                }
            } catch (t: Throwable) {
                view.onError(t.localizedMessage)
                logE(t.localizedMessage)
            }
        }
    }
    private fun updateFireStore(data:VerifyResponse,id: String){
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
}

interface DetailFileView {
    fun onLoading(state: Boolean)
    fun onError(msg: String)
    fun showResult(data: VerifyResponse)
    fun otpSended()
}