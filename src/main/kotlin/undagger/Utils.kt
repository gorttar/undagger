package undagger

import undagger.BeanHolder.Companion.beans
import kotlin.collections.Map.Entry
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * [Map] like [Bean] holder which obtains its [beans] lazily
 * [BeanHolder] overrides [toString] its own way thus ensuring laziness in case of printing it
 */
class BeanHolder<out Key, out Bean> private constructor(
    private val delegates: Map<out Key, Delegate<Bean>>
) {
    constructor(vararg delegates: Pair<Key, Delegate<Bean>>) : this(mapOf(*delegates))

    override fun toString(): String = delegates.entries.joinToString(
        prefix = "BeanHolder {",
        separator = ", ",
        postfix = "}"
    ) { (key, delegate) -> "${if (key is CharSequence) "\"$key\"" else "$key"} <- $delegate" }

    companion object {
        val <Key, Bean> BeanHolder<Key, Bean>.entries: Sequence<Entry<Key, Delegate<Bean>>>
            get() = delegates.asSequence()

        val <Key, Bean> BeanHolder<Key, Bean>.bindings: Sequence<Pair<Key, Bean>>
            get() = entries.map { (k, delegate) -> k to delegate() }

        val <Bean> BeanHolder<*, Bean>.beans: Sequence<Bean> get() = bindings.map { it.second }

        /**
         * Evaluates [Bean] bound to [key] if there is corresponding [Delegate] in [delegates]
         */
        operator fun <Key, Bean> BeanHolder<Key, Bean>.get(key: Key) = delegates[key]?.invoke()

        /**
         * Checks if [BeanHolder] represented by [this] contains th given [key]
         */
        operator fun <Key> BeanHolder<Key, *>.contains(key: Key) = key in delegates

        /**
         * Creates a new [BeanHolder] by replacing or adding entries from [other] to [this].
         * Those entries of [other] that are missing in [this] are iterated in the end in the order of [other]
         */
        operator fun <Key, Bean> BeanHolder<Key, Bean>.plus(
            other: BeanHolder<Key, Bean>
        ) = BeanHolder(delegates + other.delegates)
    }
}

/**
 * Owner and property independent implementation of [ReadOnlyProperty]
 *
 * @param V the return type of the [Delegate]
 * @see ReadOnlyProperty
 */
class Delegate<out V> @PublishedApi internal constructor(
    private val vType: KType,
    private val blockName: String,
    private val block: () -> V
) : ReadOnlyProperty<Any?, V>, () -> V by block {
    override fun getValue(thisRef: Any?, property: KProperty<*>): V = this()
    override fun toString(): String = "Delegate<$vType>($blockName)"

    companion object {
        /**
         * Constructor of [Delegate]<[V]> with pretty [toString]
         * @param V the return type of the [Delegate]
         * @param blockName the optional name of wrapped [block]
         * @param block the function wrapped into the [Delegate]
         */
        inline operator fun <reified V> invoke(blockName: String, noinline block: () -> V): Delegate<V> =
            Delegate(typeOf<V>(), blockName, block)
    }
}

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
 * Make [constructor] for [Bean] named as [constructorName] formally [dependent] from [Import]
 */
fun <Import, Bean> dependent(constructorName: String, constructor: () -> Bean): (Import) -> Bean =
    object : (Import) -> Bean by { constructor() } {
        override fun toString(): String = constructorName
    }

/**
 * Make [constructor] for [Bean] formally [dependent] from [Import]
 */
fun <Import, Bean> dependent(constructor: () -> Bean): (Import) -> Bean = dependent("$constructor", constructor)

/**
 * [perRequest] creates [Delegate] for [Bean] using its [constructor] named as [constructorName] and [Import]
 * represented by [this]
 * Created [Delegate] calls [constructor] for each invocation
 */
inline fun <Import, reified Bean> Import.perRequest(
    constructorName: String,
    noinline constructor: (Import) -> Bean
): Delegate<Bean> = Delegate(constructorName) { new(constructor) }

/**
 * [perRequest] creates [Delegate] for [Bean] using its [constructor] and [Import]
 * represented by [this]
 * Created [Delegate] calls [constructor] for each invocation
 */
inline fun <Import, reified Bean> Import.perRequest(noinline constructor: (Import) -> Bean): Delegate<Bean> =
    perRequest("$constructor", constructor)

/**
 * [perRequest] creates [BeanHolder]<[Key], [Bean]> which values are evaluated by corresponding [Delegate]s.
 * [Delegate]s are created using constructors from [keyToConstructor] and [Import] represented by [this]
 * Created [Delegate]s invokes constructors for each invocation
 */
inline fun <Import, Key, reified Bean> Import.perRequest(
    vararg keyToConstructor: Pair<Key, (Import) -> Bean>
): BeanHolder<Key, Bean> =
    BeanHolder(*keyToConstructor.map { (k, constructor) -> k to perRequest(constructor) }.toTypedArray())

/**
 * [perComponent] creates [Delegate] for [Bean] using its [constructor] named as [constructorName] and [Import]
 *  * represented by [this]
 * Created [Delegate] invokes [constructor] once during first invocation
 */
inline fun <Import, reified Bean> Import.perComponent(
    constructorName: String,
    noinline constructor: (Import) -> Bean
): Delegate<Bean> = Delegate(constructorName, lazy { new(constructor) }::value)

/**
 * [perComponent] creates [Delegate] for [Bean] using its [constructor] and [Import]
 *  * represented by [this]
 * Created [Delegate] invokes [constructor] once during first invocation
 */
inline fun <Import, reified Bean> Import.perComponent(noinline constructor: (Import) -> Bean): Delegate<Bean> =
    perComponent("$constructor", constructor)

/**
 * [perComponent] creates [BeanHolder]<[Key], [Bean]> which values are evaluated by corresponding [Delegate]s.
 * [Delegate]s are created using constructors from [keyToConstructor] and [Import] represented by [this]
 * Created [Delegate]s invokes constructors once during first invocation
 */
inline fun <Import, Key, reified Bean> Import.perComponent(
    vararg keyToConstructor: Pair<Key, (Import) -> Bean>
): BeanHolder<Key, Bean> =
    BeanHolder(*keyToConstructor.map { (k, constructor) -> k to perComponent(constructor) }.toTypedArray())
