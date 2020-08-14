package id.infiniteuny.dokuin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*

abstract class BaseFragment (private val layout:Int):Fragment(),BaseFragmentView{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }

}
interface BaseFragmentView{
    fun viewCreated(savedInstanceState: Bundle?)
}