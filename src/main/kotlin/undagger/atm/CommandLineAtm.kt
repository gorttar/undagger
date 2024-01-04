package undagger.atm

import java.util.Scanner

internal object CommandLineAtm {
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        val commandRouter = CommandRouterFactory.router
        while (scanner.hasNextLine()) {
            val unused = commandRouter.route(scanner.nextLine()).status()
        }
    }
}