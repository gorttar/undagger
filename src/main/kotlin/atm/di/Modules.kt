package atm.di

import atm.commands.Command
import atm.commands.DepositCommand
import atm.commands.HelloWorldCommand
import atm.commands.LoginCommand
import atm.commands.LogoutCommand
import atm.commands.NestedLoginCommand
import atm.commands.WithdrawCommand
import atm.data.Database
import atm.data.Database.Account
import atm.io.Outputter
import undagger.BeanHolder
import undagger.dependent
import undagger.perComponent
import undagger.perRequest
import java.math.BigDecimal

/** Bindings for the [Account] of the currently signed-in user.  */
fun DatabaseExport.accountModule(username: String): AccountExport = object : AccountExport {
    override val account: Account by perComponent { database.getAccount(username) }
}

/** Configures various amounts of money the application uses to control transactions. */
fun amountsModule(): AmountsExport = object : AmountsExport {
    override val minimumBalance: BigDecimal by perRequest { BigDecimal.ZERO }
    override val maximumWithdrawal: BigDecimal by perRequest { 1_000.toBigDecimal() }
}

/** Installs basic commands.  */
fun CommonCommandsImports.commonCommandsModule(): CommandsExport = object : CommandsExport {
    override val commands: BeanHolder<String, Command> = perRequest(
        "hello" to ::HelloWorldCommand,
        "login" to ::LoginCommand,
    )
}

fun databaseModule(): DatabaseExport = object : DatabaseExport {
    override val database: Database by perComponent(dependent(::Database))
}

fun systemOutModule(): OutputterExport = object : OutputterExport {
    override val outputter: Outputter by perRequest { Outputter(::println) }
}

/** Commands that are only applicable when a user is logged in. */
fun UserCommandsImports.userCommandsModule(): CommandsExport = object : CommandsExport {
    override val commands: BeanHolder<String, Command> = perRequest(
        "deposit" to ::DepositCommand,
        "withdraw" to ::WithdrawCommand,
        "logout" to ::LogoutCommand,
        "login" to ::NestedLoginCommand
    )
}
