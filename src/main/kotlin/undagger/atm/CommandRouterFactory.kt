package undagger.atm

import undagger.atm.commands.Command
import undagger.atm.commands.HelloWorldCommandDependency
import undagger.atm.commands.LoginCommandDependency
import undagger.atm.modules.HelloWorldModule
import undagger.atm.modules.LoginCommandModule
import undagger.atm.modules.OutputterRequirement
import undagger.atm.modules.SystemOutModule
import undagger.di.new

object CommandRouterFactory :
    OutputterRequirement by SystemOutModule,
    HelloWorldCommandDependency,
    LoginCommandDependency,
    CommandRouterDependency {
    val router: CommandRouter get() = new(::CommandRouter)
    override val commands: Map<String, Command>
        get() = mapOf(
            "hello" to new(::HelloWorldModule).command,
            "login" to new(::LoginCommandModule).command
        )
}