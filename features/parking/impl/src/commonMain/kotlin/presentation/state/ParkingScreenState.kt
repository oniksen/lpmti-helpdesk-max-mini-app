package presentation.state

import QrCodeScannerResult

data class ParkingScreenState(
    val qrCodeScannerOpen: Boolean = false,
    val qrCodeScannerResult: QrCodeScannerResult? = null,
)