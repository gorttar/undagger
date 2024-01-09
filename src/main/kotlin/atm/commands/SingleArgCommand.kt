package atm.commands

import atm.commands.Command.Result

/** Abstract command that accepts a single argument. */
abstract class SingleArgCommand : Command {
    override fun handleInput(input: List<String>): Result =
        input.singleOrNull()?.let(::handleArg) ?: Result.invalid

    /** Handles the single argument to the command. */
    protected abstract fun handleArg(arg: String): Result
}
