package undagger.atm.commands

import undagger.atm.commands.Command.Result.Companion.invalid

/** Abstract command that accepts a single argument.  */
abstract class SingleArgCommand : Command {
    override fun handleInput(input: List<String>): Command.Result =
        if (input.size == 1) handleArg(input[0]) else invalid()

    /** Handles the single argument to the command.  */
    protected abstract fun handleArg(arg: String): Command.Result
}
