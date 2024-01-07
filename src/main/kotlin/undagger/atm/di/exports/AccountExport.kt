package undagger.atm.di.exports

import undagger.atm.data.Database

interface AccountExport {
    val account: Database.Account? get() = null
}
