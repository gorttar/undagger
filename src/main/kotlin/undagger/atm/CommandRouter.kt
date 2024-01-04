package undagger.atm

import undagger.atm.commands.Command
import undagger.atm.commands.Command.Result.Companion.invalid

interface CommandRouterDependency {
    val commands: Map<String, Command>
}

class CommandRouter(dependency: CommandRouterDependency) {
    private val commands: Map<String, Command> = dependency.commands

    fun route(input: String): Command.Result {
        val splitInput = split(input)
        if (splitInput.isEmpty()) {
            return invalidCommand(input)
        }
        val commandKey = splitInput[0]
        val command = commands[commandKey] ?: return invalidCommand(input)
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