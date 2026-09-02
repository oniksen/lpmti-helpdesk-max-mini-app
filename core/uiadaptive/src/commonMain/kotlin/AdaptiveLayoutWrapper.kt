import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

@Composable
fun<T> AdaptiveLayoutWrapper(
    state: T,
    compact: @Composable T.() -> Unit,
    medium: (@Composable T.() -> Unit)? = null,
    expanded: @Composable T.() -> Unit = medium ?: compact,
) {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    when {
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_LARGE_LOWER_BOUND) ->
            expanded(state)
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ->
            (medium ?: compact)(state)
        else ->
            compact(state)
    }
}