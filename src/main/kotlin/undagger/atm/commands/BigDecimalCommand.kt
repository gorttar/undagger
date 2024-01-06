package undagger.atm.commands

import undagger.atm.commands.Command.Result
import undagger.atm.di.exports.OutputterExport
import java.math.BigDecimal


/**
 * Abstract [Command] expecting a single argument that can be converted to [BigDecimal].
 */
abstract class BigDecimalCommand protected constructor(private val import: OutputterExport) : SingleArgCommand() {
    override fun handleArg(arg: String): Result = with(import) {
        val amount = tryParse(arg)
        when {
            amount == null -> outputter.output("$arg is not a valid number")
            amount <= BigDecimal.ZERO -> outputter.output("amount must be positive")
            else -> handleAmount(amount)
        }
        Result.handled()
    }

    /** Handles the given (positive) `amount` of money.  */
    protected abstract fun handleAmount(amount: BigDecimal)

    private companion object {
        fun tryParse(arg: String): BigDecimal? = runCatching { BigDecimal(arg) }.getOrNull()
    }
}