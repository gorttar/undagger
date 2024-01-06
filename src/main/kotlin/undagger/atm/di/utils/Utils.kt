package undagger.atm.di.utils

import kotlin.properties.ReadOnlyProperty

/**
 * Create [new] bean of type [T] using its [constructor] and [this] bean import of type [D]
 */
inline fun <D, T> D.new(constructor: D.() -> T): T = constructor()

/**
 * Create [new] independent bean of type [T] using its [constructor]
 */
inline fun <T> new(constructor: () -> T): T = constructor()

/**
 * Create [perRequest] delegate for bean of type [T] using its [constructor] and [this] bean import of type [D]
 * Created delegate invokes [constructor] for each invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <D, T> D.perRequest(crossinline constructor: D.() -> T): ReadOnlyProperty<Any?, T> =
    ReadOnlyProperty { _, _ -> constructor() }

/**
 * Create [perRequest] delegate for independent bean of type [T] using its [constructor]
 * Created delegate invokes [constructor] for each invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <T> perRequest(crossinline constructor: () -> T): ReadOnlyProperty<Any?, T> =
    Unit.perRequest { constructor() }

/**
 * Create [perComponent] delegate for bean of type [T] using its [constructor] and [this] bean import of type [D]
 * Created delegate invokes [constructor] once during first invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <T, D> D.perComponent(crossinline constructor: D.() -> T): ReadOnlyProperty<Any?, T> =
    ReadOnlyProperty(lazy { constructor() }::getValue)

/**
 * Create [perComponent] delegate for independent bean of type [T] using its [constructor]
 * Created delegate invokes [constructor] once during first invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <T> perComponent(crossinline constructor: () -> T): ReadOnlyProperty<Any?, T> =
    Unit.perComponent { constructor() }
