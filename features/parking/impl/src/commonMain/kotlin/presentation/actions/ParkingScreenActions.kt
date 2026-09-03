package presentation.actions

data class ParkingScreenActions(
    val openScan: () -> Unit,
    val resetScanResult: () -> Unit,
    val onNumberChanged: (String) -> Unit,
)
