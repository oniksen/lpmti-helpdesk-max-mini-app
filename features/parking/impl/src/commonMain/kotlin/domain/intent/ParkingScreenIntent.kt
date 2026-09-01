package domain.intent

sealed class ParkingScreenIntent {
    data object OpenScanner : ParkingScreenIntent()
    data object OpenQrPicker : ParkingScreenIntent()
}