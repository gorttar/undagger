package atm.commands

import atm.di.exports.OutputterExport
import atm.di.utils.invoke

class HelloWorldCommand(private val import: OutputterExport) : Command {
    override fun handleInput(input: List<String>): Command.Result = import {
        if (input.isEmpty()) {
            outputter.output("world!")
            Command.Result.handled()
        } else Command.Result.invalid()
    }
}
