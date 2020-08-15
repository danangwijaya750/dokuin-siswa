package id.infiniteuny.dokuin.ui.instansi

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_instansi_main.*

class InstansiMainActivity : BaseActivity(R.layout.activity_instansi_main) {
    override fun viewCreated(savedInstanceState: Bundle?) {

        user_profile_instansi.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        tv_user_name.text = getString(R.string.greeting, SharedPref(this).userName)
    }


}