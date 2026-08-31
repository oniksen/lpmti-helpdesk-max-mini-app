package navigation

import AppNavigator
import FeatureNavModule
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import presentation.screen.QrScannerScreen

class QrScanModule : FeatureNavModule {
    override val serializerModule = SerializersModule {
        polymorphic(NavKey::class) { subclass(QrScanScreenRoute::class, QrScanScreenRoute.serializer()) }
    }

    override fun canResolve(key: NavKey): Boolean = key is QrScanScreenRoute

    override fun resolve(
        key: NavKey,
        navigator: AppNavigator
    ): NavEntry<out NavKey> = NavEntry(key = key as QrScanScreenRoute) {
        QrScannerScreen()
    }
}