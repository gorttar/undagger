package undagger.atm.di.components

import undagger.atm.CommandProcessor
import undagger.atm.CommandRouter
import undagger.atm.CommandRouterImport
import undagger.atm.commands.Command
import undagger.atm.commands.HelloWorldCommandImport
import undagger.atm.commands.LoginCommandImport
import undagger.atm.data.Database
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.modules.HelloWorldModule
import undagger.atm.di.modules.LoginCommandModule
import undagger.atm.di.modules.SystemOutModule
import undagger.atm.di.utils.new
import undagger.atm.di.utils.perComponent

object CommandProcessorFactory :
    OutputterExport by SystemOutModule,
    HelloWorldCommandImport,
    LoginCommandImport,
    CommandRouterImport,
    CommandProcessor.Import
{

    override val database: Database by perComponent { Database() }
    override val firstCommandRouter: CommandRouter by perComponent(::CommandRouter)
    override val commands: Map<String, Command> by perComponent {
        mapOf(
            "hello" to new(::HelloWorldModule).command,
            "login" to new(::LoginCommandModule).command,
        )
    }
    val processor: CommandProcessor by perComponent(::CommandProcessor)
    override val userCommandRouterFactory: UserCommandsRouter.Factory by perComponent {
        UserCommandsRouter.Factory { account ->
            UserCommandsRouter(
                object :
                    UserCommandsRouter.Import,
                    OutputterExport by this,
                    CommandRouterImport by this
                {
                    override val account: Database.Account = account
                }
            )
        }
    }
}
