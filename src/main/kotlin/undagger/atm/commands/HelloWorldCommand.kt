package undagger.atm.commands

import undagger.atm.di.exports.OutputterExport

interface HelloWorldCommandImport : OutputterExport

class HelloWorldCommand(import: HelloWorldCommandImport) : Command {
    private val outputter = import.outputter

    override fun handleInput(input: List<String>): Command.Result {
        if (input.isNotEmpty()) {
            return Command.Result.invalid()
        }
        outputter.output("world!")
        return Command.Result.handled()
    }
}