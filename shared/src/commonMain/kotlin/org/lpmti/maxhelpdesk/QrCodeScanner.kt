@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.lpmti.maxhelpdesk

interface QrCodeScanner {
    /**
     * @param fileSelect если true, пользователь может выбрать файл из галереи.
     *                   если false, используется только камера (рекомендуется для UX сканера).
     */
    suspend fun scan(fileSelect: Boolean = false): QrCodeScannerResult
}