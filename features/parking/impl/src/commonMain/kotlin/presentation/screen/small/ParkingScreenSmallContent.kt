package presentation.screen.small

import QrCodeScannerResult
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.screen.LocalParkingScreenActions
import presentation.screen.shared.ScanQrButton
import presentation.state.ParkingScreenState

@Composable
internal fun ParkingScreenState.ParkingScreenSmallContent() {
    val localParkingScreenActions = LocalParkingScreenActions.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            println("Qr scan result: $qrCodeScannerResult")
            when (qrCodeScannerResult) {
                is QrCodeScannerResult.Success -> {
                    Text(
                        text = "Результат: ${qrCodeScannerResult.value}",
                    )
                }
                QrCodeScannerResult.Unavailable -> {
                    Text(
                        text = "Сканирование QR через MAX недоступно",
                    )
                }
                is QrCodeScannerResult.Error -> {
                    Text(
                        text = "Ошибка сканирования: ${qrCodeScannerResult.cause.message}",
                    )
                }
                null -> Unit
            }
            ScanQrButton { localParkingScreenActions.openScan() }
        }
    }
}
