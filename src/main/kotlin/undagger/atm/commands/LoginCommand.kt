package undagger.atm.commands

import undagger.atm.commands.Command.Result.Companion.enterNestedCommandSet
import undagger.atm.data.Database
import undagger.atm.di.components.UserCommandsRouter
import undagger.atm.di.exports.OutputterExport

interface LoginCommandImport : OutputterExport {
    val database: Database
    fun userCommandRouter(account: Database.Account): UserCommandsRouter
}

class LoginCommand(private val import: LoginCommandImport) : SingleArgCommand() {

    public override fun handleArg(arg: String): Command.Result = with(import) {
        val username = arg
        val account = database.getAccount(username)
        outputter.output("$username is logged in with balance: ${account.balance}")
        return enterNestedCommandSet(
            userCommandRouter(account).router
        )
    }
}
