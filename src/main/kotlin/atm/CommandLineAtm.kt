package atm

import atm.di.components.DaggerCommandProcessorComponent
import java.util.Scanner

/** Entry point for the command-line ATM. */
fun main() {
    val scanner = Scanner(System.`in`)
    val commandProcessorFactory = DaggerCommandProcessorComponent.create()
    val commandProcessor = commandProcessorFactory.commandProcessor
    while (scanner.hasNextLine()) commandProcessor.process(scanner.nextLine())
}
