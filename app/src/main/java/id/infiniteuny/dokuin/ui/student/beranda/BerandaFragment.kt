package id.infiniteuny.dokuin.ui.student.beranda

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_beranda.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class BerandaFragment : BaseFragment(R.layout.fragment_beranda), BerandaView {

    companion object {
        fun getInstance(): BerandaFragment = BerandaFragment()
    }

    private val presenter by inject<BerandaPresenter>(){
        parametersOf(this)
    }
    private val myDocuments= mutableListOf<Data>()
    private val latestDocumentApproved = mutableListOf<Data>()

    private val adapterLatestApproved = object : RvAdapter<Data>(latestDocumentApproved,
        {
            handleClick(it)
        }) {
        override fun layoutId(position: Int, obj: Data): Int = R.layout.item_document

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            LatestApprovedVH(view)

    }
    private fun handleClick(data : Data?){
        val intent = Intent(context, DetailFileActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }
    private val waitingDocList = mutableListOf<Data>()

    private val rvAdapter = object : RvAdapter<Data>(waitingDocList,
        {
            handleClick(it)
        }) {
        override fun layoutId(position: Int, obj: Data): Int = R.layout.item_document_waiting_student

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            WaitingDocumentStudentVH(view)

    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        user_profile.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            SharedPref(requireContext()).userEmail = ""
            activity?.finish()
        }

        ll_all.setOnClickListener {
            startActivity(Intent(context, AllFilesActivity::class.java))
        }
        iv_key.setOnClickListener {
            val builder= AlertDialog.Builder(context!!)
            val key=FirebaseAuth.getInstance().currentUser!!.uid
            builder.setTitle("Key")
            builder.setMessage(key)
            builder.setPositiveButton("COPY"){dialog, which ->
                val clipboard = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip: ClipData = ClipData.newPlainText("key", key)
                clipboard.setPrimaryClip(clip)
                dialog.dismiss()
            }
            builder.setCancelable(false)
            val alert=builder.create()
            alert.show()
        }

        tv_user_name.text =
            requireContext().getString(R.string.greeting, SharedPref(requireContext()).userName)
        rv_latest_approved.apply {
            adapter = adapterLatestApproved
            val layManager = LinearLayoutManager(this@BerandaFragment.context!!)
            layManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = layManager
        }
        rv_latest_waiting.apply {
            adapter = rvAdapter
            val layManager = LinearLayoutManager(this@BerandaFragment.context!!)
            layManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = layManager
        }

        //presenter.getLatestApproved(FirebaseAuth.getInstance().currentUser!!.uid)
        //presenter.getWaiting(FirebaseAuth.getInstance().currentUser!!.uid)
        presenter.getMyDocument(SharedPref(context!!).userEmail)
    }

    override fun onLoading(state: Boolean) {
        when (state) {
            true -> {
                pg_loading.visibility = View.VISIBLE
            }
            false -> {
                pg_loading.visibility = View.GONE
            }
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
            myDocuments.clear()
            myDocuments.addAll(data)
            mapper()
        }
    }
    private fun mapper(){
        latestDocumentApproved.clear()
        waitingDocList.clear()
        myDocuments.sortByDescending { it.timestamp?.toLong() }
        latestDocumentApproved.addAll(myDocuments.filter { it.valid=="2" })
        waitingDocList.addAll(myDocuments.filter { it.valid=="1" })
        rvAdapter.notifyDataSetChanged()
        adapterLatestApproved.notifyDataSetChanged()
    }

}