package undagger.atm.di.modules

import undagger.atm.commands.LoginCommand
import undagger.atm.commands.LoginCommandImport
import undagger.atm.di.utils.perRequest

class LoginCommandModule(import: LoginCommandImport) {
    val command by import.perRequest(::LoginCommand)
}
