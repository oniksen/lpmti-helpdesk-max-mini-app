package org.lpmti.maxhelpdesk

import DiProvider
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        MaterialExpressiveTheme {
            // Гарантируем отсутствие лишних рекомпозиций.
            remember { DiProvider() }.MainKoinApplication()
        }
    }
}