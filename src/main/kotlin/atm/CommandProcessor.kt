package atm

import atm.commands.Command.Result
import atm.commands.Command.Status
import java.util.ArrayDeque
import java.util.Deque
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class CommandProcessor @Inject constructor(firstCommandRouter: CommandRouter) {
    private val commandRouterStack: Deque<CommandRouter> = ArrayDeque()

    init {
        commandRouterStack.push(firstCommandRouter)
    }

    fun process(input: String): Status {
        val result: Result = commandRouterStack.peek().route(input)
        if (result.status() == Status.INPUT_COMPLETED) {
            commandRouterStack.pop()
            return if (commandRouterStack.isEmpty()) Status.INPUT_COMPLETED else Status.HANDLED
        }
        result.nestedCommandRouter().ifPresent(commandRouterStack::push)
        return result.status()
    }
}