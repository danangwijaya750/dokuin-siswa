package id.infiniteuny.dokuin.di

import id.infiniteuny.dokuin.ui.detail.DetailFilePresenter
import id.infiniteuny.dokuin.ui.detail.DetailFileView
import id.infiniteuny.dokuin.ui.files.AllFilesPresenter
import id.infiniteuny.dokuin.ui.files.AllFilesView
import id.infiniteuny.dokuin.ui.login.AuthPresenter
import id.infiniteuny.dokuin.ui.login.AuthView
import id.infiniteuny.dokuin.ui.otp.VerifyOtpPresenter
import id.infiniteuny.dokuin.ui.otp.VerifyOtpView
import id.infiniteuny.dokuin.ui.school.beranda.SchoolBerandaPresenter
import id.infiniteuny.dokuin.ui.school.beranda.SchoolBerandaView
import id.infiniteuny.dokuin.ui.student.beranda.BerandaPresenter
import id.infiniteuny.dokuin.ui.student.beranda.BerandaView
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
    factory {(view: BerandaView)->
        BerandaPresenter(get(),view)
    }
    factory {(view: SchoolBerandaView)->
        SchoolBerandaPresenter(get(),view)
    }
    factory {(view: VerifyOtpView)->
        VerifyOtpPresenter(get(),get(),view)
    }
    factory {(view: AllFilesView)->
        AllFilesPresenter(get(),view)
    }
}