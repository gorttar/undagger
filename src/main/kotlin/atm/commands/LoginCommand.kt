package atm.commands

import atm.commands.Command.Result
import atm.di.AccountExport
import atm.di.OutputterExport
import atm.di.UserCommandsComponentExport

interface LoginCommandImport : OutputterExport, UserCommandsComponentExport

/** Logs in a user, allowing them to interact with the ATM. */
class LoginCommand(private val import: LoginCommandImport) : SingleArgCommand() {
    public override fun handleArg(arg: String): Result {
        val username = arg
        return Result.enterNestedCommandSet(import.userCommandsComponent(username).router)
    }
}

interface NestedLoginCommandImport : OutputterExport, AccountExport

class NestedLoginCommand(private val import: NestedLoginCommandImport) : SingleArgCommand() {
    // User logged in. Don't allow a login command if we already have someone logged in!
    override fun handleArg(arg: String): Result = import.run {
        val loggedInUser = account.username
        val username = arg
        outputter.output("$loggedInUser is already logged in")
        if (loggedInUser != username) outputter.output("run `logout` first before trying to log in another user")
        return Result.handled
    }
}
