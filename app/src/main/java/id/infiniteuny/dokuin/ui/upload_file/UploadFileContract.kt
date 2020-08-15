package id.infiniteuny.dokuin.ui.upload_file

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.ResponseModel
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.time.LocalDateTime
import java.util.*

class UploadFilePresenter(private val repository: UploadRepository,private val view: UploadFileView): BasePresenter() {

    fun doUploadFile(uid:String, filename:String,file: File){
        view.onLoading(true)
        val fileReqBody = RequestBody.create(MediaType.parse("application/pdf"),file)
        val formData= MultipartBody.Part.createFormData("file", filename, fileReqBody)
        launch {
            try {
                val result = withContext(Dispatchers.IO){ repository.uploadFile(uid, filename, formData)}
                uploadFirestore(result,filename, uid)
            }catch (throwable:Throwable){
                logE(throwable.localizedMessage)
                view.onError(throwable.localizedMessage)
                view.onLoading(false)
            }
        }
    }
    private fun uploadFirestore(result:ResponseModel,filename: String,uid: String){
        val db=FirebaseFirestore.getInstance()
        val data = hashMapOf(
            "title" to filename,
            "uid" to uid,
            "dateUpload" to Timestamp(Date()),
            "dateApproved" to Timestamp(Date()),
            "status" to "waiting"
        )
        db.collection("documents").add(data)
            .addOnSuccessListener {
                view.onLoading(false)
                view.showResult(result)
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
                view.onError(it.localizedMessage)
                view.onLoading(false)
            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        cleanUp()
    }

}
interface UploadFileView{
    fun onLoading(state:Boolean)
    fun onError(msg:String)
    fun showResult(data:ResponseModel)
}