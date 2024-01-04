package undagger.atm.modules

import undagger.atm.commands.LoginCommand
import undagger.atm.commands.LoginCommandDependency
import undagger.di.new

class LoginCommandModule(private val dependency: LoginCommandDependency) {
    val command get() = dependency.new(::LoginCommand)
}
