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
import atm.di.utils.dependent
import atm.di.utils.new
import atm.di.utils.perComponent

interface CommandProcessorComponent {
    val processor: CommandProcessor
}

val commandProcessorComponent: CommandProcessorComponent = object :
    CommandProcessorComponent,
    LoginCommandImport,
    CommandRouterImport,
    CommandProcessorImport {

    override val database: Database by perComponent(dependent(::Database))
    override val firstCommandRouter: CommandRouter by perComponent(::CommandRouter)
    override val commands: BeanHolder<String, Command> = perComponent(
        "hello" to ::HelloWorldCommand,
        "login" to ::LoginCommand,
    )
    override val processor: CommandProcessor by perComponent(::CommandProcessor)
    override fun userCommandsComponent(account: Account): UserCommandsComponent = object :
        DatabaseExport by this,
        UserCommandsComponentImport {

        override val account: Account = account
    }
        .new(::userCommandsComponent)
}
