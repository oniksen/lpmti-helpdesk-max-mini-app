package org.lpmti.maxhelpdesk

sealed interface QrCodeScannerResult {

    data class Success(
        val value: String,
    ) : QrCodeScannerResult

    data object Unavailable : QrCodeScannerResult

    data class Error(
        val cause: Throwable,
    ) : QrCodeScannerResult
}