package undagger.atm.commands

import undagger.atm.data.Database.Account
import undagger.atm.di.exports.OutputterExport

interface UserCommandImport : OutputterExport {
    val account: Account
}
