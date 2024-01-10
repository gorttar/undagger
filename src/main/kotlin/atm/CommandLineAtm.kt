package atm

import atm.di.components.commandProcessorComponent
import java.util.Scanner

/** Entry point for the command-line ATM. */
fun main() {
    val scanner = Scanner(System.`in`)
    val commandProcessor = commandProcessorComponent.processor
    while (scanner.hasNextLine()) commandProcessor.process(scanner.nextLine())
}
