package id.infiniteuny.dokuin.ui.school.daftar_siswa

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.model.StudentModel
import id.infiniteuny.dokuin.data.model.UserModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_document.*

class StudentVH (override val containerView: View): RecyclerView.ViewHolder(containerView)
    , LayoutContainer, RvAdapter.Binder<StudentModel>{

    override fun bindData(data: StudentModel, listen: (StudentModel) -> Unit, position: Int) {

        itemView.setOnClickListener { listen(data) }
    }
}