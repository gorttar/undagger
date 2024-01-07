package undagger.atm.di

import undagger.atm.di.utils.OwnerlessReadOnlyProperty
import undagger.atm.di.utils.value
import java.util.AbstractMap.SimpleImmutableEntry

class BeanMap<B : Bean> private constructor(
    private val _entries: EntrySet<B> // you can't override entries directly due to probable compiler bug
) : AbstractMap<String, B>() {
    class EntrySet<B : Bean> internal constructor(
        private val delegate: Set<Pair<String, OwnerlessReadOnlyProperty<B>>>
    ) : AbstractSet<Map.Entry<String, B>>() {
        override val size: Int by delegate::size
        override fun iterator(): Iterator<Map.Entry<String, B>> = delegate.asSequence().map { (k, delegate) ->
            SimpleImmutableEntry(k, delegate.value)
        }.iterator()

        operator fun plus(elements: EntrySet<B>): EntrySet<B> = EntrySet(delegate + elements.delegate)

    }

    override val entries: Set<Map.Entry<String, B>> by ::_entries
    operator fun plus(map: BeanMap<B>): BeanMap<B> = BeanMap(_entries + map._entries)

    companion object {
        fun <B : Bean> beanMapOf(
            vararg entries: Pair<String, OwnerlessReadOnlyProperty<B>>
        ): BeanMap<B> = BeanMap(EntrySet(entries.toSet()))
    }
}
