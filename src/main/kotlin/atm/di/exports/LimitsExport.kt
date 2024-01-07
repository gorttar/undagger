package atm.di.exports

import java.math.BigDecimal

interface LimitsExport {
    val minimumBalance: BigDecimal get() = BigDecimal.ZERO
    val maximumWithdrawal: BigDecimal get() = 1_000.toBigDecimal()
}
