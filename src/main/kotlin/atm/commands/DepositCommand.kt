package atm.commands

import atm.di.AccountExport
import atm.di.OutputterExport
import atm.di.WithdrawalLimiterExport
import undagger.invoke
import java.math.BigDecimal

interface DepositCommandImport : AccountExport, OutputterExport, WithdrawalLimiterExport

/** Deposits money to the ATM. */
class DepositCommand(private val import: DepositCommandImport) : BigDecimalCommand(import) {
    override fun handleAmount(amount: BigDecimal): Unit = import {
        account.deposit(amount)
        withdrawalLimiter.recordDeposit(amount)
        outputter.output("your new balance is: ${account.balance}")
    }
}
