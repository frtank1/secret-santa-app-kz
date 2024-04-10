package kz.secret_santa_jusan.core

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import kz.secret_santa_jusan.core.network.httpClientModule
import kz.secret_santa_jusan.core.network.ktorConfigModel
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.di.dataAuthApiKtorModule
import kz.secret_santa_jusan.di.dataAuthApiRepoModule
import kz.secret_santa_jusan.di.dataExampleApiKtorModule
import kz.secret_santa_jusan.di.dataExampleApiRepoModule
import kz.secret_santa_jusan.di.dataFormApiKtorModule
import kz.secret_santa_jusan.di.dataFormApiRepoModule
import kz.secret_santa_jusan.di.dataGameApiKtorModule
import kz.secret_santa_jusan.di.dataGameApiRepoModule
import kz.secret_santa_jusan.di.dataInvateApiKtorModule
import kz.secret_santa_jusan.di.dataInvateApiRepoModule
import kz.secret_santa_jusan.di.dataPassRecoceryApiKtorModule
import kz.secret_santa_jusan.di.dataPassRecoceryApiRepoModule
import kz.secret_santa_jusan.di.dataProfileApiRepoModule
import kz.secret_santa_jusan.di.dataProfileyApiKtorModule
import kz.secret_santa_jusan.di.dataRegisterApiKtorModule
import kz.secret_santa_jusan.di.dataRegisterApiRepoModule
import kz.secret_santa_jusan.di.featureAuthViewModel
import kz.secret_santa_jusan.di.featureExampleViewModel
import kz.secret_santa_jusan.di.featureGameViewModel
import kz.secret_santa_jusan.di.featureInvateViewModel
import kz.secret_santa_jusan.di.featureMainViewModel
import kz.secret_santa_jusan.di.featurePassRecoceryViewModel
import kz.secret_santa_jusan.di.featureProfileViewModel
import kz.secret_santa_jusan.di.featureRegisterViewModel
import kz.secret_santa_jusan.di.featureWhishListViewModel
import kz.secret_santa_jusan.presentation.auth.AuthRouter
import kz.secret_santa_jusan.presentation.auth.AuthScreen
import kz.secret_santa_jusan.presentation.auth.pass_recovery.PassRecoveryRouter
import kz.secret_santa_jusan.presentation.auth.pass_recovery.PassRecoveryScreen
import kz.secret_santa_jusan.presentation.example.ExampleRouter
import kz.secret_santa_jusan.presentation.example.ExampleScreen
import kz.secret_santa_jusan.presentation.game.GameRouter
import kz.secret_santa_jusan.presentation.game.GameScreen
import kz.secret_santa_jusan.presentation.game.create.CreateScreen
import kz.secret_santa_jusan.presentation.invate.InvateRouter
import kz.secret_santa_jusan.presentation.invate.InvateScreen
import kz.secret_santa_jusan.presentation.invate.adding.link.LinkRouter
import kz.secret_santa_jusan.presentation.invate.adding.link.LinkScreen
import kz.secret_santa_jusan.presentation.invate.adding.manual_addition.ManualAdditionRouter
import kz.secret_santa_jusan.presentation.invate.adding.manual_addition.ManualAdditionScreen
import kz.secret_santa_jusan.presentation.main.MainRouter
import kz.secret_santa_jusan.presentation.main.MainScreen
import kz.secret_santa_jusan.presentation.form.my_wishlist.MyWishlistRouter
import kz.secret_santa_jusan.presentation.form.my_wishlist.MyWishlistScreen
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
            featureGame()
            featureInvate()
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                httpClientModule,
                ktorConfigModel,
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
                featureProfileViewModel,
                dataProfileyApiKtorModule,
                dataProfileApiRepoModule,
                featureGameViewModel,
                dataGameApiKtorModule,
                dataGameApiRepoModule,
                featureInvateViewModel,
                dataInvateApiKtorModule,
                dataInvateApiRepoModule,
                featureWhishListViewModel,
                dataFormApiKtorModule,
                dataFormApiRepoModule
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

val featureGame = screenModule {
    register<GameRouter.GameScreen> {
        GameScreen()
    }
    register<GameRouter.CreateScreen> {
        CreateScreen()
    }
}

val featureInvate = screenModule {
    register<InvateRouter.InvateScreen> {
        InvateScreen(it.link,it.gameModel)
    }

    register<LinkRouter.LinkScreen> {
        LinkScreen(it.id)
    }

    register<ManualAdditionRouter.ManualAdditionScreen> {
        ManualAdditionScreen(it.id)
    }
}

val featureWhishList = screenModule {
    register<MyWishlistRouter.MyWishlistScreen> {
        MyWishlistScreen(it.id)
    }
}