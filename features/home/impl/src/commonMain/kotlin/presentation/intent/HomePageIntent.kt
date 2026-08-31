package presentation.intent

sealed class HomePageIntent {
    data object OpenParkingPage : HomePageIntent()
}