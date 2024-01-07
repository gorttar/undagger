package atm

import atm.di.components.CommandProcessorComponent
import java.util.Scanner

internal object CommandLineAtm {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        val commandProcessorFactory = CommandProcessorComponent
        val commandProcessor = commandProcessorFactory.processor
        while (scanner.hasNextLine()) {
            @Suppress("UNUSED_VARIABLE") val unused = commandProcessor.process(scanner.nextLine())
        }
    }
}
