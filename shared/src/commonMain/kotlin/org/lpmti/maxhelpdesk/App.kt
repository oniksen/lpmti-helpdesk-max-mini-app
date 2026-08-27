package org.lpmti.maxhelpdesk

import DiProvider
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    val diProvider = DiProvider()

    diProvider.provideModules(
        listOf(
            module {
                single<QrCodeScanner> {
                    createQrCodeScanner()
                }
            }
        )
    )

    diProvider.MineKoinApplication {
        MaterialTheme {
            QrScannerScreen()
        }
    }
}

@Composable
fun QrScannerScreen(
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