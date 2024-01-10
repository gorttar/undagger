package atm.di.components

import atm.CommandRouter
import atm.WithdrawalLimiter
import atm.commands.Command
import atm.commands.Command.Result
import atm.commands.SingleArgCommand
import atm.di.AccountExport
import atm.di.AmountsExport
import atm.di.CommandsExport
import atm.di.CommonCommandsImports
import atm.di.OutputterExport
import atm.di.UserCommandsImports
import atm.di.amountsModule
import atm.di.getCommonCommandsModule
import atm.di.getUserCommandsModule
import atm.di.systemOutModule
import undagger.BeanHolder
import undagger.BeanHolder.Companion.plus
import undagger.perComponent

interface UserCommandsComponent {
    val router: CommandRouter
}

fun userCommandsComponent(import: AccountExport): UserCommandsComponent = object :
    UserCommandsComponent,
    AccountExport by import,
    AmountsExport by amountsModule,
    OutputterExport by systemOutModule,
    CommandsExport,
    CommonCommandsImports,
    UserCommandsImports {

    override val router: CommandRouter by perComponent(::CommandRouter)

    override val withdrawalLimiter: WithdrawalLimiter by perComponent(::WithdrawalLimiter)
    override val commands: BeanHolder<String, Command> = getCommonCommandsModule().commands +
        getUserCommandsModule().commands +
        // User logged in. Don't allow a login command if we already have someone logged in!
        perComponent(
            "login" to {
                object : SingleArgCommand() {
                    override fun handleArg(arg: String): Result {
                        val loggedInUser = account.username
                        val username = arg
                        outputter.output("$loggedInUser is already logged in")
                        if (loggedInUser != username) outputter.output("run `logout` first before trying to log in another user")
                        return Result.handled
                    }
                }
            }
        )

    override fun userCommandsComponent(username: String): UserCommandsComponent = error("No nested logins allowed!")
}
