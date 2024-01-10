package atm.commands

import atm.di.OutputterExport
import undagger.invoke
import java.math.BigDecimal


/**
 * Abstract [Command] expecting a single argument that can be converted to [BigDecimal].
 */
abstract class BigDecimalCommand protected constructor(private val import: OutputterExport) : SingleArgCommand() {
    override fun handleArg(arg: String): Command.Result = import {
        val amount = tryParse(arg)
        when {
            amount == null -> outputter.output("$arg is not a valid number")
            amount <= BigDecimal.ZERO -> outputter.output("amount must be positive")
            else -> handleAmount(amount)
        }
        return Command.Result.handled
    }

    /** Handles the given (positive) `amount` of money.  */
    protected abstract fun handleAmount(amount: BigDecimal)

    companion object {
        private fun tryParse(arg: String): BigDecimal? = runCatching { BigDecimal(arg) }
            .onFailure { if (it !is NumberFormatException) throw it }
            .getOrNull()
    }
}
