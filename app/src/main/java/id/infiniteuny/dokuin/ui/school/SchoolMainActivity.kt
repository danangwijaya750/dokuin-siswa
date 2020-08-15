package id.infiniteuny.dokuin.ui.school

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.base.RvAdapter
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.ui.main.ExampleVH
import id.infiniteuny.dokuin.ui.school.beranda.SchoolBerandaFragment
import id.infiniteuny.dokuin.ui.school.notification.SchoolNotificationFragment
import id.infiniteuny.dokuin.ui.upload_file.UploadFileActivity
import kotlinx.android.synthetic.main.activity_school_main.*

class SchoolMainActivity : BaseActivity(R.layout.activity_school_main) {

    private val exampleListUser = mutableListOf<UserModel>()
    private val exampleRvAdapter = object : RvAdapter<UserModel>(exampleListUser,
        {
            //handle click rv didalam sini
        }) {
        //layout item untuk rv
        override fun layoutId(position: Int, obj: UserModel): Int = R.layout.item_document

        //view holder untuk RV
        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =
            ExampleVH(view)

    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        bn_school_main.setOnNavigationItemSelectedListener(navListener)
        if (savedInstanceState == null) {
            bn_school_main.selectedItemId = R.id.menu_beranda_school
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this, UploadFileActivity::class.java))
                }
            }
        }
    }


    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.menu_beranda_school -> {
                changeFragment(SchoolBerandaFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_upload_school -> {
                openUpload()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_notif_school -> {
                changeFragment(SchoolNotificationFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    private fun changeFragment(fr: Fragment) {
        val transact = supportFragmentManager.beginTransaction()
        transact.replace(R.id.fl_container_school, fr)
        transact.addToBackStack(null)
        transact.commit()
    }

    private fun openUpload() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 1
        )
    }

}