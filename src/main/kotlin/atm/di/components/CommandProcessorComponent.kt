package atm.di.components

import atm.CommandProcessor
import atm.di.CommonCommandsModule
import atm.di.SystemOutModule
import atm.di.UserCommandsComponentModule
import dagger.Component
import javax.inject.Singleton

/**
 * Handwritten API for interfacing with Dagger. The command-line ATM needs a single class to
 * execute: [CommandProcessor].
 *
 * <p>The list of [Component.modules] declares where Dagger should look, besides
 * [javax.inject.Inject]-annotated constructors, to help instantiate [CommandProcessor] and its
 * dependencies.
 */
@Singleton
@Component(
    modules = [
        CommonCommandsModule::class,
        UserCommandsComponentModule::class,
        SystemOutModule::class
    ]
)
interface CommandProcessorComponent {
    val processor: CommandProcessor
}
