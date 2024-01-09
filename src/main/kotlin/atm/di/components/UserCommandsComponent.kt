package atm.di.components

import atm.CommandRouter
import atm.di.AccountModule
import atm.di.AmountsModule
import atm.di.PerSession
import atm.di.UserCommandsModule
import atm.di.Username
import dagger.BindsInstance
import dagger.Subcomponent

@PerSession
@Subcomponent(modules = [AccountModule::class, AmountsModule::class, UserCommandsModule::class])
interface UserCommandsComponent {
    val router: CommandRouter

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance
            @Username
            username: String
        ): UserCommandsComponent
    }
}
