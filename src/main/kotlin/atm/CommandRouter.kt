package atm

import atm.commands.Command
import atm.commands.Command.Result
import atm.commands.Command.Status
import atm.di.CommandsExport

/** Routes individual text commands to the appropriate [Command] (s). */
class CommandRouter(private val import: CommandsExport) {

    /**
     * Calls [Command.handleInput] on this router's [CommandsExport.commands] from [import]
     */
    fun route(input: String): Result = input
        .trim()
        .split("\\s+".toRegex())
        .dropLastWhile { it.isEmpty() }
        .takeUnless { splitInput -> splitInput.isEmpty() }
        ?.let { splitInput -> import.commands[splitInput[0]]?.run { handleInput(splitInput.drop(1)) } }
        ?.takeUnless { it.status == Status.INVALID }
        ?: run {
            println("couldn't understand \"$input\". please try again.")
            Result.invalid
        }
}