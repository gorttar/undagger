package undagger.atm.di.exports

import undagger.atm.WithdrawalLimiter

interface WithdrawalLimiterExport {
    val withdrawalLimiter: WithdrawalLimiter
}
