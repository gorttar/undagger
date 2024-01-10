package atm.commands

import atm.CommandRouter
import java.util.Optional
import kotlin.jvm.optionals.getOrNull


/** A text-based command handler. */
interface Command {
    /**
     * Processes and optionally acts upon the given [input].
     *
     * @return a [Result] indicating how the input was handled
     */
    fun handleInput(input: List<String>): Result

    /**
     * A command result, which has a [Status] and optionally a new [CommandRouter] that
     * will handle subsequent commands.
     */
    class Result(
        val status: Status,
        nestedCommandRouter: Optional<CommandRouter>
    ) {
        val nestedCommandRouter = nestedCommandRouter.getOrNull()

        companion object {
            val invalid: Result = Result(Status.INVALID, Optional.empty())
            val handled: Result = Result(Status.HANDLED, Optional.empty())
            val inputCompleted: Result = Result(Status.INPUT_COMPLETED, Optional.empty())
            fun enterNestedCommandSet(nestedCommandRouter: CommandRouter): Result =
                Result(Status.HANDLED, Optional.of(nestedCommandRouter))
        }
    }

    enum class Status {
        /** The command or its arguments were invalid. */
        INVALID,

        /** The command handled the input and no other commands should attempt to handle it. */
        HANDLED,

        /** The command handled the input and no further inputs should be submitted. */
        INPUT_COMPLETED
    }
}
