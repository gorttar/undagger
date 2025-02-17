package atm

import atm.di.modules.MaximumWithdrawal
import atm.di.modules.PerSession
import java.math.BigDecimal
import javax.inject.Inject

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