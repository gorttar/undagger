package atm

import atm.di.exports.AmountsExport
import java.math.BigDecimal

interface WithdrawalLimiterImport : AmountsExport

class WithdrawalLimiter(import: WithdrawalLimiterImport) {
    var remainingWithdrawalLimit = import.maximumWithdrawal
        private set

    fun recordDeposit(amount: BigDecimal) {
        remainingWithdrawalLimit += amount
    }

    fun recordWithdrawal(amount: BigDecimal) {
        recordDeposit(-amount)
    }
}
