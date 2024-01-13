package atm.di.components

import atm.CommandRouter
import atm.WithdrawalLimiter
import atm.commands.Command
import atm.di.AccountExport
import atm.di.AmountsExport
import atm.di.CommandsExport
import atm.di.CommonCommandsImports
import atm.di.OutputterExport
import atm.di.UserCommandsImports
import atm.di.amountsModule
import atm.di.commonCommandsModule
import atm.di.systemOutModule
import atm.di.userCommandsModule
import undagger.BeanHolder
import undagger.BeanHolder.Companion.plus
import undagger.perComponent

interface UserCommandsComponent {
    val router: CommandRouter
}

fun userCommandsComponent(import: AccountExport): UserCommandsComponent = object :
    UserCommandsComponent,
    AccountExport by import,
    AmountsExport by amountsModule(),
    OutputterExport by systemOutModule(),
    CommandsExport,
    CommonCommandsImports,
    UserCommandsImports {

    override val router: CommandRouter by perComponent(::CommandRouter)

    override val withdrawalLimiter: WithdrawalLimiter by perComponent(::WithdrawalLimiter)
    override val commands: BeanHolder<String, Command> = commonCommandsModule().commands +
        userCommandsModule().commands

    override fun userCommandsComponent(username: String): UserCommandsComponent = error("No nested logins allowed!")
}
