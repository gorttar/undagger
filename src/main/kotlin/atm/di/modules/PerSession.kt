package atm.di.modules

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.SOURCE


@Scope
@MustBeDocumented
@Retention(SOURCE)
annotation class PerSession
