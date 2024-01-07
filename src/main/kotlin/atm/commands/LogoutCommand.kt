package atm.commands

import atm.commands.Command.Result

class LogoutCommand : Command {
    override fun handleInput(input: List<String>): Result =
        if (input.isEmpty()) Result.inputCompleted() else Result.invalid()
}
