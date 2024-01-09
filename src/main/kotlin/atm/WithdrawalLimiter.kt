package atm

import atm.di.MaximumWithdrawal
import atm.di.PerSession
import java.math.BigDecimal
import javax.inject.Inject

/** Maintains the withdrawal amount available within a user session. */
@PerSession
class WithdrawalLimiter @Inject constructor(@MaximumWithdrawal private val maximumWithdrawal: BigDecimal) {
    var remainingWithdrawalLimit: BigDecimal = maximumWithdrawal
        private set

    fun recordDeposit(amount: BigDecimal) {
        remainingWithdrawalLimit += amount
    }

    fun recordWithdrawal(amount: BigDecimal) {
        recordDeposit(-amount)
    }
}
