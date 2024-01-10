package atm.commands

import atm.di.AccountExport
import atm.di.AmountsExport
import atm.di.OutputterExport
import atm.di.WithdrawalLimiterExport
import undagger.invoke
import java.math.BigDecimal

interface WithdrawCommandImport : AccountExport, OutputterExport, WithdrawalLimiterExport, AmountsExport

/** Withdraws money from the ATM. */
class WithdrawCommand(private val import: WithdrawCommandImport) : BigDecimalCommand(import) {
    override fun handleAmount(amount: BigDecimal) = import {
        val remainingWithdrawalLimit = withdrawalLimiter.remainingWithdrawalLimit
        when {
            amount > remainingWithdrawalLimit -> outputter.output(
                "you may not withdraw $amount; you may withdraw $remainingWithdrawalLimit more in this session"
            )
            account.balance - amount < minimumBalance -> outputter.output(
                "you don't have sufficient funds to withdraw $amount. your balance is ${account.balance} and the " +
                    "minimum balance is $minimumBalance"
            )
            else -> {
                account.withdraw(amount)
                withdrawalLimiter.recordWithdrawal(amount)
                outputter.output("your new balance is: ${account.balance}")
            }
        }
    }
}
