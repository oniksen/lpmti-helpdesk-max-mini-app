import androidx.compose.runtime.Composable
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class DiProvider {
    private val appModules = listOf<Module>()

    fun provideModules(list: List<Module>) {
        appModules.addAll(list)
    }

    @Composable
    fun MineKoinApplication(
        content: @Composable () -> Unit
    ) {
        startKoin {
            modules(appModules)
        }

        content()
    }
}