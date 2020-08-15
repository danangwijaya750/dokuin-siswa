package id.infiniteuny.dokuin.base

import androidx.lifecycle.LifecycleObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter :LifecycleObserver,CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    protected fun cleanUp(){
        job.cancel()
    }

}