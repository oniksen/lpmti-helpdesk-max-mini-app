package presentation.screen

import AdaptiveLayoutWrapper
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import domain.intent.ParkingScreenIntent
import kotlinx.coroutines.flow.SharedFlow
import presentation.actions.ParkingScreenActions
import presentation.effect.ParkingScreenEffect
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
    val effect = viewModel.effect
    val parkingScreenActions = remember {
        ParkingScreenActions(
            openScan = { viewModel.sendIntent(ParkingScreenIntent.OpenScanner) },
            resetScanResult = { viewModel.sendIntent(ParkingScreenIntent.ResetScannerResult) },
            onNumberChanged = { viewModel.sendIntent(ParkingScreenIntent.OnNumberChanged(it)) },
        )
    }

    CompositionLocalProvider(LocalParkingScreenActions provides parkingScreenActions) {
        ParkingScreenContentShell(
            uiState = uiState,
            effect = effect,
        )
    }
}

@Composable
private fun ParkingScreenContentShell(
    uiState: ParkingScreenState,
    effect: SharedFlow<ParkingScreenEffect>,
) {
    AdaptiveLayoutWrapper(
        state = uiState,
        effect = effect,
        compact = { state, effect ->
            ParkingScreenSmallContent(state, effect)
        }
    )
}