package atm.commands

import atm.WithdrawalLimiter
import atm.data.Database.Account
import atm.io.Outputter
import java.math.BigDecimal
import javax.inject.Inject

/** Deposits money to the ATM. */
class DepositCommand @Inject constructor(
    private val account: Account,
    private val outputter: Outputter,
    private val withdrawalLimiter: WithdrawalLimiter
) : BigDecimalCommand(outputter) {
    override fun handleAmount(amount: BigDecimal) {
        account.deposit(amount)
        withdrawalLimiter.recordDeposit(amount)
        outputter.output("your new balance is: ${account.balance}")
    }
}
