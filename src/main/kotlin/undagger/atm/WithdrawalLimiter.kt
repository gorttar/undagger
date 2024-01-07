package undagger.atm

import undagger.atm.di.exports.LimitsExport
import java.math.BigDecimal

interface WithdrawalLimiterImport : LimitsExport

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
