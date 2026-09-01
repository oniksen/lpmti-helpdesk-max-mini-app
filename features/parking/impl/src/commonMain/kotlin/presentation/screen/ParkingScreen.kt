package presentation.screen

import QrCodeScanner
import QrCodeScannerResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ParkingScreen(
    scanner: QrCodeScanner = koinInject()
) {
    val scope = rememberCoroutineScope()

    var result by remember {
        mutableStateOf<QrCodeScannerResult?>(null)
    }

    Button(
        onClick = {
            scope.launch {
                result = scanner.scan()
            }
        },
    ) {
        Text("Сканировать QR")
    }

    when (val currentResult = result) {
        is QrCodeScannerResult.Success -> {
            Text(
                text = "Результат: ${currentResult.value}",
            )
        }

        QrCodeScannerResult.Unavailable -> {
            Text(
                text = "Сканирование QR через MAX недоступно",
            )
        }

        is QrCodeScannerResult.Error -> {
            Text(
                text = "Ошибка сканирования: ${currentResult.cause.message}",
            )
        }

        null -> Unit
    }
}