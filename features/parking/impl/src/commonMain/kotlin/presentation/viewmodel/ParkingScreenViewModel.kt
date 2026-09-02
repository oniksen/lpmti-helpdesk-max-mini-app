package presentation.viewmodel

import QrCodeScanner
import domain.intent.ParkingScreenIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.state.ParkingScreenState

class ParkingScreenViewModel(
    private val qrCodeScanner: QrCodeScanner
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    val uiState: StateFlow<ParkingScreenState>
        field = MutableStateFlow(ParkingScreenState())

    fun sendIntent(intent: ParkingScreenIntent) {
        when (intent) {
            is ParkingScreenIntent.OpenScanner -> openScanner(fileSelect = true)
        }
    }

    private fun openScanner(fileSelect: Boolean) {
        updateState { copy(qrCodeScannerOpen = true) }

        scope.launch(Dispatchers.Default) {
            val result = qrCodeScanner.scan(fileSelect = fileSelect)

            withContext(Dispatchers.Main) {
                updateState {
                    copy(
                        qrCodeScannerResult = result,
                        qrCodeScannerOpen = false,
                    )
                }
            }
        }
    }

    private fun updateState(block: ParkingScreenState.() -> ParkingScreenState) =
        uiState.update { state -> block(state) }
}