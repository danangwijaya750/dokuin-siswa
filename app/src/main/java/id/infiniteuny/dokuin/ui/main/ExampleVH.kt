package id.infiniteuny.dokuin.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.UserModel
import kotlinx.android.extensions.LayoutContainer

class ExampleVH (override val containerView: View): RecyclerView.ViewHolder(containerView)
    , LayoutContainer, RvAdapter.Binder<UserModel>{

    override fun bindData(data: UserModel, listen: (UserModel) -> Unit, position: Int) {


        //eksekusi click di activity
        itemView.setOnClickListener { listen(data) }
    }
}