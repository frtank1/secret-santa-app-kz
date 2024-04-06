package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.data.example.ExampleApiKtor
import kz.secret_santa_jusan.data.example.ExampleApiRepository
import kz.secret_santa_jusan.data.registration.RegisterApiKtor
import org.koin.dsl.module

val dataExampleApiRepoModule = module {
    single { ExampleApiKtor(get()) }
}

val dataRegisterApiRepoModule = module {
    single { RegisterApiKtor(get()) }
}