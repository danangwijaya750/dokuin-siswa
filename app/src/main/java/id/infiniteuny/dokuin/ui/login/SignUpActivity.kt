package id.infiniteuny.dokuin.ui.login

import android.content.Intent
import android.os.Bundle
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.model.LoginModel
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.data.model.VerifyEmailOTP
import id.infiniteuny.dokuin.ui.otp.VerifyOtpActivity
import id.infiniteuny.dokuin.util.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignUpActivity : BaseActivity(R.layout.activity_sign_up),AuthView {
    private val presenter by inject<AuthPresenter>{
        parametersOf(this)
    }
    override fun viewCreated(savedInstanceState: Bundle?) {
        btn_goto_sign_in.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btn_sign_up.setOnClickListener {
            presenter.signUpUser(et_sign_up_email.text.toString(),et_sign_up_password.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycle.addObserver(presenter)
    }

    override fun showUserDataResult(user: UserModel) {

    }

    override fun showLoginResult(data: LoginModel) {

    }

    override fun otpSended() {
        toast("Kode OTP Terkirim ke Email ${et_sign_up_email.text}")
        val intent= Intent(this,VerifyOtpActivity::class.java)
        intent.putExtra("caller","sign-up")
        intent.putExtra("email",et_sign_up_email.text.toString())
        startActivity(intent)
        finish()
    }

    override fun onError(msg: String) {

    }

    override fun onLoading(state: Boolean) {

    }
}