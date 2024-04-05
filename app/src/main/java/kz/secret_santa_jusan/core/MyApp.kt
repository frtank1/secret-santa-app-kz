package kz.secret_santa_jusan.core

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import kz.secret_santa_jusan.di.dataExampleApiKtorModule
import kz.secret_santa_jusan.di.dataExampleApiRepoModule
import kz.secret_santa_jusan.di.featureTemplateModule
import kz.secret_santa_jusan.presentation.example.ExampleRouter
import kz.secret_santa_jusan.presentation.example.ExampleScreen
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
                featureTemplateModule
            )
        }
    }
}

val featureExample = screenModule {
    register<ExampleRouter.AnyScreen> {
        ExampleScreen()
    }
}