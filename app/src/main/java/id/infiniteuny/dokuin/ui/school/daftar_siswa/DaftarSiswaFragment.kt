package id.infiniteuny.dokuin.ui.school.daftar_siswa

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.model.StudentModel
import id.infiniteuny.dokuin.ui.detail_siswa.DetailSiswaActivity
import id.infiniteuny.dokuin.ui.files.AllFilesActivity
import id.infiniteuny.dokuin.ui.files.AllFilesVH
import id.infiniteuny.dokuin.ui.login.LoginActivity
import id.infiniteuny.dokuin.ui.main.beranda.BerandaPresenter
import id.infiniteuny.dokuin.ui.main.beranda.LatestApprovedVH
import kotlinx.android.synthetic.main.fragment_daftar_siswa.*


class DaftarSiswaFragment : BaseFragment(R.layout.fragment_daftar_siswa){

    companion object {
        fun getInstance(): DaftarSiswaFragment = DaftarSiswaFragment()
    }

    private val listStudent = mutableListOf<StudentModel>()

    private val adapterLatestApproved = object : RvAdapter<StudentModel>(listStudent,
        {
            handleClick(it)
        }) {
        override fun layoutId(position: Int, obj: StudentModel): Int = R.layout.item_document

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            StudentVH(view)

    }

    private fun handleClick(data:StudentModel){
        val intent=Intent(context!!,DetailSiswaActivity::class.java)
        intent.putExtra("data",data)
        startActivity(intent)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        rv_latest.apply {
            adapter=adapterLatestApproved
            val layMan=LinearLayoutManager(this@DaftarSiswaFragment.context!!)
            layMan.orientation=LinearLayoutManager.VERTICAL
            layoutManager=layMan
        }
        populateData()
    }
    private fun populateData(){
        listStudent.add(StudentModel("kgRK8fcJ8pWRNRiIjXBp4KIC3cs2","","","",""))
        listStudent.add(StudentModel("kgRK8fcJ8pWRNRiIjXBp4KIC3cs2","","","",""))
        listStudent.add(StudentModel("kgRK8fcJ8pWRNRiIjXBp4KIC3cs2","","","",""))
        listStudent.add(StudentModel("kgRK8fcJ8pWRNRiIjXBp4KIC3cs2","","","",""))
    }


}