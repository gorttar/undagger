package undagger.atm.commands

import java.math.BigDecimal

class DepositCommand(private val import: UserCommandImport) : BigDecimalCommand(import) {
    override fun handleAmount(amount: BigDecimal): Unit = with(import) {
        account.deposit(amount)
        outputter.output("${account.username} now has: ${account.balance}")
    }
}
