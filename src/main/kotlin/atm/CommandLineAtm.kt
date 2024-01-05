package atm

import atm.commands.Command
import atm.di.components.CommandProcessorFactory
import atm.di.components.DaggerCommandProcessorFactory
import java.util.Scanner

internal object CommandLineAtm {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        val commandProcessorFactory: CommandProcessorFactory = DaggerCommandProcessorFactory.create()
        val commandProcessor: CommandProcessor = commandProcessorFactory.commandProcessor()
        while (scanner.hasNextLine()) {
            @Suppress("UNUSED_VARIABLE") val unused: Command.Status = commandProcessor.process(scanner.nextLine())
        }
    }
}