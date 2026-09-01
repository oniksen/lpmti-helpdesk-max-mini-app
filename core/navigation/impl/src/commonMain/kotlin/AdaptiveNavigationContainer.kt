import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun AdaptiveNavigationContainer(
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
    content: @Composable () -> Unit
) {
    AdaptiveLayoutWrapper(
        state = content,
        compact = {
            SmallScreen(
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged,
            )
        },
        medium = {
            MediumScreen(
                expanded = false,
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged,
            )
        },
        expanded = {
            LargeScreen(
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged,
            )
        }
    )
}

@Composable
private fun (@Composable () -> Unit).LargeScreen(
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
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
            this@LargeScreen.invoke()
        }
    }
}

@Composable
private fun (@Composable () -> Unit).MediumScreen(
    expanded: Boolean,
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
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
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) { this@MediumScreen.invoke() }
    }
}

@Composable
private fun (@Composable () -> Unit).SmallScreen(
    currentDestination: AppDestination,
    onDestinationChanged: (AppDestination) -> Unit,
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
        Box(Modifier.fillMaxSize().padding(innerPadding)) { this@SmallScreen.invoke() }
    }
}