package undagger.atm.commands

import undagger.atm.di.Import
import undagger.atm.di.exports.LimitsExport
import undagger.atm.di.utils.invoke
import java.math.BigDecimal

interface WithdrawCommandImport : UserCommandImport, LimitsExport, Import

class WithdrawCommand(private val import: WithdrawCommandImport) : BigDecimalCommand(import) {
    public override fun handleAmount(amount: BigDecimal) = import {
        when {
            amount > maximumWithdrawal -> outputter.output("You are too greedy!")
            account.balance - amount < minimumBalance -> outputter.output("More gold is required!")
            else -> {
                account.withdraw(amount)
                outputter.output("your new balance is: ${account.balance}")
            }
        }
    }
}
