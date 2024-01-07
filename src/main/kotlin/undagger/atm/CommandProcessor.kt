package undagger.atm

import undagger.atm.commands.Command.Status
import undagger.atm.di.Bean
import undagger.atm.di.Import
import java.util.ArrayDeque

interface CommandProcessorImport : Import {
    val firstCommandRouter: CommandRouter
}

class CommandProcessor(import: CommandProcessorImport) : Bean {
    private val commandRouterStack = ArrayDeque<CommandRouter>()
        .apply { push(import.firstCommandRouter) }

    fun process(input: String): Status {
        val result = commandRouterStack.peek().route(input)
        return if (result.status() == Status.INPUT_COMPLETED) {
            commandRouterStack.pop()
            if (commandRouterStack.isEmpty()) {
                Status.INPUT_COMPLETED
            } else {
                Status.HANDLED
            }
        } else {
            result.nestedCommandRouter().ifPresent(commandRouterStack::push)
            result.status()
        }
    }
}
