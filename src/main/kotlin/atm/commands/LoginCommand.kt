package atm.commands

import atm.commands.Command.Result
import atm.data.Database
import atm.data.Database.Account
import atm.di.components.UserCommandsComponent
import atm.io.Outputter
import java.util.Optional
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull


class LoginCommand @Inject constructor(
    private val database: Database,
    private val outputter: Outputter,
    private val userCommandsComponentFactory: UserCommandsComponent.Factory,
    account: Optional<Account>
) : SingleArgCommand() {
    private val account = account.getOrNull()
    override fun handleArg(arg: String): Result = account?.let { Result.handled() } ?: run {
        val username = arg
        val account = database.getAccount(username)
        outputter.output("$username is logged in with balance: ${account.balance}")
        Result.enterNestedCommandSet(userCommandsComponentFactory.create(account).router())
    }
}
