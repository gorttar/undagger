package undagger.atm.di.components

import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.commands.Command
import undagger.atm.commands.DepositCommand
import undagger.atm.commands.LogoutCommand
import undagger.atm.commands.UserCommandImport
import undagger.atm.commands.WithdrawCommand
import undagger.atm.commands.WithdrawCommandImport
import undagger.atm.di.utils.new
import undagger.atm.di.utils.perComponent

// can't make it inner interface of UserCommandsRouter: https://youtrack.jetbrains.com/issue/KT-17455/
interface UserCommandsRouterImport : UserCommandImport, CommandRouterImport

//@PerSession
//@Subcomponent(modules = [UserCommandsModule::class])
class UserCommandsRouter(import: UserCommandsRouterImport) : //todo naming; does NOT inherit CommandRouter
    UserCommandsRouterImport by import,
    WithdrawCommandImport
{

    val router: CommandRouter by perComponent(::CommandRouter)
    override val commands: Map<String, Command> by perComponent {
        import.commands + mapOf(
            "deposit" to new(::DepositCommand),
            "withdraw" to new(::WithdrawCommand),
            "logout" to new(::LogoutCommand),
        )
    }
}
