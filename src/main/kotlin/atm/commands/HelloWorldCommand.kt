package atm.commands

import atm.commands.Command.Result
import atm.di.OutputterExport

class HelloWorldCommand(private val import: OutputterExport) : Command {
    override fun handleInput(input: List<String>): Result = if (input.isEmpty()) {
        import.outputter.output("howdy!")
        Result.handled
    } else Result.invalid
}
