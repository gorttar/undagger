package atm.di.components

import atm.CommandProcessor
import atm.CommandProcessorImport
import atm.CommandRouter
import atm.CommandRouterImport
import atm.commands.Command
import atm.commands.HelloWorldCommand
import atm.commands.LoginCommand
import atm.commands.LoginCommandImport
import atm.data.Database
import atm.data.Database.Account
import atm.di.BeanHolder
import atm.di.exports.DatabaseExport
import atm.di.exports.OutputterExport
import atm.di.utils.dependent
import atm.di.utils.new
import atm.di.utils.perComponent

interface CommandProcessorFactoryExport :
    OutputterExport,
    LoginCommandImport,
    CommandRouterImport,
    CommandProcessorImport

object CommandProcessorFactory : CommandProcessorFactoryExport {
    override val database: Database by perComponent(dependent(::Database))
    override val firstCommandRouter: CommandRouter by perComponent(::CommandRouter)
    override val commands: BeanHolder<String, Command> = perComponent(
        "hello" to ::HelloWorldCommand,
        "login" to ::LoginCommand,
    )
    val processor: CommandProcessor by perComponent(::CommandProcessor)
    override fun userCommandRouter(account: Account): UserCommandsRouter = object :
        CommandRouterImport by this,
        DatabaseExport by this,
        OutputterExport,
        UserCommandsRouterImport {

        override val account: Account = account
    }
        .new(::UserCommandsRouter)
}
