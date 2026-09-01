import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

@Composable
internal fun AdaptiveNavigationContainer(
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
    content: @Composable () -> Unit
) {
    val windowsSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass

    when {
        windowsSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_LARGE_LOWER_BOUND) ->
            LargeScreen(
                content = content,
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged,
            )
        windowsSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) ->
            MediumScreen(
                expanded = true, content = content,
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged,
            )
        windowsSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ->
            MediumScreen(
                expanded = false, content = content,
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged,
            )
        else ->
            SmallScreen(
                content = content,
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged,
            )
    }
}

@Composable
private fun LargeScreen(
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
    content: @Composable () -> Unit
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.width(240.dp),
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight(),
                ) {
                    AppDestination.entries.forEach { entry ->
                        NavigationDrawerItem(
                            label = { Text(text = entry.label) },
                            selected = currentDestination == entry,
                            onClick = { onDestinationChanged(entry) },
                            icon = { Icon(entry.icon, contentDescription = null) },
                        )
                    }
                }
            }
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            content()
        }
    }
}

@Composable
private fun MediumScreen(
    expanded: Boolean,
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
    content: @Composable () -> Unit
) {
    val railState = rememberWideNavigationRailState(
        if (expanded) WideNavigationRailValue.Expanded
        else WideNavigationRailValue.Collapsed
    )

    Row(Modifier.fillMaxSize()) {
        WideNavigationRail(
            state = railState,
        ) {
            AppDestination.entries.forEach { entry ->
                NavigationRailItem(
                    selected = currentDestination == entry,
                    onClick = { onDestinationChanged(entry) },
                    icon = {
                        Icon(entry.icon, contentDescription = null)
                    },
                    label = { Text(text = entry.label) },
                    alwaysShowLabel = true,
                )
            }
        }
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) { content() }
    }
}

@Composable
private fun SmallScreen(
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                AppDestination.entries.forEach { entry ->
                    NavigationBarItem(
                        selected = currentDestination == entry,
                        onClick = { onDestinationChanged(entry) },
                        icon = {
                            Icon(entry.icon, contentDescription = null)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) { content() }
    }
}