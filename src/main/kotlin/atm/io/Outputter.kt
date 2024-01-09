package atm.io

/** Writes to a user interface. */
fun interface Outputter {
    fun output(output: String)
}