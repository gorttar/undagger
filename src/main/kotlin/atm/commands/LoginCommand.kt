package atm.commands

import atm.commands.Command.Result
import atm.data.Database.Account
import atm.di.components.UserCommandsComponent
import atm.io.Outputter
import java.util.Optional
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull


/** Logs in a user, allowing them to interact with the ATM. */
class LoginCommand @Inject constructor(
    private val outputter: Outputter,
    private val userCommandsComponentFactory: UserCommandsComponent.Factory,
    account: Optional<Account>
) : SingleArgCommand() {
    private val account = account.getOrNull()

    @Suppress("UnnecessaryVariable")
    override fun handleArg(arg: String): Result = account
        // If an Account binding exists, that means there is a user logged in. Don't allow a login
        // command if we already have someone logged in!
        ?.let { account ->
            val loggedInUser = account.username
            val username = arg
            outputter.output("$loggedInUser is already logged in")
            if (loggedInUser != username) outputter.output("run `logout` first before trying to log in another user")
            Result.handled
        } ?: run {
        val username = arg
        Result.enterNestedCommandSet(userCommandsComponentFactory.create(username).router)
    }
}
