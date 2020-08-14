package id.infiniteuny.dokuin.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private val layout:Int):AppCompatActivity(),BaseActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(layout)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
            , WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        viewCreated(savedInstanceState)
    }


}
interface BaseActivityView{
    fun viewCreated(savedInstanceState: Bundle?)
}