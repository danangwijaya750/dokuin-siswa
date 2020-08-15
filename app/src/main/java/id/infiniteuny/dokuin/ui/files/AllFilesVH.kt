package id.infiniteuny.dokuin.ui.files

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.model.UserModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_document_detail.*


class AllFilesVH (override val containerView: View): RecyclerView.ViewHolder(containerView)
    , LayoutContainer, RvAdapter.Binder<DocumentModel>{

    override fun bindData(data: DocumentModel, listen: (DocumentModel) -> Unit, position: Int) {
        tv_doc_title.text=data.title
        tv_doc_date.text=data.dateUpload.toString()
        textView2.text=data.status
        when(data.status){
            "waiting"->{
                textView2.background= containerView.context.resources.getDrawable(R.drawable.rounded_status_yellow)
            }
            "approved"->{
                textView2.background= containerView.context.resources.getDrawable(R.drawable.rounded_status_yellow)
            }
            "rejected"->{
                textView2.background= containerView.context.resources.getDrawable(R.drawable.rounded_status_red)
            }
        }

        itemView.setOnClickListener { listen(data) }
    }
}