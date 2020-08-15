package id.infiniteuny.dokuin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.ui.instansi.InstansiMainActivity
import id.infiniteuny.dokuin.ui.login.LoginActivity
import id.infiniteuny.dokuin.ui.main.MainActivity
import id.infiniteuny.dokuin.ui.school.SchoolMainActivity

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    private var handler: Handler? = null
    override fun viewCreated(savedInstanceState: Bundle?) {
        handler = Handler()
        doSplash()
    }

    private fun doSplash() {
        handler?.postDelayed({
            if(FirebaseAuth.getInstance().currentUser!=null) {
                val intent = when (SharedPref(this).userRole) {
                    "student" -> MainActivity::class.java
                    "instansi" -> InstansiMainActivity::class.java
                    else -> SchoolMainActivity::class.java
                }
                startActivity(Intent(this, intent))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1500)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler = null
    }

}