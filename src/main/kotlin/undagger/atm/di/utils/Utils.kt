package undagger.atm.di.utils

import undagger.atm.di.Bean
import undagger.atm.di.BeanHolder
import undagger.atm.di.Import
import kotlin.properties.ReadOnlyProperty

/**
 * [invoke] operator for [Import] of type [Imp] represented by [this] on [block]
 * effectively applying [block] to [this] evaluating it to [R]
 */
inline operator fun <Imp : Import, R> Imp.invoke(block: Imp.() -> R): R = run(block)

/**
 * Create [new] instance of [Bean] with type [B] using its [constructor] and [Import] of type [Imp]
 * represented by [this]
 */
inline fun <Imp : Import, B : Bean> Imp.new(constructor: Imp.() -> B): B = this(constructor)

/**
 * Make [constructor] for [Bean] of type [B] formally [dependent] from [Import] of type [Imp]
 */
inline fun <Imp : Import, B : Bean> dependent(crossinline constructor: () -> B): Imp.() -> B = { constructor() }

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

private val nothing: Nothing get() = error("Nothing can't be obtained")

val <V> OwnerlessReadOnlyProperty<V>.value: V get() = getValue(null, ::nothing)

/**
 * [perRequest] creates delegate for [Bean] type [B] using its [constructor] and [Import] of type [Imp]
 * represented by [this]
 * Created delegate calls [constructor] for each invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Imp : Import, B : Bean> Imp.perRequest(
    crossinline constructor: Imp.() -> B
): OwnerlessReadOnlyProperty<B> = OwnerlessReadOnlyProperty { new(constructor) }

/**
 * [perRequest] creates [BeanHolder]<[String], [B]> which values are evaluated by corresponding delegates
 * for [Bean] type [B]
 * Delegates are created using constructors from [keyToConstructor] and [Import] of type [Imp] represented by [this]
 * Created delegates invokes constructors for each invocation of their [ReadOnlyProperty.getValue]
 */
fun <Imp : Import, B : Bean> Imp.perRequest(
    vararg keyToConstructor: Pair<String, Imp.() -> B>
): BeanHolder<String, B> =
    BeanHolder(*keyToConstructor.map { (k, constructor) -> k to perRequest(constructor) }.toTypedArray())

/**
 * [perComponent] creates delegate for [Bean] of type [B] using its [constructor] and [Import] of type [Imp]
 *  * represented by [this]
 * Created delegate invokes [constructor] once during first invocation of its [ReadOnlyProperty.getValue]
 */
inline fun <Imp : Import, B : Bean> Imp.perComponent(
    crossinline constructor: Imp.() -> B
): OwnerlessReadOnlyProperty<B> = OwnerlessReadOnlyProperty(lazy { new(constructor) }::value)

/**
 * [perComponent] creates [BeanHolder]<[String], [B]> which values are evaluated by corresponding delegates
 * for [Bean] of type [B]
 * Delegates are created using constructors from [keyToConstructor] and [Import] of type [Imp] represented by [this]
 * Created delegates invokes constructors once during first invocation of their [ReadOnlyProperty.getValue]
 */
fun <Imp : Import, B : Bean> Imp.perComponent(
    vararg keyToConstructor: Pair<String, Imp.() -> B>
): BeanHolder<String, B> =
    BeanHolder(*keyToConstructor.map { (k, constructor) -> k to perComponent(constructor) }.toTypedArray())
