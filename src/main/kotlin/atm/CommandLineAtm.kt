package atm

import atm.di.components.DaggerCommandProcessorComponent
import java.util.Scanner

internal object CommandLineAtm {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        val commandProcessorFactory = DaggerCommandProcessorComponent.create()
        val commandProcessor = commandProcessorFactory.commandProcessor()
        while (scanner.hasNextLine()) {
            @Suppress("UNUSED_VARIABLE") val unused = commandProcessor.process(scanner.nextLine())
        }
    }
}
