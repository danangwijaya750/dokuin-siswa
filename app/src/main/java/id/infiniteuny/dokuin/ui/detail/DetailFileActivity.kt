package id.infiniteuny.dokuin.ui.detail

import android.os.Bundle
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.model.DocumentModel

class DetailFileActivity : BaseActivity((R.layout.activity_detail_file)) {
    override fun viewCreated(savedInstanceState: Bundle?) {
        val data=intent.extras?.get("data") as DocumentModel
        showData(data)
    }

    private fun showData(data:DocumentModel){

    }
}