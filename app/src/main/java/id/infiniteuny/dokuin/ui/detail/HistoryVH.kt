package id.infiniteuny.dokuin.ui.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.HistoryModel
import id.infiniteuny.dokuin.data.model.StudentModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_history.*

class HistoryVH(override val containerView: View): RecyclerView.ViewHolder(containerView)
    , LayoutContainer, RvAdapter.Binder<HistoryModel>{
    override fun bindData(data: HistoryModel, listen: (HistoryModel) -> Unit, position: Int) {
        tv_time.text=data.time
        tv_name.text=data.user
        tv_activity.text=data.action
    }
}