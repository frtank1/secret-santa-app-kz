package kz.secret_santa_jusan.core

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import kz.secret_santa_jusan.di.dataExampleApiKtorModule
import kz.secret_santa_jusan.di.dataExampleApiRepoModule
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

        /* GlobalStorage.setBaseUrl(BuildConfig.BASE_URL)
         GlobalStorage.setFlavor(BuildConfig.FLAVOR)
         GlobalStorage.applicationId = BuildConfig.APPLICATION_ID*/

        ScreenRegistry {
            featureExample()

        }

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                dataExampleApiKtorModule,
                dataExampleApiRepoModule,
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