package undagger.atm.di.utils

import kotlin.properties.ReadOnlyProperty

/**
 * Create [new] [Bean] using its [constructor] and [this] [Import]
 */
inline fun <Import, Bean> Import.new(constructor: Import.() -> Bean): Bean = constructor()

/**
 * Create [new] independent [Bean] using its [constructor]
 */
inline fun <Bean> new(constructor: () -> Bean): Bean = constructor()

/**
 * Base interface that can be used for implementing owner and property independent (ownerless)
 * property delegates of read-only properties.
 *
 * This is provided only for convenience; you don't have to extend this interface
 * as long as your property delegate has methods with the same signatures.
 *
 * @param V the type of the property value.
 * @see ReadOnlyProperty
 */
typealias OwnerlessReadOnlyProperty<V> = ReadOnlyProperty<Any?, V>

inline fun <V> OwnerlessReadOnlyProperty(crossinline block: () -> V): OwnerlessReadOnlyProperty<V> =
    OwnerlessReadOnlyProperty { _, _ -> block() }

/**
 * Create [perRequest] delegate for [Bean] using its [constructor] and [this] [Import]
 * Created delegate invokes [constructor] for each invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Import, Bean> Import.perRequest(
    crossinline constructor: Import.() -> Bean
): OwnerlessReadOnlyProperty<Bean> = OwnerlessReadOnlyProperty { constructor() }

/**
 * Create [perRequest] delegate for independent [Bean] using its [constructor]
 * Created delegate invokes [constructor] for each invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Bean> perRequest(
    crossinline constructor: () -> Bean
): OwnerlessReadOnlyProperty<Bean> = Unit.perRequest { constructor() }

/**
 * Create [perComponent] delegate [Bean] using its [constructor] and [this] [Import]
 * Created delegate invokes [constructor] once during first invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Import, Bean> Import.perComponent(
    crossinline constructor: Import.() -> Bean
): OwnerlessReadOnlyProperty<Bean> = OwnerlessReadOnlyProperty(lazy { constructor() }::value)

/**
 * Create [perComponent] delegate for independent [Bean] using its [constructor]
 * Created delegate invokes [constructor] once during first invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Bean> perComponent(
    crossinline constructor: () -> Bean
): OwnerlessReadOnlyProperty<Bean> = Unit.perComponent { constructor() }
