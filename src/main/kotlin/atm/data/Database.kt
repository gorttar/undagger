package atm.data

import java.math.BigDecimal

class Database {
    private val accounts = HashMap<String, Account>()
    fun getAccount(username: String): Account = accounts.computeIfAbsent(username, ::Account)

    class Account(val username: String) {
        var balance = BigDecimal.ZERO
            private set

        fun deposit(amount: BigDecimal) {
            balance += amount
        }

        fun withdraw(amount: BigDecimal) {
            deposit(-amount)
        }
    }
}
