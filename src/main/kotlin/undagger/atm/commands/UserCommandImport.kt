package undagger.atm.commands

import undagger.atm.data.Database.Account
import undagger.atm.di.exports.AccountExport
import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.exports.WithdrawalLimiterExport

interface UserCommandImport : OutputterExport, AccountExport, WithdrawalLimiterExport {
    override val account: Account
}
