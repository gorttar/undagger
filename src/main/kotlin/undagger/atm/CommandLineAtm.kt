package undagger.atm

import undagger.atm.di.components.CommandRouterFactory
import java.util.Scanner

internal object CommandLineAtm {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        val commandRouter = CommandRouterFactory.router
        while (scanner.hasNextLine()) {
            @Suppress("UNUSED_VARIABLE") val unused = commandRouter.route(scanner.nextLine()).status()
        }
    }
}