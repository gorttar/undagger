package atm

import atm.di.components.commandProcessorComponent
import java.io.InputStreamReader

/** Entry point for the command-line ATM. */
fun main() {
    val processor = commandProcessorComponent().processor
    System.`in`.let(::InputStreamReader)
        .useLines { it.forEach(processor::process) }
}
