package undagger.atm.di.components

import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.commands.Command
import undagger.atm.commands.DepositCommand
import undagger.atm.commands.LogoutCommand
import undagger.atm.commands.UserCommandImport
import undagger.atm.commands.WithdrawCommand
import undagger.atm.commands.WithdrawCommandImport
import undagger.atm.di.Bean
import undagger.atm.di.BeanHolder
import undagger.atm.di.BeanHolder.Companion.plus
import undagger.atm.di.Import
import undagger.atm.di.utils.perComponent

// can't make it inner interface of UserCommandsRouter: https://youtrack.jetbrains.com/issue/KT-17455/
interface UserCommandsRouterImport : UserCommandImport, CommandRouterImport, Import

//@PerSession
//@Subcomponent(modules = [UserCommandsModule::class])
class UserCommandsRouter(import: UserCommandsRouterImport) : //todo naming; does NOT inherit CommandRouter
    UserCommandsRouterImport by import,
    WithdrawCommandImport,
    Bean {

    val router: CommandRouter by perComponent(::CommandRouter)
    override val commands: BeanHolder<String, Command> = import.commands + perComponent(
        "deposit" to ::DepositCommand,
        "withdraw" to ::WithdrawCommand,
        "logout" to ::LogoutCommand,
    )
}
