package undagger.atm.di.components

import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.commands.Command
import undagger.atm.commands.DepositCommand
import undagger.atm.data.Database
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.new
import undagger.atm.di.utils.perComponent
import undagger.atm.io.Outputter

//@PerSession
//@Subcomponent(modules = [UserCommandsModule::class])
class UserCommandsRouter(import: Import) : //todo naming
    DepositCommand.Import,
    CommandRouterImport
{
    interface Import : OutputterExport, CommandRouterImport {
        val account: Database.Account
    }

    fun interface Factory {
        fun create(account: Database.Account): UserCommandsRouter
    }

    override val account: Database.Account by import::account
    override val outputter: Outputter by import::outputter
    val router: CommandRouter by perComponent(::CommandRouter)
    override val commands: Map<String, Command> by perComponent {
        import.commands + mapOf(
            "deposit" to new(::DepositCommand),
        )
    }
}
