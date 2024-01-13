package atm.di

import atm.commands.DepositCommandImport
import atm.commands.LoginCommandImport
import atm.commands.LogoutCommandImport
import atm.commands.NestedLoginCommandImport
import atm.commands.WithdrawCommandImport

interface CommonCommandsImports : LoginCommandImport, OutputterExport
interface UserCommandsImports :
    DepositCommandImport,
    WithdrawCommandImport,
    LogoutCommandImport,
    NestedLoginCommandImport
