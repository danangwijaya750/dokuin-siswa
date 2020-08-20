package id.infiniteuny.dokuin.di

import id.infiniteuny.dokuin.data.repository.UploadRepository
import id.infiniteuny.dokuin.data.repository.UserRepository
import org.koin.dsl.module.module

val repositoryModule= module{
    single {
        UploadRepository(get())
    }
    single{
        UserRepository(get())
    }
}