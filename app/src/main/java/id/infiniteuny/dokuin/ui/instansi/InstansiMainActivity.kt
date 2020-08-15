package id.infiniteuny.dokuin.ui.instansi

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseActivity
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.ui.login.AuthPresenter
import id.infiniteuny.dokuin.ui.login.AuthView
import kotlinx.android.synthetic.main.activity_instansi_main.*

class InstansiMainActivity : BaseActivity(R.layout.activity_instansi_main), AuthView {
    private val authPresenter = AuthPresenter(this)
    override fun viewCreated(savedInstanceState: Bundle?) {
        authPresenter.getUserProfileData(FirebaseAuth.getInstance().currentUser?.uid!!)
    }

    override fun showUserDataResult(user: UserModel) {
        tv_user_name.text = this.getString(R.string.greeting, user.name)
    }

}