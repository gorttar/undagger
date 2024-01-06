package undagger.atm.commands

import undagger.atm.commands.Command.Result.Companion.inputCompleted
import undagger.atm.commands.Command.Result.Companion.invalid
import undagger.atm.di.exports.OutputterExport

class LogoutCommand(
    private val import: OutputterExport,
) : Command {

    override fun handleInput(input: List<String>): Command.Result = with(import) {
        if (input.isEmpty()) {
            inputCompleted()
        } else {
            outputter.output("No args expected, found: $input")
            invalid()
        }
    }
}
