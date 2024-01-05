package undagger.atm.di.modules

import undagger.atm.di.exports.OutputterExport
import undagger.atm.io.Outputter

object SystemOutModule : OutputterExport {
    override val outputter: Outputter get() = Outputter(::println)
}