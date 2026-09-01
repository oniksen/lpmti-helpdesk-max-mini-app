package presentation.screen

import QrCodeScanner
import QrCodeScannerResult
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun ParkingScreenContent() {

}