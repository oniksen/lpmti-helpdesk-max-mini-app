package presentation.screen.shared

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
internal fun PassNumberInput() {
    // Используем классический String-state
    var textState by remember { mutableStateOf("1234567") }

    OutlinedTextField(
        value = textState,
        onValueChange = { textState = it },
        singleLine = true,
        label = {
            Text(text = "Номер пропуска")
        },
        placeholder = {
            Text("1234567")
        },
    )
}