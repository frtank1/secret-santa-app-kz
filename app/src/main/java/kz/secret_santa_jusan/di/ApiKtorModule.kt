package kz.secret_santa_jusan.di

import kz.secret_santa_jusan.data.auth.AuthApiKtor
import kz.secret_santa_jusan.data.example.ExampleApiKtor
import kz.secret_santa_jusan.data.game.GameApiKtor
import kz.secret_santa_jusan.data.game.create.CreateApiKtor
import kz.secret_santa_jusan.data.invate.InvateApiKtor
import kz.secret_santa_jusan.data.profile.ProfileApiKtor
import kz.secret_santa_jusan.data.recovery_pass.PassRecoveryApiKtor
import kz.secret_santa_jusan.data.registration.RegisterApiKtor
import org.koin.dsl.module

val dataExampleApiRepoModule = module {
    single { ExampleApiKtor(get()) }
}

val dataRegisterApiRepoModule = module {
    single { RegisterApiKtor(get()) }
}

val dataAuthApiRepoModule = module {
    single { AuthApiKtor(get()) }
}

val dataPassRecoceryApiRepoModule = module {
    single { PassRecoveryApiKtor(get()) }
}
val dataProfileApiRepoModule = module {
    single { ProfileApiKtor(get()) }
}

val dataGameApiRepoModule = module {
    single { GameApiKtor(get()) }
    single { CreateApiKtor(get()) }
}

val dataInvateApiRepoModule = module {
    single { InvateApiKtor(get()) }
}