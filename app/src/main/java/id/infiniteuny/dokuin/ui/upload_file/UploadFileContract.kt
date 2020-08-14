package id.infiniteuny.dokuin.ui.upload_file

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UploadFilePresenter(private val repository: UploadRepository,private val view: UploadFileView): BasePresenter() {

    fun doUploadFile(uid:String, filename:String,file: File){
        val fileReqBody = RequestBody.create(MediaType.parse("application/pdf"),file)
        val formData= MultipartBody.Part.createFormData("lampiran", filename, fileReqBody)
        launch {
            try {
                val result = repository.uploadFile(uid, filename, formData)
                view.showResult()
            }catch (throwable:Throwable){
                logE(throwable.localizedMessage)
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
    fun onError(state:Boolean)
    fun showResult()
}