import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import org.koin.compose.KoinContext
import org.koin.compose.getKoin

@Composable
fun BasicDslContainer(
    startRoute: NavKey
) {
    KoinContext {
        val koin = getKoin()

        // 1. Достаем ВСЕ зарегистрированные модули фич из Koin.
        val featureModules = remember { koin.getAll<FeatureNavModule>() }

        // 2. Динамически собираем конфигурацию сериализации из всех фич.
        val appNavConfig = remember(featureModules) {
            val combinedSerializer = featureModules
                .map { it.serializerModule }
                .reduceOrNull { acc, module -> acc + module } ?: SerializersModule { }

            SavedStateConfiguration { serializersModule = combinedSerializer }
        }

        // 3. Инициализируем стек навигации.
        // Передаем конфигурацию и стартовый маршрут как vararg элементы.
        val navBackStack = rememberNavBackStack(
            appNavConfig,
            startRoute // Передаем объект напрямую в vararg
        )

        // 4. Лямбда для кнопки назад, которая безопасно управляет стеком
        val onBackClick = {
            if (navBackStack.size > 1) {
                navBackStack.removeAt(navBackStack.lastIndex)
            }
        }

        // 5. Рендерим граф.
        NavDisplay(
            backStack = navBackStack,
        ) { key ->
            val module = featureModules.firstOrNull { it.canResolve(key) }
                ?: error("Не найден навигационный модуль для маршрута $key")

            // Передаем управление внутрь модуля фичи
            module.resolve(key = key, onBackPressed = onBackClick) as NavEntry<NavKey>
        }
    }
}