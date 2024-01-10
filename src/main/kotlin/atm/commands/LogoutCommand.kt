package atm.commands

import atm.commands.Command.Result
import atm.di.AccountExport
import atm.di.OutputterExport

interface LogoutCommandImport : OutputterExport, AccountExport

/** Logs out the current user. */
class LogoutCommand(private val import: LogoutCommandImport) : Command {
    override fun handleInput(input: List<String>): Result = import.handle(input)
    private fun LogoutCommandImport.handle(input: List<String>) = if (input.isEmpty()) {
        outputter.output("logged out ${account.username}")
        Result.inputCompleted
    } else Result.invalid
}
