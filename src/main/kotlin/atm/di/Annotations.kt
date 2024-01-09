package atm.di

import javax.inject.Qualifier
import javax.inject.Scope

/** A scope for instances that should be retained within a user session. */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class PerSession

/** Qualifier for the maximum amount that can be withdrawn from an account in a single transaction. */
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class MaximumWithdrawal

/** Qualifier for the minimum balance an account may have. */
@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class MinimumBalance

/** Qualifier for the currently logged-in user.  */
@Qualifier
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Username
