package undagger.atm.di

import undagger.atm.di.utils.OwnerlessReadOnlyProperty
import undagger.atm.di.utils.value

class BeanHolder<K, out B : Bean> private constructor(private val delegate: Map<K, OwnerlessReadOnlyProperty<B>>) {
    constructor(vararg entries: Pair<K, OwnerlessReadOnlyProperty<B>>) : this(mapOf(*entries))

    operator fun get(k: K): B? = delegate[k]?.value
    operator fun contains(k: K): Boolean = k in delegate

    companion object {
        operator fun <K, B : Bean> BeanHolder<out K, B>.plus(
            other: BeanHolder<out K, B>
        ) = BeanHolder(delegate + other.delegate)
    }
}
