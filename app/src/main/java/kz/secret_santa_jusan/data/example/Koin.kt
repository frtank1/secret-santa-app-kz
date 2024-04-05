package kz.secret_santa_jusan.data.example

import org.koin.dsl.module

val dataExampleApiModule = module {
    single { ExampleApiKtor(get()) }
    single { ExampleApiRepository(get()) }
}