package atm.commands

import atm.commands.Command.Result
import atm.io.Outputter
import javax.inject.Inject

class HelloWorldCommand @Inject constructor(private val outputter: Outputter) : Command {
    override fun handleInput(input: List<String>): Result = if (input.isEmpty()) {
        outputter.output("howdy!")
        Result.handled
    } else Result.invalid
}
