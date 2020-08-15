package id.infiniteuny.dokuin.ui.school.beranda

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
import id.infiniteuny.dokuin.ui.login.LoginActivity
import id.infiniteuny.dokuin.ui.main.beranda.BerandaPresenter
import id.infiniteuny.dokuin.ui.main.beranda.LatestApprovedVH
import id.infiniteuny.dokuin.util.logE
import kotlinx.android.synthetic.main.fragment_school_beranda.*

class SchoolBerandaFragment : BaseFragment(R.layout.fragment_school_beranda),SchoolBerandaView {

    companion object {
        fun getInstance(): SchoolBerandaFragment = SchoolBerandaFragment()
    }

    private val presenter = SchoolBerandaPresenter(this)
    private val latestDocumentApproved = mutableListOf<DocumentModel>()

    private val adapterLatestApproved = object : RvAdapter<DocumentModel>(latestDocumentApproved,
        {

        }) {
        override fun layoutId(position: Int, obj: DocumentModel): Int = R.layout.item_document

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            LatestDocumentVH(view)

    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        user_profile_school.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
        tv_user_name.text =
            requireContext().getString(R.string.greeting, SharedPref(requireContext()).userName)
        rv_latest.apply {
            adapter=adapterLatestApproved
            val layMan=LinearLayoutManager(this@SchoolBerandaFragment.context!!)
            layMan.orientation=LinearLayoutManager.HORIZONTAL
            layoutManager=layMan
        }
        presenter.getLatest(FirebaseAuth.getInstance().currentUser!!.uid)
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
        latestDocumentApproved.clear()
        latestDocumentApproved.addAll(data)
        adapterLatestApproved.notifyDataSetChanged()
    }
}