package atm.di.components

import atm.CommandRouter
import atm.data.Database.Account
import atm.di.modules.PerSession
import atm.di.modules.UserCommandsModule
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent

@PerSession
@Subcomponent(modules = [UserCommandsModule::class])
interface UserCommandsComponent {
    fun router(): CommandRouter

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance account: Account): UserCommandsComponent
    }

    @Module(subcomponents = [UserCommandsComponent::class])
    interface InstallationModule
}
