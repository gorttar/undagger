package atm.di.components

import atm.CommandProcessor
import atm.CommandProcessorImport
import atm.CommandRouter
import atm.commands.Command
import atm.di.CommandsExport
import atm.di.CommonCommandsImports
import atm.di.DatabaseExport
import atm.di.OutputterExport
import atm.di.UserCommandsComponentExport
import atm.di.accountModule
import atm.di.databaseModule
import atm.di.getCommonCommandsModule
import atm.di.systemOutModule
import undagger.BeanHolder
import undagger.new
import undagger.perComponent

interface CommandProcessorComponent {
    val processor: CommandProcessor
}

val commandProcessorComponent: CommandProcessorComponent = object :
    CommandProcessorComponent,
    DatabaseExport by databaseModule,
    OutputterExport by systemOutModule,
    CommandsExport,
    CommonCommandsImports,
    CommandProcessorImport,
    UserCommandsComponentExport {

    override val processor: CommandProcessor by perComponent(::CommandProcessor)

    override val firstCommandRouter: CommandRouter by perComponent(::CommandRouter)
    override val commands: BeanHolder<String, Command> = getCommonCommandsModule().commands
    override fun userCommandsComponent(username: String): UserCommandsComponent =
        accountModule(username).new(::userCommandsComponent)
}
