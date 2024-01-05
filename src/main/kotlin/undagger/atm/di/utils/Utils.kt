package undagger.atm.di.utils

import kotlin.properties.ReadOnlyProperty

/**
 * Create [new] bean of type [T] using its [constructor] and [this] bean import of type [D]
 */
inline fun <T, D> D.new(constructor: (D) -> T): T = constructor(this)

/**
 * Create [perRequest] delegate for bean of type [T] using its [constructor] and [this] bean import of type [D]
 * Created delegate invokes [constructor] for each invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <T, D> D.perRequest(crossinline constructor: (D) -> T): ReadOnlyProperty<Any?, T> =
    ReadOnlyProperty { _, _ -> new(constructor) }

/**
 * Create [scoped] delegate for bean of type [T] using its [constructor] and [this] bean import of type [D]
 * Created delegate invokes [constructor] once during first invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <T, D> D.scoped(crossinline constructor: (D) -> T): ReadOnlyProperty<Any?, T> =
    ReadOnlyProperty(lazy { new(constructor) }::getValue)