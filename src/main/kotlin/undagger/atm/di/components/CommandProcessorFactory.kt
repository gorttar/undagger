package undagger.atm.di.components

import undagger.atm.CommandProcessor
import undagger.atm.CommandProcessorImport
import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.commands.Command
import undagger.atm.commands.HelloWorldCommand
import undagger.atm.commands.HelloWorldCommandImport
import undagger.atm.commands.LoginCommand
import undagger.atm.commands.LoginCommandImport
import undagger.atm.data.Database
import undagger.atm.data.Database.Account
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.new
import undagger.atm.di.utils.perComponent
import undagger.atm.di.utils.perRequest
import undagger.atm.io.Outputter

object CommandProcessorFactory :
    OutputterExport,
    HelloWorldCommandImport,
    LoginCommandImport,
    CommandRouterImport,
    CommandProcessorImport {

    override val outputter: Outputter by perRequest { Outputter(::println) }
    override val database: Database by perComponent { Database() }
    override val firstCommandRouter: CommandRouter by perComponent(::CommandRouter)
    override val commands: Map<String, Command> by perComponent {
        mapOf(
            "hello" to new(::HelloWorldCommand),
            "login" to new(::LoginCommand),
        )
    }
    val processor: CommandProcessor by perComponent(::CommandProcessor)
    override fun userCommandRouter(account: Account) = UserCommandsRouter(
        object :
            UserCommandsRouterImport,
            OutputterExport by this,
            CommandRouterImport by this {

            override val account: Account = account
        }
    )
}
