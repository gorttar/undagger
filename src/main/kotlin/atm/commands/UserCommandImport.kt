package atm.commands

import atm.data.Database.Account
import atm.di.exports.AccountExport
import atm.di.exports.OutputterExport
import atm.di.exports.WithdrawalLimiterExport

interface UserCommandImport : OutputterExport, AccountExport, WithdrawalLimiterExport {
    override val account: Account
}
