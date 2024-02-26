package atm

import atm.di.components.DaggerCommandProcessorComponent
import java.io.InputStreamReader

/** Entry point for the command-line ATM. */
fun main() {
    val processor = DaggerCommandProcessorComponent.create().processor
    System.`in`.let(::InputStreamReader)
        .useLines { it.forEach(processor::process) }
}
