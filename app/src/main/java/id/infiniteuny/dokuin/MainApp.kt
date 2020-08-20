package id.infiniteuny.dokuin

import android.app.Application
import id.infiniteuny.dokuin.di.networkModule
import id.infiniteuny.dokuin.di.presenterModule
import id.infiniteuny.dokuin.di.repositoryModule
import org.koin.android.ext.android.startKoin

class MainApp:Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, repositoryModule, presenterModule))
    }
}