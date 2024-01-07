package undagger.atm

import undagger.atm.commands.Command
import undagger.atm.commands.Command.Result.Companion.invalid
import undagger.atm.di.BeanHolder

interface CommandRouterImport {
    val commands: BeanHolder<String, Command>
}

class CommandRouter(private val import: CommandRouterImport) {
    fun route(input: String): Command.Result {
        val splitInput = split(input)
        if (splitInput.isEmpty()) {
            return invalidCommand(input)
        }
        val commandKey = splitInput[0]
        val command = import.commands[commandKey] ?: return invalidCommand(input)
        val args = splitInput.subList(1, splitInput.size)
        val result = command.handleInput(args)
        return if (result.status() == Command.Status.INVALID) invalidCommand(input) else result
    }

    private fun invalidCommand(input: String): Command.Result {
        println("couldn't understand \"$input\". please try again.")
        return invalid()
    }

    companion object {
        // Split on whitespace
        private fun split(input: String): List<String> {
            return input.trim { it <= ' ' }.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
        }
    }
}