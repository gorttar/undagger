package undagger.atm.di.components

import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.WithdrawalLimiter
import undagger.atm.WithdrawalLimiterImport
import undagger.atm.commands.Command
import undagger.atm.commands.DepositCommand
import undagger.atm.commands.HelloWorldCommand
import undagger.atm.commands.LoginCommand
import undagger.atm.commands.LoginCommandImport
import undagger.atm.commands.LogoutCommand
import undagger.atm.commands.UserCommandImport
import undagger.atm.commands.WithdrawCommand
import undagger.atm.commands.WithdrawCommandImport
import undagger.atm.data.Database
import undagger.atm.di.BeanHolder
import undagger.atm.di.exports.AccountExport
import undagger.atm.di.exports.DatabaseExport
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.perComponent

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
        "logout" to ::LogoutCommand,
    )

    override fun userCommandRouter(account: Database.Account): UserCommandsRouter = error("No nested logins allowed!")
    override val withdrawalLimiter: WithdrawalLimiter by perComponent(::WithdrawalLimiter)
}
