package presentation.screen.shared

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import max_helpdesk.features.parking.impl.generated.resources.Res
import max_helpdesk.features.parking.impl.generated.resources.parking_scan_qr_button
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ScanQrButton(onCLick: () -> Unit) {
    Button(
        onClick = onCLick,
    ) {
        Text(
            text = stringResource(resource = Res.string.parking_scan_qr_button),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}