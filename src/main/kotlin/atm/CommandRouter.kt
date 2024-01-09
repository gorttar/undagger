package atm

import atm.commands.Command
import atm.commands.Command.Result
import atm.commands.Command.Status
import javax.inject.Inject

/** Routes individual text commands to the appropriate [Command] (s). */
class CommandRouter @Inject constructor(private val commands: Map<String, @JvmSuppressWildcards Command>) {
    /**
     * Calls [Command.handleInput] on this router's [commands]
     */
    fun route(input: String): Result = input
        .trim()
        .split("\\s+".toRegex())
        .dropLastWhile { it.isEmpty() }
        .takeUnless { splitInput -> splitInput.isEmpty() }
        ?.let { splitInput -> commands[splitInput[0]]?.run { handleInput(splitInput.drop(1)) } }
        ?.takeUnless { it.status == Status.INVALID }
        ?: run {
            println("couldn't understand \"$input\". please try again.")
            Result.invalid
        }
}