package atm.di

import atm.commands.Command
import atm.commands.DepositCommand
import atm.commands.HelloWorldCommand
import atm.commands.LoginCommand
import atm.commands.LogoutCommand
import atm.commands.WithdrawCommand
import atm.data.Database
import atm.data.Database.Account
import atm.di.components.UserCommandsComponent
import atm.io.Outputter
import dagger.Binds
import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.math.BigDecimal

/** Bindings for the [Account] of the currently signed-in user.  */
@Module
interface AccountModule {
    companion object {
        @Provides
        fun account(database: Database, @Username username: String): Account = database.getAccount(username)
    }
}

/** Configures various amounts of money the application uses to control transactions. */
@Module
interface AmountsModule {
    companion object {
        @Provides
        @MinimumBalance
        fun minimumBalance(): BigDecimal = BigDecimal.ZERO

        @Provides
        @MaximumWithdrawal
        fun maximumWithdrawal(): BigDecimal = 1000.toBigDecimal()
    }
}

/** Installs basic commands.  */
@Module
interface CommonCommandsModule {
    @Binds
    @IntoMap
    @StringKey("hello")
    fun helloWorld(command: HelloWorldCommand): Command

    @Binds
    @IntoMap
    @StringKey("login")
    fun login(command: LoginCommand): Command

    /**
     * Declare an optional binding for [Account]. This allows other bindings to change their
     * behavior depending on whether an [Account] is bound in the current (sub)component.
     */
    @BindsOptionalOf
    fun loggedInAccount(): Account?

}

@Module
object SystemOutModule {
    @Provides
    fun textOutputter(): Outputter = Outputter(::println)
}

@Module(subcomponents = [UserCommandsComponent::class])
interface UserCommandsComponentModule

/** Commands that are only applicable when a user is logged in. */
@Module
interface UserCommandsModule {
    @Binds
    @IntoMap
    @StringKey("deposit")
    fun depositCommand(command: DepositCommand): Command

    @Binds
    @IntoMap
    @StringKey("withdraw")
    fun withdrawCommand(command: WithdrawCommand): Command

    @Binds
    @IntoMap
    @StringKey("logout")
    fun logoutCommand(command: LogoutCommand): Command
}
