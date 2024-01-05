package undagger.atm.commands

import undagger.atm.commands.Command.Result.Companion.handled
import undagger.atm.di.exports.OutputterExport

interface LoginCommandImport : OutputterExport

class LoginCommand(import: LoginCommandImport) : SingleArgCommand() {
    private val outputter by import::outputter

    public override fun handleArg(arg: String): Command.Result {
        val username = arg
        outputter.output("$username is logged in.")
        return handled()
    }
}