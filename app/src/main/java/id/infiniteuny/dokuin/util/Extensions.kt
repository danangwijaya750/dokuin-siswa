package id.infiniteuny.dokuin.util

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast

inline fun<reified T>T.logE(msg:String?) = msg.let{
    Log.e(T::class.java.simpleName,msg)
}
inline fun<reified T>T.logD(msg:String?) = msg.let{
    Log.d(T::class.java.simpleName,msg)
}
fun Context.toast(msg:String?){
    val tst = Toast.makeText(this,msg,Toast.LENGTH_SHORT)
    tst.setGravity(Gravity.CENTER,0,0)
    tst.show()
}