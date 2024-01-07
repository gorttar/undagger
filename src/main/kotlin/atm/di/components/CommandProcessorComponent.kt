package atm.di.components

import atm.CommandProcessor
import atm.di.modules.AmountsModule
import atm.di.modules.HelloWorldModule
import atm.di.modules.LoginCommandModule
import atm.di.modules.SystemOutModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HelloWorldModule::class,
        SystemOutModule::class,
        LoginCommandModule::class,
        UserCommandsComponent.InstallationModule::class,
        AmountsModule::class
    ]
)
internal interface CommandProcessorComponent {
    fun commandProcessor(): CommandProcessor
}
