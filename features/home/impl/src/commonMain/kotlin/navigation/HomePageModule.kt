package navigation

import FeatureNavModule
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import presentation.screen.HomePage

class HomePageModule : FeatureNavModule {
    override val serializerModule = SerializersModule {
        polymorphic(NavKey::class) { subclass(HomePageRoute::class, HomePageRoute.serializer()) }
    }

    override fun canResolve(key: NavKey): Boolean = key is HomePageRoute

    override fun resolve(
        key: NavKey,
        onBackPressed: () -> Unit
    ): NavEntry<out NavKey> = NavEntry(key = key as HomePageRoute) {
        HomePage()
    }
}