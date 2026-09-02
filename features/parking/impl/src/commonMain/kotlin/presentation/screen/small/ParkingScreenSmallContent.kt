package presentation.screen.small

import QrCodeScannerResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import max_helpdesk.features.parking.impl.generated.resources.Res
import max_helpdesk.features.parking.impl.generated.resources.parking_scan_qr_button
import org.jetbrains.compose.resources.stringResource
import presentation.screen.LocalParkingScreenActions
import presentation.screen.shared.PassNumberInput
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                PassNumberInput()
                Spacer(modifier = Modifier.width(4.dp))
                ScanQrButton(
                    modifier = Modifier
                        .height(64.dp)
                        .offset(y = 5.dp)
                ) {
                    localParkingScreenActions.openScan()
                }
            }
        }
    }
}

@Composable
private fun ScanQrButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FilledIconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Outlined.QrCodeScanner,
            contentDescription = null,
        )
    }
}
