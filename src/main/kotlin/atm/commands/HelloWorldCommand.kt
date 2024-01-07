package atm.commands

import atm.io.Outputter
import javax.inject.Inject

class HelloWorldCommand @Inject constructor(private val outputter: Outputter) : Command {
    override fun handleInput(input: List<String>): Command.Result = if (input.isEmpty()) {
        outputter.output("world!")
        Command.Result.handled()
    } else Command.Result.invalid()
}
