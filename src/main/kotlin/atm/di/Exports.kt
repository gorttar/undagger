package atm.di

import atm.WithdrawalLimiter
import atm.commands.Command
import atm.data.Database
import atm.data.Database.Account
import atm.di.components.UserCommandsComponent
import atm.io.Outputter
import undagger.BeanHolder
import java.math.BigDecimal

interface AccountExport {
    val account: Account
}

interface AmountsExport {
    val minimumBalance: BigDecimal
    val maximumWithdrawal: BigDecimal
}

interface CommandsExport {
    val commands: BeanHolder<String, Command>
}

interface DatabaseExport {
    val database: Database
}

interface OutputterExport {
    val outputter: Outputter
}

interface UserCommandsComponentExport {
    fun userCommandsComponent(username: String): UserCommandsComponent
}

interface WithdrawalLimiterExport {
    val withdrawalLimiter: WithdrawalLimiter
}
