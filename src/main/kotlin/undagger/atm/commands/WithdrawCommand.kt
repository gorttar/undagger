package undagger.atm.commands

import undagger.atm.di.exports.LimitsExport
import java.math.BigDecimal

interface WithdrawCommandImport : UserCommandImport, LimitsExport

class WithdrawCommand(
    private val import: WithdrawCommandImport,
) : BigDecimalCommand(import.outputter) {
    public override fun handleAmount(amount: BigDecimal) = with(import) {
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
