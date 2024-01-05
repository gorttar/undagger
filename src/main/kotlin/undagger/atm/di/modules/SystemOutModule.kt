package undagger.atm.di.modules

import undagger.atm.di.exports.OutputterExport
import undagger.atm.di.utils.perRequest
import undagger.atm.io.Outputter

object SystemOutModule : OutputterExport {
    override val outputter: Outputter by perRequest { Outputter(::println) }
}