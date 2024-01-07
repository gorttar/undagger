package undagger.atm.di.components

import undagger.atm.CommandProcessor
import undagger.atm.CommandProcessorImport
import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.commands.Command
import undagger.atm.commands.HelloWorldCommand
import undagger.atm.commands.LoginCommand
import undagger.atm.commands.LoginCommandImport
import undagger.atm.data.Database
import undagger.atm.data.Database.Account
import undagger.atm.di.BeanHolder
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.dependent
import undagger.atm.di.utils.new
import undagger.atm.di.utils.perComponent

object CommandProcessorFactory :
    OutputterExport,
    LoginCommandImport,
    CommandRouterImport,
    CommandProcessorImport {

    override val database: Database by perComponent(dependent(::Database))
    override val firstCommandRouter: CommandRouter by perComponent(::CommandRouter)
    override val commands: BeanHolder<String, Command> = perComponent(
        "hello" to ::HelloWorldCommand,
        "login" to ::LoginCommand,
    )
    val processor: CommandProcessor by perComponent(::CommandProcessor)
    override fun userCommandRouter(account: Account): UserCommandsRouter = object :
        UserCommandsRouterImport,
        OutputterExport by this,
        CommandRouterImport by this {

        override val account: Account = account
    }
        .new(::UserCommandsRouter)
}
