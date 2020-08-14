package id.infiniteuny.dokuin.ui.main.beranda

import android.os.Bundle
import id.infiniteuny.dokuin.R
import id.infiniteuny.dokuin.base.BaseFragment
import id.infiniteuny.dokuin.base.BaseFragmentView

class BerandaFragment: BaseFragment(R.layout.fragment_beranda) {

    companion object{
        fun getInstance():BerandaFragment = BerandaFragment()
    }

    override fun viewCreated(savedInstanceState: Bundle?) {

    }
}