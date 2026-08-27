package org.lpmti.maxhelpdesk

import kotlinx.coroutines.await
import kotlin.js.JsString
import kotlin.js.Promise
import kotlin.js.js

private fun isMaxBridgeAvailable(): Boolean =
    js(
        """
        typeof window !== "undefined" &&
        typeof window.WebApp !== "undefined" &&
        window.WebApp !== null &&
        typeof window.WebApp.openCodeReader === "function"
        """
    )

private fun openMaxCodeReader(
    fileSelect: Boolean,
): Promise<JsString> =
    js(
        """
        window.WebApp.openCodeReader(fileSelect)
        """
    )

private class MaxQrCodeScanner : QrCodeScanner {

    override suspend fun scan(
        fileSelect: Boolean,
    ): QrCodeScannerResult {

        if (!isMaxBridgeAvailable()) {
            return QrCodeScannerResult.Unavailable
        }

        return try {
            val result = openMaxCodeReader(fileSelect)
                .await<JsString>()
                .toString()

            QrCodeScannerResult.Success(
                value = result,
            )
        } catch (throwable: Throwable) {
            QrCodeScannerResult.Error(
                cause = throwable,
            )
        }
    }
}

actual fun createQrCodeScanner(): QrCodeScanner {
    return MaxQrCodeScanner()
}