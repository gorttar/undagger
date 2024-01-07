package atm.di

import atm.di.utils.OwnerlessReadOnlyProperty
import atm.di.utils.value

class BeanHolder<Key, out Bean> private constructor(
    private val delegate: Map<Key, OwnerlessReadOnlyProperty<Bean>>
) : Sequence<Pair<Key, Bean>> by (delegate.asSequence().map { (k, prop) -> k to prop.value }) {
    constructor(vararg entries: Pair<Key, OwnerlessReadOnlyProperty<Bean>>) : this(mapOf(*entries))

    operator fun get(k: Key): Bean? = delegate[k]?.value
    operator fun contains(k: Key): Boolean = k in delegate

    companion object {
        operator fun <K, Bean> BeanHolder<out K, Bean>.plus(
            other: BeanHolder<out K, Bean>
        ) = BeanHolder(delegate + other.delegate)
    }
}
