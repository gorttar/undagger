package atm.commands

import atm.commands.Command.Result
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
