package atm

import atm.commands.Command.Status
import java.util.ArrayDeque
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class CommandProcessor @Inject constructor(firstCommandRouter: CommandRouter) {
    private val commandRouterStack = ArrayDeque<CommandRouter>()
        .apply { push(firstCommandRouter) }

    fun process(input: String): Status {
        val result = commandRouterStack.peek().route(input)
        return if (result.status() == Status.INPUT_COMPLETED) {
            commandRouterStack.pop()
            if (commandRouterStack.isEmpty()) Status.INPUT_COMPLETED else Status.HANDLED
        } else {
            result.nestedCommandRouter().ifPresent(commandRouterStack::push)
            result.status()
        }
    }
}
