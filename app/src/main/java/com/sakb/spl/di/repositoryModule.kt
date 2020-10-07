package com.sakb.spl.di

import com.sakb.spl.data.remote.SplApiEndpoints
import com.sakb.spl.data.repository.SplRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRepository(get()) }
}

fun provideRepository(remote: SplApiEndpoints): SplRepository {
    return SplRepository(remote)
}