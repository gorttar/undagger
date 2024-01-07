package atm.di.exports

import atm.data.Database

interface AccountExport {
    val account: Database.Account? get() = null
}
