package presentation.viewmodel

import AppNavigator
import navigation.QrScanScreenRoute
import presentation.intent.HomePageIntent

internal class HomePageViewModel(
    private val navigator: AppNavigator
) {
    fun sendIntent(intent: HomePageIntent) {
        when (intent) {
            HomePageIntent.OpenParkingPage -> openParkingPage()
        }
    }

    private fun openParkingPage() {
        navigator.navigate(QrScanScreenRoute)
    }
}