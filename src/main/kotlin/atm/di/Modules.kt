package atm.di

import atm.commands.Command
import atm.commands.DepositCommand
import atm.commands.HelloWorldCommand
import atm.commands.LoginCommand
import atm.commands.LogoutCommand
import atm.commands.WithdrawCommand
import atm.data.Database
import atm.data.Database.Account
import atm.io.Outputter
import undagger.BeanHolder
import undagger.dependent
import undagger.perComponent
import java.math.BigDecimal

/** Bindings for the [Account] of the currently signed-in user.  */
fun DatabaseExport.accountModule(username: String): AccountExport = object : AccountExport {
    override val account: Account = database.getAccount(username)
}

/** Configures various amounts of money the application uses to control transactions. */
val amountsModule: AmountsExport = object : AmountsExport {
    override val minimumBalance: BigDecimal = BigDecimal.ZERO
    override val maximumWithdrawal: BigDecimal = 1_000.toBigDecimal()
}

/** Installs basic commands.  */
fun CommonCommandsImports.getCommonCommandsModule(): CommandsExport = object : CommandsExport {
    override val commands: BeanHolder<String, Command> = perComponent(
        "hello" to ::HelloWorldCommand,
        "login" to ::LoginCommand,
    )
}

val databaseModule: DatabaseExport = object : DatabaseExport {
    override val database: Database by perComponent(dependent(::Database))
}

val systemOutModule: OutputterExport = object : OutputterExport {
    override val outputter: Outputter = Outputter(::println)
}

/** Commands that are only applicable when a user is logged in. */
fun UserCommandsImports.getUserCommandsModule(): CommandsExport = object : CommandsExport {
    override val commands: BeanHolder<String, Command> = perComponent(
        "deposit" to ::DepositCommand,
        "withdraw" to ::WithdrawCommand,
        "logout" to ::LogoutCommand,
    )
}
