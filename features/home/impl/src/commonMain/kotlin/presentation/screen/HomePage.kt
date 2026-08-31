package presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun HomePage(
    onScanPage: () -> Unit,
) {

    HomePageContent(
        onScanPage = onScanPage,
    )
}

@Composable
private fun HomePageContent(
    onScanPage: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Home Page",
                style = MaterialTheme.typography.headlineLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onScanPage,
            ) {
                Text(
                    text = "Сканировать QR"
                )
            }
        }
    }
}

@Composable
private fun PreviewState() {
    HomePageContent(
        onScanPage = { }
    )
}

@Preview
@Composable
private fun HomePagePreviewLight() {
    MaterialTheme {
        Surface {
            PreviewState()
        }
    }
}

@Preview
@Composable
private fun HomePagePreviewDark() {
    MaterialTheme(darkColorScheme()) {
        Surface {
            PreviewState()
        }
    }
}