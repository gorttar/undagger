package atm.di.exports

import atm.WithdrawalLimiter

interface WithdrawalLimiterExport {
    val withdrawalLimiter: WithdrawalLimiter
}
