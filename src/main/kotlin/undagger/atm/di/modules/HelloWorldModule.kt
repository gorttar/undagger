package undagger.atm.di.modules

import undagger.atm.commands.HelloWorldCommand
import undagger.atm.commands.HelloWorldCommandImport
import undagger.atm.di.utils.perRequest

class HelloWorldModule(import: HelloWorldCommandImport) {
    val command by import.perRequest(::HelloWorldCommand)
}
