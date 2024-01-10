package atm

import atm.di.AmountsExport
import java.math.BigDecimal

/** Maintains the withdrawal amount available within a user session. */
class WithdrawalLimiter(import: AmountsExport) {
    var remainingWithdrawalLimit: BigDecimal = import.maximumWithdrawal
        private set

    fun recordDeposit(amount: BigDecimal) {
        remainingWithdrawalLimit += amount
    }

    fun recordWithdrawal(amount: BigDecimal) {
        recordDeposit(-amount)
    }
}
