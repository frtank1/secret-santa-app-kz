package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.presentation.example.ExampleViewModel
import org.koin.dsl.module

val featureTemplateModule = module {
    factory { ExampleViewModel() }
}