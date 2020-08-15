package id.infiniteuny.dokuin.ui.school.beranda

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.DocumentModel

class SchoolBerandaPresenter(private val view: SchoolBerandaView) : BasePresenter() {
    private val db =FirebaseFirestore.getInstance()

    fun getLatest(uid:String){
        view.onLoading(true)
        db.collection("documents")
            .whereEqualTo("schoolId",uid)
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    val data= mutableListOf<DocumentModel>()
                    it.forEach {snap->
                        data.add(snap.toObject(DocumentModel::class.java).withId(snap.id))
                    }
                    view.showResult(data)
                }else{
                    view.showResult(listOf())
                }
                view.onLoading(false)
            }
            .addOnFailureListener {
                view.onLoading(false)
                view.onError(it.localizedMessage)
            }
    }
    fun getWaiting(uid:String){
        view.onLoading(true)
        db.collection("documents")
            .whereEqualTo("schoolId",uid)
            .whereEqualTo("status","waiting")
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    val data= mutableListOf<DocumentModel>()
                    it.forEach {snap->
                        data.add(snap.toObject(DocumentModel::class.java).withId(snap.id))
                    }
                    view.showResultWaiting(data)
                }else{
                    view.showResultWaiting(listOf())
                }
                view.onLoading(false)
            }
            .addOnFailureListener {
                view.onLoading(false)
                view.onError(it.localizedMessage)
            }
    }
}

interface SchoolBerandaView {
    fun onLoading(state:Boolean)
    fun onError(msg:String)
    fun showResult(data:List<DocumentModel>)
    fun showResultWaiting(data:List<DocumentModel>)
}

