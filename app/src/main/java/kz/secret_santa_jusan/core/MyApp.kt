package kz.secret_santa_jusan.core

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import io.paperdb.BuildConfig
import kz.kizirov.template.ExampleRouter
import kz.kizirov.template.ExampleScreen
import kz.secret_santa_jusan.core.storage.GlobalStorage
import kz.secret_santa_jusan.data.example.dataExampleApiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApp: CoreApp() {

    override fun onCreate() {
        super.onCreate()

       /* GlobalStorage.setBaseUrl(BuildConfig.BASE_URL)
        GlobalStorage.setFlavor(BuildConfig.FLAVOR)
        GlobalStorage.applicationId = BuildConfig.APPLICATION_ID*/

        ScreenRegistry{
            featureExample()
        }

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                dataExampleApiModule
            )
        }
    }
}

val featureExample = screenModule {
    register<ExampleRouter.AnyScreen> {
        ExampleScreen()
    }
}