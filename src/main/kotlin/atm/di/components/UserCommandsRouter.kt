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
interface UserCommandsRouterImport : AccountExport, DatabaseExport {
    override val account: Database.Account
}

interface UserCommandsRouterExport :
    CommandRouterImport,
    OutputterExport,
    LoginCommandImport,
    UserCommandImport,
    WithdrawCommandImport,
    WithdrawalLimiterImport

//@PerSession
//@Subcomponent(modules = [UserCommandsModule::class])
class UserCommandsRouter(import: UserCommandsRouterImport) : //todo naming; does NOT inherit CommandRouter
    UserCommandsRouterImport by import,
    UserCommandsRouterExport {

    val router: CommandRouter by perComponent(::CommandRouter)
    override val commands: BeanHolder<String, Command> = perComponent(
        "hello" to ::HelloWorldCommand,
        "login" to ::LoginCommand,
        "deposit" to ::DepositCommand,
        "withdraw" to ::WithdrawCommand,
        "logout" to dependent(::LogoutCommand),
    )

    override fun userCommandRouter(account: Database.Account): UserCommandsRouter = error("No nested logins allowed!")
    override val withdrawalLimiter: WithdrawalLimiter by perComponent(::WithdrawalLimiter)
}
