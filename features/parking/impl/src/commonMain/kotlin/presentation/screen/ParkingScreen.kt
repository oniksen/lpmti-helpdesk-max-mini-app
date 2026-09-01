package presentation.screen

import AdaptiveLayoutWrapper
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import domain.intent.ParkingScreenIntent
import presentation.actions.ParkingScreenActions
import presentation.screen.small.ParkingScreenSmallContent
import presentation.state.ParkingScreenState
import presentation.viewmodel.ParkingScreenViewModel

val LocalParkingScreenActions = staticCompositionLocalOf<ParkingScreenActions> {
    error("Local actions not provided")
}

@Composable
fun ParkingScreen(
    viewModel: ParkingScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val parkingScreenActions = ParkingScreenActions(
        openScan = { viewModel.sendIntent(ParkingScreenIntent.OpenScanner) }
    )

    CompositionLocalProvider(LocalParkingScreenActions provides parkingScreenActions) {
        ParkingScreenContentShell(
            uiState = uiState,
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun ParkingScreenContentShell(
    uiState: ParkingScreenState,
) {
    AdaptiveLayoutWrapper(
        state = uiState,
        compact = {
            ParkingScreenSmallContent()
        }
    )
}