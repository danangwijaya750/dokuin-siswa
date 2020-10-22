package id.infiniteuny.dokuin.ui.school.beranda

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.Data
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.ui.detail.DetailFileActivity
import id.infiniteuny.dokuin.ui.files.AllFilesActivity
import id.infiniteuny.dokuin.ui.login.LoginActivity
import id.infiniteuny.dokuin.util.logE
import kotlinx.android.synthetic.main.fragment_school_beranda.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SchoolBerandaFragment : BaseFragment(R.layout.fragment_school_beranda),SchoolBerandaView {

    companion object {
        fun getInstance(): SchoolBerandaFragment = SchoolBerandaFragment()
    }

    private val presenter by inject<SchoolBerandaPresenter>() {
        parametersOf(this)
    }
    private val myDoc= mutableListOf<Data>()
    private val latestDocumentApproved = mutableListOf<Data>()
    private val waitingDocument = mutableListOf<Data>()

    private val adapterLatestApproved = object : RvAdapter<Data>(latestDocumentApproved,
        {
            handleClick(it)
        }) {
        override fun layoutId(position: Int, obj: Data): Int = R.layout.item_document

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            LatestDocumentVH(view)

    }

    private val adapterWaitingDocument = object : RvAdapter<Data>(waitingDocument,
        {
            handleClick(it)
        }) {
        override fun layoutId(position: Int, obj: Data): Int = R.layout.item_document_waiting

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            WaitingDocumentVH(view)

    }

    private fun handleClick(data : Data){
        val intent= Intent(context!!, DetailFileActivity::class.java)
        intent.putExtra("data",data)
        startActivity(intent)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        user_profile_school.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            SharedPref(requireContext()).userEmail = ""
            activity?.finish()
        }

        ll_all.setOnClickListener {
            startActivity(Intent(context,AllFilesActivity::class.java))
        }

        tv_user_name.text =
            requireContext().getString(R.string.greeting, SharedPref(requireContext()).userName)
        rv_latest.apply {
            adapter=adapterLatestApproved
            val layMan=LinearLayoutManager(this@SchoolBerandaFragment.context!!)
            layMan.orientation=LinearLayoutManager.HORIZONTAL
            layoutManager=layMan
        }

        rv_latest_waiting.apply {
            adapter=adapterWaitingDocument
            val layMan=LinearLayoutManager(this@SchoolBerandaFragment.context!!)
            layMan.orientation=LinearLayoutManager.HORIZONTAL
            layoutManager=layMan
        }
        //presenter.getLatest(FirebaseAuth.getInstance().currentUser!!.uid)
        //presenter.getWaiting(FirebaseAuth.getInstance().currentUser!!.uid)
        presenter.getMyDocument(SharedPref(context!!).userEmail)
    }

    override fun onLoading(state: Boolean) {
        when(state){
            true->pg_loading.visibility=View.VISIBLE
            false->pg_loading.visibility=View.GONE
        }
    }

    override fun onError(msg: String) {
        logE(msg)
    }

    override fun showResult(data: List<DocumentModel>) {

    }

    override fun showResultWaiting(data: List<DocumentModel>) {

    }

    override fun showData(data: List<Data>?) {
        if(!data.isNullOrEmpty()){
            myDoc.clear()
            myDoc.addAll(data)
            mapper()
        }
    }
    private fun mapper(){
        latestDocumentApproved.clear()
        waitingDocument.clear()
        myDoc.sortByDescending { it.timestamp?.toLong() }
        latestDocumentApproved.addAll(myDoc.filter { it.valid=="2" })
        waitingDocument.addAll(myDoc.filter { it.valid=="1" })
        adapterLatestApproved.notifyDataSetChanged()
        adapterWaitingDocument.notifyDataSetChanged()
    }
}