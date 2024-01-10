package atm

import atm.commands.Command
import atm.commands.Command.Status
import java.util.ArrayDeque
import javax.inject.Singleton

interface CommandProcessorImport {
    val firstCommandRouter: CommandRouter
}

/**
 * Processes successive commands by delegating to a [CommandRouter].
 *
 * <p>Whereas [CommandRouter] routes an input string to a particular [Command], this
 * class maintains inter-command state to determine which [CommandRouter] should route
 * successive commands.
 *
 * <p>This class is [Singleton] scoped because it has mutable state
 * ([commandRouterStack]), and all users of [CommandProcessor] must use the same instance.
 */
class CommandProcessor(import: CommandProcessorImport) {
    private val commandRouterStack = ArrayDeque<CommandRouter>()
        .apply { push(import.firstCommandRouter) }

    fun process(input: String): Status {
        val result = commandRouterStack.peek().route(input)
        return when (val status = result.status) {
            Status.INPUT_COMPLETED -> {
                commandRouterStack.pop()
                if (commandRouterStack.isEmpty()) status else Status.HANDLED
            }
            Status.HANDLED -> {
                result.nestedCommandRouter?.let(commandRouterStack::push)
                status
            }
            Status.INVALID -> status
        }
    }
}
