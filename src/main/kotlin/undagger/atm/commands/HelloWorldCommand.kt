package undagger.atm.commands

import undagger.atm.commands.Command.Result
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.invoke

class HelloWorldCommand(private val import: OutputterExport) : Command {
    override fun handleInput(input: List<String>): Result = import {
        if (input.isEmpty()) {
            outputter.output("world!")
            Result.handled()
        } else {
            outputter.output("No args expected, found: $input")
            Result.invalid()
        }
    }
}