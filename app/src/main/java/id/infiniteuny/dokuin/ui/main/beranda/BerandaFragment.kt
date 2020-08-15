package id.infiniteuny.dokuin.ui.main.beranda

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.base.BaseFragmentView
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.DocumentModel

class BerandaFragment: BaseFragment(R.layout.fragment_beranda) {

    companion object{
        fun getInstance():BerandaFragment = BerandaFragment()
    }

    private val latestDocumentApproved= mutableListOf<DocumentModel>()

    private val adapterLatestApproved=object:RvAdapter<DocumentModel>(latestDocumentApproved,
        {

        }){
        override fun layoutId(position: Int, obj: DocumentModel): Int = R.layout.item_document

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = LatestApprovedVH(view)

    }

    override fun viewCreated(savedInstanceState: Bundle?) {

    }
}