package id.infiniteuny.dokuin.ui.instansi

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.model.StudentModel
import id.infiniteuny.dokuin.data.model.UserModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_document.*
import kotlinx.android.synthetic.main.item_student.*

class SearchVH (override val containerView: View): RecyclerView.ViewHolder(containerView)
    , LayoutContainer, RvAdapter.Binder<UserModel>{

    override fun bindData(data: UserModel, listen: (UserModel) -> Unit, position: Int) {
        tv_student_name.text=data.name
        itemView.setOnClickListener { listen(data) }
    }
}