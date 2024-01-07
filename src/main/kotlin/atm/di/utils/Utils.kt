package atm.di.utils

import atm.di.BeanHolder
import kotlin.properties.ReadOnlyProperty

/**
 * [invoke] operator for [Import] represented by [this] on [block]
 * effectively applying [block] to [this] evaluating it to [R]
 */
inline operator fun <Import, R> Import.invoke(block: Import.() -> R): R = run(block)

/**
 * Create [new] instance of [Bean] using its [constructor] and [Import]
 * represented by [this]
 */
inline fun <Import, Bean> Import.new(constructor: (Import) -> Bean): Bean = this(constructor)

/**
 * Make [constructor] for [Bean] formally [dependent] from [Import]
 */
inline fun <Import, Bean> dependent(crossinline constructor: () -> Bean): (Import) -> Bean = { constructor() }

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

/**
 * constructor of [OwnerlessReadOnlyProperty]<[V]>
 */
inline fun <V> OwnerlessReadOnlyProperty(
    crossinline block: () -> V
): OwnerlessReadOnlyProperty<V> = OwnerlessReadOnlyProperty { _, _ -> block() }

private val undefined: Nothing get() = error("Nothing can't be obtained")

val <V> OwnerlessReadOnlyProperty<V>.value: V get() = getValue(null, ::undefined)

/**
 * [perRequest] creates delegate for [Bean] using its [constructor] and [Import]
 * represented by [this]
 * Created delegate calls [constructor] for each invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Import, Bean> Import.perRequest(
    crossinline constructor: (Import) -> Bean
): OwnerlessReadOnlyProperty<Bean> = OwnerlessReadOnlyProperty { new(constructor) }

/**
 * [perRequest] creates [BeanHolder]<[Key], [Bean]> which values are evaluated by corresponding delegates.
 * Delegates are created using constructors from [keyToConstructor] and [Import] represented by [this]
 * Created delegates invokes constructors for each invocation of their [ReadOnlyProperty.getValue]
 */
fun <Import, Key, Bean> Import.perRequest(
    vararg keyToConstructor: Pair<Key, (Import) -> Bean>
): BeanHolder<Key, Bean> =
    BeanHolder(*keyToConstructor.map { (k, constructor) -> k to perRequest(constructor) }.toTypedArray())

/**
 * [perComponent] creates delegate for [Bean] using its [constructor] and [Import]
 *  * represented by [this]
 * Created delegate invokes [constructor] once during first invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Import, Bean> Import.perComponent(
    crossinline constructor: (Import) -> Bean
): OwnerlessReadOnlyProperty<Bean> = OwnerlessReadOnlyProperty(lazy { new(constructor) }::value)

/**
 * [perComponent] creates [BeanHolder]<[Key], [Bean]> which values are evaluated by corresponding delegates.
 * Delegates are created using constructors from [keyToConstructor] and [Import] represented by [this]
 * Created delegates invokes constructors once during first invocation of their [ReadOnlyProperty.getValue]
 */
fun <Import, Key, Bean> Import.perComponent(
    vararg keyToConstructor: Pair<Key, (Import) -> Bean>
): BeanHolder<Key, Bean> =
    BeanHolder(*keyToConstructor.map { (k, constructor) -> k to perComponent(constructor) }.toTypedArray())
