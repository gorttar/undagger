package atm.di.components

import atm.CommandRouter
import atm.CommandRouterImport
import atm.WithdrawalLimiter
import atm.WithdrawalLimiterImport
import atm.commands.Command
import atm.commands.DepositCommand
import atm.commands.HelloWorldCommand
import atm.commands.LoginCommand
import atm.commands.LoginCommandImport
import atm.commands.LogoutCommand
import atm.commands.UserCommandImport
import atm.commands.WithdrawCommand
import atm.commands.WithdrawCommandImport
import atm.data.Database
import atm.di.BeanHolder
import atm.di.exports.AccountExport
import atm.di.exports.DatabaseExport
import atm.di.exports.OutputterExport
import atm.di.utils.dependent
import atm.di.utils.perComponent

// can't make it inner interface of UserCommandsRouter: https://youtrack.jetbrains.com/issue/KT-17455/
interface UserCommandsComponentImport : AccountExport, DatabaseExport {
    override val account: Database.Account
}

interface UserCommandsComponent {
    val router: CommandRouter
}

fun userCommandsComponent(import: UserCommandsComponentImport): UserCommandsComponent = object :
    UserCommandsComponent,
    UserCommandsComponentImport by import,
    CommandRouterImport,
    OutputterExport,
    LoginCommandImport,
    UserCommandImport,
    WithdrawCommandImport,
    WithdrawalLimiterImport {

    override val router: CommandRouter by perComponent(::CommandRouter)
    override val withdrawalLimiter: WithdrawalLimiter by perComponent(::WithdrawalLimiter)
    override val commands: BeanHolder<String, Command> = perComponent(
        "hello" to ::HelloWorldCommand,
        "login" to ::LoginCommand,
        "deposit" to ::DepositCommand,
        "withdraw" to ::WithdrawCommand,
        "logout" to dependent(::LogoutCommand),
    )

    override fun userCommandsComponent(account: Database.Account): UserCommandsComponent =
        error("No nested logins allowed!")
}
