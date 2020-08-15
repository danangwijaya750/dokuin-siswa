package id.infiniteuny.dokuin.ui.detail

import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.VerifyResponse
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFilePresenter(
    private val repository: UploadRepository,
    private val view: DetailFileView
) : BasePresenter() {
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
}