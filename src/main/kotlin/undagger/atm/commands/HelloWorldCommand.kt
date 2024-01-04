package undagger.atm.commands

import undagger.atm.modules.OutputterRequirement

interface HelloWorldCommandDependency : OutputterRequirement

class HelloWorldCommand(dependency: HelloWorldCommandDependency) : Command {
    private val outputter = dependency.outputter

    override fun handleInput(input: List<String>): Command.Result {
        if (input.isNotEmpty()) {
            return Command.Result.invalid()
        }
        outputter.output("world!")
        return Command.Result.handled()
    }
}