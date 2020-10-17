package id.infiniteuny.dokuin.ui.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.Data
import id.infiniteuny.dokuin.data.model.DocumentModel
import id.infiniteuny.dokuin.data.model.VerifyResponse
import id.infiniteuny.dokuin.ui.login.LoginActivity
import id.infiniteuny.dokuin.util.toast
import kotlinx.android.synthetic.main.activity_verify_otp.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class VerifyOtpActivity : BaseActivity(R.layout.activity_verify_otp),VerifyOtpView {

    private val presenter:VerifyOtpPresenter by inject{
        parametersOf(this)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {

        val caller=intent.getStringExtra("caller")

        btn_submit_otp.setOnClickListener {
            if(caller=="sign-up"){
                val email=intent.getStringExtra("email")
                //val passw=intent.getStringExtra("password")
                presenter.verifOtpSignUp(email,et_otp.text.toString(),"","")
            }else{
                val email=intent.getStringExtra("email")
                //val passw=intent.getStringExtra("password")
                //presenter.verifOtpDoc(email,et_otp.text.toString(),"","")
                val data = intent.extras?.get("data-doc") as Data
                presenter.generateSignature(data.fileName!!,SharedPref(this).userEmail)
            }
        }
    }

    override fun onLoading(state: Boolean) {

    }

    override fun onError(msg: String) {

    }

    override fun verifSignUp() {
        toast("Verfikasi Berhasil")
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    override fun verifDoc() {
        val data = intent.extras?.get("data-doc") as Data
        presenter.generateSignature(data.fileName!!,SharedPref(this).userEmail)
    }

    override fun showResult(data: VerifyResponse) {
        toast("Verifikasi Dokumen Berhasil")
        finish()
    }


}