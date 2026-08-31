import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import navigation.HomePageModule
import navigation.HomePageRoute
import navigation.QrScanModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class DiProvider {
    private val maxMiniAppModule = module {
        single<QrCodeScanner> { createQrCodeScanner() }
    }

    // Создаем модуль навигации, где стэк предоставляется через Koin
    private val navigationModule = module {
        // Регистрируем модули фич
        // Инициализация должна проводиться таким образом, иначе следующий фича-модуль перезапишет предыдущий.
        single { HomePageModule() } bind FeatureNavModule::class
        single { QrScanModule() } bind FeatureNavModule::class
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

        BasicDslContainer(startRoute = HomePageRoute)
    }
}