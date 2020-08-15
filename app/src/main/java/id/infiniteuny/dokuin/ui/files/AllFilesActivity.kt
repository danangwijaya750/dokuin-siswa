package id.infiniteuny.dokuin.ui.files

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.DocumentModel

class AllFilesActivity : BaseActivity(R.layout.activity_all_files) {
    private val db=FirebaseFirestore.getInstance()

    private val documentList= mutableListOf<DocumentModel>()
    private val rvAdapter=object:RvAdapter<DocumentModel>(documentList,{

    }){
        override fun layoutId(position: Int, obj: DocumentModel): Int = R.layout.item_document_detail

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =AllFilesVH(view)

    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        getPopulateData()
    }

    private fun getPopulateData(){
        when(SharedPref(this).userRole){
            "student"->{

            }
            "school"->{}
            "instansi"->{}
        }
    }

}