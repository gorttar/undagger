package undagger.atm.commands

import undagger.atm.di.utils.invoke
import java.math.BigDecimal

class DepositCommand(private val import: UserCommandImport) : BigDecimalCommand(import) {
    override fun handleAmount(amount: BigDecimal): Unit = import {
        account.deposit(amount)
        withdrawalLimiter.recordDeposit(amount)
        outputter.output("${account.username} now has: ${account.balance}")
    }
}
