package id.infiniteuny.dokuin.ui.files

import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.Data
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AllFilesPresenter(private val repository: UploadRepository,private val view:AllFilesView): BasePresenter(){
    fun getMyFiles(email:String){
        view.onLoading(true)
        launch {
            try {
                val result = withContext(Dispatchers.IO){ repository.getMyFile(email)}
                view.showData(result.data)
                view.onLoading(false)
            }catch (throwable:Throwable){
                logE(throwable.localizedMessage)
                view.onError(throwable.localizedMessage)
                view.onLoading(false)
            }
        }
    }
    fun getMyFilesSignator(email:String){
        view.onLoading(true)
        launch {
            try {
                val result = withContext(Dispatchers.IO){ repository.getMyFileSignator(email)}
                view.showData(result.data)
                view.onLoading(false)
            }catch (throwable:Throwable){
                logE(throwable.localizedMessage)
                view.onError(throwable.localizedMessage)
                view.onLoading(false)
            }
        }
    }
}
interface AllFilesView{
    fun onLoading(state:Boolean)
    fun onError(msg:String)
    fun showData(data:List<Data>?)
}