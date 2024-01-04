package undagger.di

/**
 * [T] - bean type
 * [D] - bean dependency type
 */
inline fun <T, D> D.new(constructor: (D) -> T): T = constructor(this)