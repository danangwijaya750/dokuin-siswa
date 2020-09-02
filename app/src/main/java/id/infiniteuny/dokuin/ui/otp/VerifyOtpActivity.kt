package id.infiniteuny.dokuin.ui.otp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
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
            }
        }
    }


}