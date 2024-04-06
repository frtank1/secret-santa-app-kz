package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.presentation.auth.AuthViewModel
import kz.secret_santa_jusan.presentation.example.ExampleViewModel
import kz.secret_santa_jusan.presentation.main.MainViewModel
import kz.secret_santa_jusan.presentation.registration.RegistrationViewModel
import org.koin.dsl.module

val featureExampleViewModel = module {
    factory { ExampleViewModel() }
}

val featureRegisterViewModel = module {
    factory { RegistrationViewModel(get()) }
}

val featureMainViewModel = module {
    factory { MainViewModel() }
}

val featureAuthViewModel = module {
    factory { AuthViewModel(get()) }
}