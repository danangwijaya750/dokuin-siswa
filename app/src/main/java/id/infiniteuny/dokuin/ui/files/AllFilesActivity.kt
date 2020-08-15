package id.infiniteuny.dokuin.ui.files

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.ui.detail.DetailFileActivity
import id.infiniteuny.dokuin.util.logE
import id.infiniteuny.dokuin.util.toast
import kotlinx.android.synthetic.main.activity_all_files.*

class AllFilesActivity : BaseActivity(R.layout.activity_all_files) {
    private val db=FirebaseFirestore.getInstance()

    private val documentList= mutableListOf<DocumentModel>()
    private val rvAdapter=object:RvAdapter<DocumentModel>(documentList,{
     handleClick(it)
    }){
        override fun layoutId(position: Int, obj: DocumentModel): Int = R.layout.item_document_detail

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =AllFilesVH(view)

    }

    override fun viewCreated(savedInstanceState: Bundle?) {

        rv_all_files.apply {
            adapter=rvAdapter
            val layMan=LinearLayoutManager(this@AllFilesActivity)
            layMan.orientation=LinearLayoutManager.VERTICAL
            layoutManager=layMan
        }
        getPopulateData()
    }

    private fun getPopulateData(){
        when(SharedPref(this).userRole){
            "student"->{
               getStudentFiles()
            }
            "school"->{
                getSchoolFiles()
            }
            "instansi"->{}
        }
    }

    private fun handleClick(data : DocumentModel){
        val intent= Intent(this,DetailFileActivity::class.java)
        intent.putExtra("data",data)
        startActivity(intent)
    }

    private fun getStudentFiles(){
        documentList.clear()
        db.collection("documents")
            .whereEqualTo("studentId",FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    it.forEach {snap->
                        documentList.add(snap.toObject(DocumentModel::class.java).withId(snap.id))
                    }
                    rvAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
                toast(it.localizedMessage)
            }
    }
    private fun getSchoolFiles(){
        documentList.clear()
        db.collection("documents")
            .whereEqualTo("schoolId",FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    it.forEach {snap->
                        documentList.add(snap.toObject(DocumentModel::class.java).withId(snap.id))
                    }
                    rvAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
                toast(it.localizedMessage)
            }
    }


}