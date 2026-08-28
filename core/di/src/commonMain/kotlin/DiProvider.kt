import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import navigation.QrScanModule
import navigation.QrScanScreenRoute
import org.koin.compose.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class DiProvider {
    private val maxMiniAppModule = module {
        single<QrCodeScanner> { createQrCodeScanner() }
    }

    // Создаем модуль навигации, где стэк предоставляется через Koin
    private val navigationModule = module {
        // Регистрируем модули фич
        single<FeatureNavModule> { QrScanModule() }
    }

    @Composable
    fun MainKoinApplication() {
        // Вместо KoinApplication лучше использовать стандартный старт,
        // это гарантирует, что граф Koin готов ДО начала работы Compose UI.
        remember {
            startKoin {
                modules(maxMiniAppModule, navigationModule)
            }
        }

        BasicDslContainer(startRoute = QrScanScreenRoute)
    }
}