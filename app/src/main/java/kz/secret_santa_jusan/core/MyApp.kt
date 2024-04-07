package kz.secret_santa_jusan.core

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import kz.secret_santa_jusan.core.network.httpClientModule
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.di.dataAuthApiKtorModule
import kz.secret_santa_jusan.di.dataAuthApiRepoModule
import kz.secret_santa_jusan.di.dataExampleApiKtorModule
import kz.secret_santa_jusan.di.dataExampleApiRepoModule
import kz.secret_santa_jusan.di.dataPassRecoceryApiKtorModule
import kz.secret_santa_jusan.di.dataPassRecoceryApiRepoModule
import kz.secret_santa_jusan.di.dataRegisterApiKtorModule
import kz.secret_santa_jusan.di.dataRegisterApiRepoModule
import kz.secret_santa_jusan.di.featureAuthViewModel
import kz.secret_santa_jusan.di.featureExampleViewModel
import kz.secret_santa_jusan.di.featureMainViewModel
import kz.secret_santa_jusan.di.featurePassRecoceryViewModel
import kz.secret_santa_jusan.di.featureProfileViewModel
import kz.secret_santa_jusan.di.featureRegisterViewModel
import kz.secret_santa_jusan.presentation.auth.AuthRouter
import kz.secret_santa_jusan.presentation.auth.AuthScreen
import kz.secret_santa_jusan.presentation.auth.pass_recovery.PassRecoveryRouter
import kz.secret_santa_jusan.presentation.auth.pass_recovery.PassRecoveryScreen
import kz.secret_santa_jusan.presentation.example.ExampleRouter
import kz.secret_santa_jusan.presentation.example.ExampleScreen
import kz.secret_santa_jusan.presentation.main.MainRouter
import kz.secret_santa_jusan.presentation.main.MainScreen
import kz.secret_santa_jusan.presentation.registration.RegistrationRouter
import kz.secret_santa_jusan.presentation.registration.RegistrationScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApp : CoreApp() {

    override fun onCreate() {
        super.onCreate()

        GlobalStorage.setBaseUrl("http://51.107.14.25:8080/")


        ScreenRegistry {
            featureExample()
            featureRegister()
            featureMain()
            featureAuth()
            featureRecoveryPass()
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
                featureExampleViewModel,
                featureRegisterViewModel,
                featureMainViewModel,
                dataAuthApiRepoModule,
                dataAuthApiKtorModule,
                featureAuthViewModel,
                featurePassRecoceryViewModel,
                dataPassRecoceryApiRepoModule,
                dataPassRecoceryApiKtorModule,
                featureProfileViewModel
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

val featureMain = screenModule {
    register<MainRouter.MainSreen> {
        MainScreen(it.isAuth)
    }
}

val featureAuth = screenModule {
    register<AuthRouter.AuthScreen> {
        AuthScreen()
    }
}

val featureRecoveryPass = screenModule {
    register<PassRecoveryRouter.PassRecoveryScreen> {
        PassRecoveryScreen()
    }
}