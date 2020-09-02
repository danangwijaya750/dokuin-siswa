package id.infiniteuny.dokuin.ui.otp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import id.infiniteuny.dokuin.base.BasePresenter
import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.data.repository.UserRepository
import id.infiniteuny.dokuin.data.service.BigBoxService
import id.infiniteuny.dokuin.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class VerifyOtpPresenter(private val userRepository: UserRepository
                         ,uploadRepository: UploadRepository
                         ,private val view:VerifyOtpView):BasePresenter(){

    fun verifOtpSignUp(email:String,otp:String,password:String,noHp:String){
        launch {
            try {
                val result=withContext(Dispatchers.IO){BigBoxService.createService()
                    .verifyEmailOTP(email,4,10,otp)}
                if(result.status==200){
                    logE("msg")
                }
                logE(result.message)
            }
            catch (t:Throwable){
                logE(t.localizedMessage)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        cleanUp()
    }

}

interface VerifyOtpView{

}