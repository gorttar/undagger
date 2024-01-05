package undagger.atm.di.modules

import undagger.atm.commands.HelloWorldCommand
import undagger.atm.commands.HelloWorldCommandImport
import undagger.atm.di.utils.new

class HelloWorldModule(private val dependency: HelloWorldCommandImport) {
    val command get() = dependency.new(::HelloWorldCommand)
}
