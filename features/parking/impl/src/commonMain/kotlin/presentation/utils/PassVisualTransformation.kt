package presentation.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Реализует маску XXX/XXXXX.
 * */
class PassVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Исходный чистый текст (только цифры, которые вводит пользователь)
        val trimmed = if (text.text.length >= 9) text.text.substring(0, 8) else text.text

        val out = StringBuilder()

        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 2) out.append("/")
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 2) offset
                else offset + 1
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                return offset - 1
            }
        }

        return TransformedText(AnnotatedString(out.toString()), numberOffsetTranslator)
    }
}
