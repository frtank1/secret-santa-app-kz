package kz.secret_santa_jusan.core

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
import kz.secret_santa_jusan.di.dataRecepientApiKtorModule
import kz.secret_santa_jusan.di.dataRecepientApiRepoModule
import kz.secret_santa_jusan.di.dataRegisterApiKtorModule
import kz.secret_santa_jusan.di.dataRegisterApiRepoModule
import kz.secret_santa_jusan.di.featureAuthViewModel
import kz.secret_santa_jusan.di.featureExampleViewModel
import kz.secret_santa_jusan.di.featureFormViewModel
import kz.secret_santa_jusan.di.featureGameViewModel
import kz.secret_santa_jusan.di.featureInvateViewModel
import kz.secret_santa_jusan.di.featureMainViewModel
import kz.secret_santa_jusan.di.featurePassRecoceryViewModel
import kz.secret_santa_jusan.di.featureProfileViewModel
import kz.secret_santa_jusan.di.featureRecepientViewModel
import kz.secret_santa_jusan.di.featureRegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApp : CoreApp() {

    override fun onCreate() {
        super.onCreate()

        GlobalStorage.setBaseUrl("http://51.107.14.25:8080/")


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
                featureFormViewModel,
                dataFormApiKtorModule,
                dataFormApiRepoModule,
                featureRecepientViewModel,
                dataRecepientApiKtorModule,
                dataRecepientApiRepoModule,

            )
        }
    }
}
