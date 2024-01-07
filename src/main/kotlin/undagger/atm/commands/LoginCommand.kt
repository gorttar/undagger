package undagger.atm.commands

import undagger.atm.commands.Command.Result
import undagger.atm.data.Database.Account
import undagger.atm.di.components.UserCommandsRouter
import undagger.atm.di.exports.AccountExport
import undagger.atm.di.exports.DatabaseExport
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.invoke

interface LoginCommandImport : OutputterExport, AccountExport, DatabaseExport {
    fun userCommandRouter(account: Account): UserCommandsRouter
}

class LoginCommand(private val import: LoginCommandImport) : SingleArgCommand() {
    public override fun handleArg(arg: String): Result = import {
        val username = arg
        val account = account
        if (account != null) {
            outputter.output("${account.username} is already logged in with balance: ${account.balance}")
            Result.handled()
        } else {
            val userAccount = database.getAccount(username)
            outputter.output("$username is logged in with balance: ${userAccount.balance}")
            Result.enterNestedCommandSet(userCommandRouter(userAccount).router)
        }
    }
}
