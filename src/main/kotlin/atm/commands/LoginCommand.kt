package atm.commands

import atm.commands.Command.Result
import atm.data.Database.Account
import atm.di.components.UserCommandsComponent
import atm.di.exports.AccountExport
import atm.di.exports.DatabaseExport
import atm.di.exports.OutputterExport

interface LoginCommandImport : OutputterExport, AccountExport, DatabaseExport {
    fun userCommandsComponent(account: Account): UserCommandsComponent
}

class LoginCommand(private val import: LoginCommandImport) : SingleArgCommand() {
    public override fun handleArg(arg: String): Result = import.handleArg(arg)

    private fun LoginCommandImport.handleArg(arg: String): Result = account?.let { Result.handled() } ?: run {
        val username = arg
        val account = database.getAccount(username)
        outputter.output("$username is logged in with balance: ${account.balance}")
        Result.enterNestedCommandSet(userCommandsComponent(account).router)
    }
}
