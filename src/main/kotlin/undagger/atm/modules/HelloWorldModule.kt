package undagger.atm.modules

import undagger.atm.commands.HelloWorldCommand
import undagger.atm.commands.HelloWorldCommandDependency
import undagger.di.new

class HelloWorldModule(private val dependency: HelloWorldCommandDependency) {
    val command get() = dependency.new(::HelloWorldCommand)
}
