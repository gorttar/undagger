package undagger.atm.commands

import undagger.atm.commands.Command.Result
import undagger.atm.data.Database
import undagger.atm.data.Database.Account
import undagger.atm.di.Import
import undagger.atm.di.components.UserCommandsRouter
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.invoke

interface LoginCommandImport : OutputterExport, Import {
    val database: Database
    fun userCommandRouter(account: Account): UserCommandsRouter
}

class LoginCommand(private val import: LoginCommandImport) : SingleArgCommand() {
    public override fun handleArg(arg: String): Result = import {
        val username = arg
        val account = database.getAccount(username)
        outputter.output("$username is logged in with balance: ${account.balance}")
        return Result.enterNestedCommandSet(userCommandRouter(account).router)
    }
}
