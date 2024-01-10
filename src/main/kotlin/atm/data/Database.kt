package atm.data

import java.math.BigDecimal

/** An ATM database that stores all of its data in memory. */
class Database {
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
