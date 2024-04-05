package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.data.example.ExampleApiKtor
import kz.secret_santa_jusan.data.example.ExampleApiRepository
import org.koin.dsl.module

val dataExampleApiRepoModule = module {
    single { ExampleApiRepository(get()) }
}