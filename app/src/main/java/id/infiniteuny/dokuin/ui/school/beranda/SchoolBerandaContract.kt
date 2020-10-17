package id.infiniteuny.dokuin.ui.school.beranda

import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.Data
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SchoolBerandaPresenter(private val repository: UploadRepository ,private val view: SchoolBerandaView) : BasePresenter() {
    fun getMyDocument(email:String){
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

interface SchoolBerandaView {
    fun onLoading(state: Boolean)
    fun onError(msg: String)
    fun showResult(data: List<DocumentModel>)
    fun showResultWaiting(data: List<DocumentModel>)
    fun showData(data:List<Data>?)
}

