package id.infiniteuny.dokuin.ui.main.beranda

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.ui.login.AuthPresenter
import id.infiniteuny.dokuin.ui.login.AuthView
import id.infiniteuny.dokuin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_beranda.*

class BerandaFragment : BaseFragment(R.layout.fragment_beranda), AuthView {
    private val authPresenter = AuthPresenter(this)

    companion object {
        fun getInstance(): BerandaFragment = BerandaFragment()
    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        user_profile.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        authPresenter.getUserProfileData(FirebaseAuth.getInstance().currentUser?.uid!!)
    }

    override fun showUserDataResult(user: UserModel) {
        tv_user_name.text = requireContext().getString(R.string.greeting, user.name)
    }
}