package org.lpmti.maxhelpdesk

import DiProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    // Гарантируем отсутствие лишних рекомпозиций.
    remember { DiProvider() }.MainKoinApplication()
}