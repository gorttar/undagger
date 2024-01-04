package undagger.atm.commands

import undagger.atm.commands.Command.Result.Companion.handled
import undagger.atm.modules.OutputterRequirement

interface LoginCommandDependency : OutputterRequirement

class LoginCommand(dependency: LoginCommandDependency) : SingleArgCommand() {
    private val outputter = dependency.outputter

    public override fun handleArg(arg: String): Command.Result {
        val username = arg
        outputter.output("$username is logged in.")
        return handled()
    }
}