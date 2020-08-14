package id.infiniteuny.dokuin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.UserModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val exampleListUser= mutableListOf<UserModel>()
    private val exampleRvAdapter=object: RvAdapter<UserModel>(exampleListUser,
        {
            //handle click rv didalam sini
        })
    {
        //layout item untuk rv
        override fun layoutId(position: Int, obj: UserModel): Int = R.layout.item_document

        //view holder untuk RV
        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder = ExampleVH(view)

    }

    override fun viewCreated(savedInstanceState: Bundle?) {

    }
}