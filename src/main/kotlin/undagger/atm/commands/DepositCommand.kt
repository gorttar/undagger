package undagger.atm.commands

import undagger.atm.data.Database.Account
import undagger.atm.io.Outputter
import java.math.BigDecimal

class DepositCommand(
    import: UserCommandImport,
) : BigDecimalCommand(import.outputter) {
    private val account: Account by import::account
    private val outputter: Outputter by import::outputter

    override fun handleAmount(amount: BigDecimal) {
        account.deposit(amount)
        outputter.output("${account.username} now has: ${account.balance}")
    }
}
