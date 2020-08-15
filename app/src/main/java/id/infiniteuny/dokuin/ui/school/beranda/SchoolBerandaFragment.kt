package id.infiniteuny.dokuin.ui.school.beranda

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.data.model.UserModel
import id.infiniteuny.dokuin.ui.login.AuthPresenter
import id.infiniteuny.dokuin.ui.login.AuthView
import kotlinx.android.synthetic.main.fragment_school_beranda.*

class SchoolBerandaFragment : BaseFragment(R.layout.fragment_school_beranda), AuthView {
    private val authPresenter = AuthPresenter(this)

    companion object {
        fun getInstance(): SchoolBerandaFragment = SchoolBerandaFragment()
    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        authPresenter.getUserProfileData(FirebaseAuth.getInstance().currentUser?.uid!!)
    }

    override fun showUserDataResult(user: UserModel) {
        tv_user_name.text = requireContext().getString(R.string.greeting, user.name)
    }
}