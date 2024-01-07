package undagger.atm.commands

import undagger.atm.data.Database.Account
import undagger.atm.di.Import
import undagger.atm.di.exports.OutputterExport

interface UserCommandImport : OutputterExport, Import {
    val account: Account
}
