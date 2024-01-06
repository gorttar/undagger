package undagger.atm.data

import java.math.BigDecimal

class Database {
    private val accounts: MutableMap<String, Account> = HashMap()

    fun getAccount(username: String): Account {
        return accounts.computeIfAbsent(username, ::Account)
    }

    class Account(val username: String) {
        var balance = BigDecimal.ZERO
            private set

        fun deposit(amount: BigDecimal) {
            balance += amount
        }

        fun withdraw(amount: BigDecimal) {
            balance -= amount
        }
    }
}
