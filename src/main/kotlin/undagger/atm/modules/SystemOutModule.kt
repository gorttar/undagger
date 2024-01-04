package undagger.atm.modules

import undagger.atm.io.Outputter

interface OutputterRequirement {
    val outputter: Outputter
}

object SystemOutModule : OutputterRequirement {
    override val outputter: Outputter get() = Outputter(::println)
}