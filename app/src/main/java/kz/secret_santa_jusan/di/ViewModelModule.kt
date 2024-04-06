package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.presentation.example.ExampleViewModel
import kz.secret_santa_jusan.presentation.registration.RegistrationViewModel
import org.koin.dsl.module

val featureExample = module {
    factory { ExampleViewModel() }
}

val featureRegister = module {
    factory { RegistrationViewModel() }
}