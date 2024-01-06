package undagger.atm.commands

import undagger.atm.commands.Command.Result.Companion.enterNestedCommandSet
import undagger.atm.data.Database
import undagger.atm.di.components.UserCommandsRouter
import undagger.atm.di.exports.OutputterExport

interface LoginCommandImport : OutputterExport {
    val database: Database
    val userCommandRouterFactory: UserCommandsRouter.Factory
}

class LoginCommand(import: LoginCommandImport) : SingleArgCommand() {
    private val outputter by import::outputter
    private val database by import::database
    private val userCommandRouterFactory by import::userCommandRouterFactory

    public override fun handleArg(arg: String): Command.Result {
        val username = arg
        val account = database.getAccount(username)
        outputter.output("$username is logged in with balance: ${account.balance}")
        return enterNestedCommandSet(
            userCommandRouterFactory.create(account).router
        )
    }
}
