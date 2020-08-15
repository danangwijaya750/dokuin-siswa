package id.infiniteuny.dokuin.ui.login

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.ui.instansi.InstansiMainActivity
import id.infiniteuny.dokuin.ui.main.MainActivity
import id.infiniteuny.dokuin.ui.school.SchoolMainActivity
import id.infiniteuny.dokuin.util.toast
import id.infiniteuny.dokuin.util.toastBottom
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(R.layout.activity_login), AuthView {
    private val presenter = AuthPresenter(this)

    override fun viewCreated(savedInstanceState: Bundle?) {

        val fAuth = FirebaseAuth.getInstance()

        btn_sign_in.setOnClickListener {
            val email = et_sign_in_email.text.toString()
            val pass = et_sign_in_password.text.toString()
            fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    getUserRole(it.result?.user?.uid)
                } else {
                    toast("Incorrect email or password!")
                }
            }
        }

        btn_goto_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun getUserRole(uid: String?) {
        presenter.getUserProfileData(uid!!)
    }

    override fun showUserDataResult(user: UserModel) {
        val pref = SharedPref(this)
        pref.userEmail=user.email!!
        pref.userName=user.name!!
        pref.userRole=user.role!!
        val intent: Class<out BaseActivity>
        user.role.let { role ->
            toastBottom(role.toString())
            SharedPref(this).userRole = role!!
            intent = when (role) {
                "student" -> MainActivity::class.java
                "instansi" -> InstansiMainActivity::class.java
                else -> SchoolMainActivity::class.java
            }
        }
        startActivity(Intent(this, intent))
        finish()
    }
}