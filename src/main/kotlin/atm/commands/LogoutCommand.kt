package atm.commands

import atm.commands.Command.Result
import atm.data.Database.Account
import atm.io.Outputter
import javax.inject.Inject

/** Logs out the current user. */
class LogoutCommand @Inject constructor(
    private val outputter: Outputter,
    private val account: Account
) : Command {
    override fun handleInput(input: List<String>): Result = if (input.isEmpty()) {
        outputter.output("logged out ${account.username}")
        Result.inputCompleted
    } else Result.invalid
}
