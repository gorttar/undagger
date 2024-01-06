package undagger.atm

import undagger.atm.di.components.CommandProcessorFactory
import java.util.*

internal object CommandLineAtm {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        val processor = CommandProcessorFactory.processor
        while (scanner.hasNextLine()) {
            @Suppress("UNUSED_VARIABLE") val unused = processor.process(scanner.nextLine())
        }
    }
}
