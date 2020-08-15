package id.infiniteuny.dokuin.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_detail_file, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.download_file-> {
                Log.d("API123", "downloaded")
                return true
            }
            R.id.share_file -> {
                Log.d("API123", "share")
                return true
            }
            R.id.close -> {
                Log.d("API123", "close")
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
}



