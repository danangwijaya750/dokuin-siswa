package id.infiniteuny.dokuin.ui.main.beranda

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.DocumentModel

class BerandaPresenter(private val view: BerandaView) : BasePresenter() {
    private val db =FirebaseFirestore.getInstance()

    fun getLatestApproved(uid:String){
        view.onLoading(true)
        db.collection("documents")
            .whereEqualTo("uid",uid)
            .whereEqualTo("status","approved")
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
}

interface BerandaView {
    fun onLoading(state:Boolean)
    fun onError(msg:String)
    fun showResult(data:List<DocumentModel>)
}

