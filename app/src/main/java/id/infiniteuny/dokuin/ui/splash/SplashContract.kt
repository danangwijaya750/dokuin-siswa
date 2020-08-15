package id.infiniteuny.dokuin.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.model.RSAKeyModel
import id.infiniteuny.dokuin.data.repository.UserRepository
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashPresenter(private val repository: UserRepository,private val view: SplashView):BasePresenter(){
    fun getKey(){
        launch {
            try {
                val result= withContext(Dispatchers.IO){repository.getKey()}
                view.showResult(result.data)
            }catch (ex:Exception){
                logE(ex.toString())
                view.onError(ex.toString())
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy(){
        cleanUp()
    }
}
interface SplashView{
    fun onError(msg:String)
    fun showResult(result:RSAKeyModel.Data?)
}