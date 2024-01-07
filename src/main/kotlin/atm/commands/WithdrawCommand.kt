package atm.commands

import atm.WithdrawalLimiter
import atm.data.Database.Account
import atm.di.modules.MinimumBalance
import atm.io.Outputter
import java.math.BigDecimal
import javax.inject.Inject


class WithdrawCommand @Inject constructor(
    private val account: Account,
    private val outputter: Outputter,
    private val withdrawalLimiter: WithdrawalLimiter,
    @MinimumBalance private val minimumBalance: BigDecimal
) : BigDecimalCommand(outputter) {
    override fun handleAmount(amount: BigDecimal) {
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
