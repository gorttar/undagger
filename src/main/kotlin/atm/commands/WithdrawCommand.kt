package atm.commands

import atm.di.exports.AmountsExport
import atm.di.utils.invoke
import java.math.BigDecimal

interface WithdrawCommandImport : UserCommandImport, AmountsExport

class WithdrawCommand(private val import: WithdrawCommandImport) : BigDecimalCommand(import) {
    override fun handleAmount(amount: BigDecimal) = import {
        val remainingWithdrawalLimit = withdrawalLimiter.remainingWithdrawalLimit
        when {
            amount > remainingWithdrawalLimit -> outputter.output(
                "you may not withdraw $amount; you may withdraw $remainingWithdrawalLimit more in this session"
            )

            account.balance - amount < minimumBalance -> outputter.output("More gold is required!")
            else -> {
                account.withdraw(amount)
                withdrawalLimiter.recordWithdrawal(amount)
                outputter.output("your new balance is: ${account.balance}")
            }
        }
    }
}
