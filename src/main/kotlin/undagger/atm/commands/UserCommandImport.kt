package undagger.atm.commands

import undagger.atm.data.Database
import undagger.atm.di.exports.OutputterExport

interface UserCommandImport : OutputterExport {
    val account: Database.Account
}
