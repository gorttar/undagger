package undagger.atm.di.components

import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.commands.Command
import undagger.atm.commands.HelloWorldCommandImport
import undagger.atm.commands.LoginCommandImport
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.modules.HelloWorldModule
import undagger.atm.di.modules.LoginCommandModule
import undagger.atm.di.modules.SystemOutModule
import undagger.atm.di.utils.new
import undagger.atm.di.utils.perRequest

object CommandRouterFactory :
    OutputterExport by SystemOutModule,
    HelloWorldCommandImport,
    LoginCommandImport,
    CommandRouterImport {

    val router: CommandRouter by perRequest(::CommandRouter)
    override val commands: Map<String, Command> by perRequest {
        mapOf(
            "hello" to new(::HelloWorldModule).command,
            "login" to new(::LoginCommandModule).command
        )
    }
}