package kz.secret_santa_jusan.core

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import kz.secret_santa_jusan.core.network.httpClientModule
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.di.dataExampleApiKtorModule
import kz.secret_santa_jusan.di.dataExampleApiRepoModule
import kz.secret_santa_jusan.di.dataRegisterApiKtorModule
import kz.secret_santa_jusan.di.dataRegisterApiRepoModule
import kz.secret_santa_jusan.di.featureExample
import kz.secret_santa_jusan.di.featureRegister
import kz.secret_santa_jusan.presentation.example.ExampleRouter
import kz.secret_santa_jusan.presentation.example.ExampleScreen
import kz.secret_santa_jusan.presentation.registration.RegistrationRouter
import kz.secret_santa_jusan.presentation.registration.RegistrationScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApp : CoreApp() {

    override fun onCreate() {
        super.onCreate()

         GlobalStorage.setBaseUrl("http://51.107.14.25:8080")


        ScreenRegistry {
            featureExample()
            featureRegister()
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                httpClientModule,
                dataExampleApiKtorModule,
                dataExampleApiRepoModule,
                dataRegisterApiKtorModule,
                dataRegisterApiRepoModule,
                featureExample,
                featureRegister
            )
        }
    }
}

val featureExample = screenModule {
    register<ExampleRouter.AnyScreen> {
        ExampleScreen()
    }
}

val featureRegister = screenModule {
    register<RegistrationRouter.RegistrationScreen> {
        RegistrationScreen()
    }
}