package presentation.viewmodel

import QrCodeScanner
import QrCodeScannerResult
import domain.intent.ParkingScreenIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import max_helpdesk.features.parking.impl.generated.resources.Res
import max_helpdesk.features.parking.impl.generated.resources.parking_scan_max_unavailable
import org.jetbrains.compose.resources.getString
import presentation.effect.ParkingScreenEffect
import presentation.state.ParkingScreenState

class ParkingScreenViewModel(
    private val qrCodeScanner: QrCodeScanner
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    val uiState: StateFlow<ParkingScreenState>
        field = MutableStateFlow(ParkingScreenState())
    val effect: SharedFlow<ParkingScreenEffect>
        field = MutableSharedFlow<ParkingScreenEffect>(
            replay = 0, // Не храним и не переизлучаем старые эффекты при пересоздании экрана
            extraBufferCapacity = 1,    // Позволяет делать tryEmit() без блокировки потока
            onBufferOverflow = BufferOverflow.DROP_OLDEST,  // Если буфер забит, старое событие стирается, новое доставляется
        )

    fun sendIntent(intent: ParkingScreenIntent) {
        when (intent) {
            is ParkingScreenIntent.OpenScanner -> openScanner(fileSelect = true)
            is ParkingScreenIntent.ResetScannerResult -> resetScannerResult()
            is ParkingScreenIntent.OnNumberChanged -> { validatePassNumber(intent.number) }
        }
    }

    private fun openScanner(fileSelect: Boolean) {
        scope.launch(Dispatchers.Default) {
            val result = qrCodeScanner.scan(fileSelect = fileSelect)

            withContext(Dispatchers.Main) {
                when (result) {
                    is QrCodeScannerResult.Error -> {
                        updateState { copy(passError = result.cause.message) }
                    }
                    is QrCodeScannerResult.Success -> {
                        updateState { copy(passError = null, passNumber = result.value) }
                    }
                    is QrCodeScannerResult.Unavailable -> {
                        resetScannerResult()
                        // Вызвать Snackbar.
                        effect.tryEmit(
                            value = ParkingScreenEffect.ShowSnackBar(
                                message = getString(Res.string.parking_scan_max_unavailable)
                            )
                        )
                    }
                }
            }
        }
    }
    /** Сброс состояний связанных с номером пропуска до дефолтных. */
    private fun resetScannerResult() {
        updateState { copy(
            passError = null,
            passNumber = "",
        ) }
    }
    private fun validatePassNumber(number: String) {
        // TODO("Валидация номера")
        updateState { copy(passNumber = number) }
    }

    private fun updateState(block: ParkingScreenState.() -> ParkingScreenState) =
        uiState.update { state -> block(state) }
}