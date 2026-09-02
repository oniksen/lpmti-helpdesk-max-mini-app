package presentation.screen.small

import QrCodeScannerResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import presentation.state.ParkingScreenState
import presentation.utils.PreviewWrapper

private val PreviewState = ParkingScreenState(
    qrCodeScannerResult = QrCodeScannerResult.Success(
        value = "Test scan result",
    )
)

@Preview(showSystemUi = true, showBackground = true, name = "Light Mode")
@Composable
private fun ParkingScreenSmallContentPreviewLight() {
    PreviewWrapper {
        PreviewState.ParkingScreenSmallContent()
    }
}
@Preview(showSystemUi = true, showBackground = true, name = "Light Mode")
@Composable
private fun ParkingScreenSmallContentPreviewDark() {
    PreviewWrapper(true) {
        PreviewState.ParkingScreenSmallContent()
    }
}