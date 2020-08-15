package id.infiniteuny.dokuin.ui.login

import android.content.Intent
import android.os.Bundle
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity(R.layout.activity_sign_up) {
    override fun viewCreated(savedInstanceState: Bundle?) {
        btn_goto_sign_in.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}