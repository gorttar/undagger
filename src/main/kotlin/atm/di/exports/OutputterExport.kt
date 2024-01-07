package atm.di.exports

import atm.io.Outputter

interface OutputterExport {
    val outputter: Outputter get() = Outputter(::println)
}
