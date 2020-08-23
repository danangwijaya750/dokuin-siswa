package id.infiniteuny.dokuin.di

import id.infiniteuny.dokuin.ui.detail.DetailFilePresenter
import id.infiniteuny.dokuin.ui.detail.DetailFileView
import id.infiniteuny.dokuin.ui.login.AuthPresenter
import id.infiniteuny.dokuin.ui.login.AuthView
import id.infiniteuny.dokuin.ui.upload_file.UploadFilePresenter
import id.infiniteuny.dokuin.ui.upload_file.UploadFileView
import org.koin.dsl.module.module

val presenterModule = module {
    factory {(view: AuthView)->
        AuthPresenter(get(),view)
    }
    factory { (view: UploadFileView)->
        UploadFilePresenter(get(),view)
    }
    factory {(view: DetailFileView)->
        DetailFilePresenter(get(),view)
    }
}