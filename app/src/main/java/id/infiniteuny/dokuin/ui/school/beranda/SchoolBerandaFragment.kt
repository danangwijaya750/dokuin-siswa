package id.infiniteuny.dokuin.ui.school.beranda

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.data.local.SharedPref
import id.infiniteuny.dokuin.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_school_beranda.*

class SchoolBerandaFragment : BaseFragment(R.layout.fragment_school_beranda) {

    companion object {
        fun getInstance(): SchoolBerandaFragment = SchoolBerandaFragment()
    }

    override fun viewCreated(savedInstanceState: Bundle?) {
        user_profile_school.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
        tv_user_name.text =
            requireContext().getString(R.string.greeting, SharedPref(requireContext()).userName)
    }
}