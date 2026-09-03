package presentation.state

data class ParkingScreenState(
    val passNumber: String = "",
    val passError: String? = null,
    val showQrBtnEnabled: Boolean = true,
)