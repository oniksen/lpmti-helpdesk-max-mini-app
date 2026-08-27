package org.lpmti.maxhelpdesk

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    MaterialTheme {
        QrScannerScreen()
    }
}

@Composable
fun QrScannerScreen() {

    val scanner = remember {
        createQrCodeScanner()
    }

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