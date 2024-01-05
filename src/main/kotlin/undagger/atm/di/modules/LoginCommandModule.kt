package undagger.atm.di.modules

import undagger.atm.commands.LoginCommand
import undagger.atm.commands.LoginCommandImport
import undagger.atm.di.utils.new

class LoginCommandModule(private val dependency: LoginCommandImport) {
    val command get() = dependency.new(::LoginCommand)
}
