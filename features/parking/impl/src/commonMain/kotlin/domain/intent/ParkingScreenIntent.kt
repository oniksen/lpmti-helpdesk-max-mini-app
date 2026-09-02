package domain.intent

sealed class ParkingScreenIntent {
    data object OpenScanner : ParkingScreenIntent()
}