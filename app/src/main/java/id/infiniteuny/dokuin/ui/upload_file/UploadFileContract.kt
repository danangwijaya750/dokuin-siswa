package id.infiniteuny.dokuin.ui.upload_file

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
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

class UploadFilePresenter(private val repository: UploadRepository,private val view: UploadFileView): BasePresenter() {

    fun doUploadFile(uid:String, filename:String,file: File){
        view.onLoading(true)
        val fileReqBody = RequestBody.create(MediaType.parse("application/pdf"),file)
        val formData= MultipartBody.Part.createFormData("file", filename, fileReqBody)
        launch {
            try {
                val result = withContext(Dispatchers.IO){ repository.uploadFile(uid, filename, formData)}
                view.showResult(result)
            }catch (throwable:Throwable){
                logE(throwable.localizedMessage)
                view.onError(throwable.localizedMessage)
            }
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