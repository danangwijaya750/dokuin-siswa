package id.infiniteuny.dokuin.ui.files

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.Data
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_document_detail.*
import java.text.SimpleDateFormat
import java.util.*


class AllFilesVH (override val containerView: View): RecyclerView.ViewHolder(containerView)
    , LayoutContainer, RvAdapter.Binder<Data>{

    override fun bindData(data: Data, listen: (Data) -> Unit, position: Int) {
        tv_doc_title.text=data.fileTitle
        val date = Date(data.timestamp!!.toLong() * 1000)
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
        tv_doc_date.text=format.format(date)
        when(data.valid){
            "1"->{
                textView2.background= containerView.context.resources.getDrawable(R.drawable.rounded_status_yellow)
                textView2.text="Pending"
            }
            "2"->{
                textView2.background= containerView.context.resources.getDrawable(R.drawable.rounded_status_green)
                textView2.text="Approved"
            }
            "3"->{
                textView2.background= containerView.context.resources.getDrawable(R.drawable.rounded_status_red)
                textView2.text="Rejected"
            }
        }

        itemView.setOnClickListener { listen(data) }
    }
}