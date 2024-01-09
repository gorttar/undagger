package atm.data

import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/** An ATM database that stores all of its data in memory. */
@Singleton
class Database @Inject constructor() {
    private val accounts = HashMap<String, Account>()
    fun getAccount(username: String): Account = accounts.computeIfAbsent(username, ::Account)

    /** An individual user's account. */
    class Account(val username: String) {
        var balance: BigDecimal = BigDecimal.ZERO
            private set

        fun deposit(amount: BigDecimal) {
            balance += amount
        }

        fun withdraw(amount: BigDecimal) {
            deposit(-amount)
        }
    }
}
