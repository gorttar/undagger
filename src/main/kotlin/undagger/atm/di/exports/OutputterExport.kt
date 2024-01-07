package undagger.atm.di.exports

import undagger.atm.di.Import
import undagger.atm.io.Outputter

interface OutputterExport : Import {
    val outputter: Outputter get() = Outputter(::println)
}