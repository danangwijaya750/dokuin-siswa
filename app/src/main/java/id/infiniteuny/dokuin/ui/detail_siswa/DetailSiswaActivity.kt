package id.infiniteuny.dokuin.ui.detail_siswa

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.model.StudentModel
import id.infiniteuny.dokuin.ui.detail.DetailFileActivity
import id.infiniteuny.dokuin.ui.main.beranda.LatestApprovedVH
import id.infiniteuny.dokuin.ui.upload_file.UploadFileActivity
import id.infiniteuny.dokuin.util.logE
import id.infiniteuny.dokuin.util.toast
import kotlinx.android.synthetic.main.activity_detail_siswa.*

class DetailSiswaActivity : BaseActivity(R.layout.activity_detail_siswa) {

    val db=FirebaseFirestore.getInstance()
    private val studentDocument= mutableListOf<DocumentModel>()
    private val rvAdapter = object : RvAdapter<DocumentModel>(studentDocument,
        {
            handleClick(it)
        }) {
        override fun layoutId(position: Int, obj: DocumentModel): Int = R.layout.item_document_detail

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            StudentDocumentVH(view)

    }

    private fun handleClick(data : DocumentModel){
        val intent= Intent(this, DetailFileActivity::class.java)
        intent.putExtra("data",data)
        startActivity(intent)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {

        if(SharedPref(this).userRole=="instansi"){
            floating_action_button.visibility=View.GONE
        }
        rv_all_files.apply {
            adapter=rvAdapter
            val layManager=LinearLayoutManager(this@DetailSiswaActivity)
            layManager.orientation=LinearLayoutManager.VERTICAL
            layoutManager=layManager
        }
        val data = intent.extras!!.get("data") as StudentModel
        showData(data)
        floating_action_button.setOnClickListener {
            openUpload()
        }
    }

    private fun showData(data:StudentModel){
        studentDocument.clear()
        tv_student_name.text=data.name
        tv_student_addreess.text=data.address
        tv_student_nisn.text=data.nisn
        db.collection("documents")
            .whereEqualTo("studentId", data.studentId)
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    it.forEach {snap->
                        studentDocument.add(snap.toObject(DocumentModel::class.java).withId(snap.id))
                    }
                    rvAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
                toast(it.localizedMessage)
            }

    }

    private fun openUpload(){
        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),1)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1->{
                if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    startActivity(Intent(this, UploadFileActivity::class.java))
                }
            }
        }
    }
}