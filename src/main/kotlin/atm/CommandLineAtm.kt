package atm

import atm.di.components.DaggerCommandProcessorComponent
import java.util.Scanner

/** Entry point for the command-line ATM. */
fun main() {
    val processor = DaggerCommandProcessorComponent.create().processor
    Scanner(System.`in`)
        .run { generateSequence { if (hasNextLine()) nextLine() else null } }
        .forEach(processor::process)
}
