package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.data.example.ExampleApiKtor
import kz.secret_santa_jusan.data.example.ExampleApiRepository
import kz.secret_santa_jusan.data.registration.RegisterApiRepository
import org.koin.dsl.module

val dataExampleApiKtorModule = module {
    single { ExampleApiRepository(get()) }
}

val dataRegisterApiKtorModule = module {
    single { RegisterApiRepository(get()) }
}