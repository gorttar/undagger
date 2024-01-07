package undagger.atm.di.exports

import undagger.atm.di.Import
import java.math.BigDecimal

interface LimitsExport : Import {
    val minimumBalance: BigDecimal get() = BigDecimal.ZERO
    val maximumWithdrawal: BigDecimal get() = 1_000.toBigDecimal()
}
