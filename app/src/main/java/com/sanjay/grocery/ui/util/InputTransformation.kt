package com.sanjay.grocery.ui.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

enum class FieldType {
    EXPIRE_DATE,
    CARD_NUMBER
}

class InputTransformation(private val fieldType: FieldType) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText = when (fieldType) {
        FieldType.EXPIRE_DATE -> expirationFilter(text)
        FieldType.CARD_NUMBER -> cardNumberFilter(text)
    }

    private fun expirationFilter(text: AnnotatedString): TransformedText {
        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset + 1
                return 5
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                return 4
            }
        }

        return TransformedText(
            AnnotatedString(FormatterUtil.expireDateFormat(text.text)),
            offsetTranslator
        )
    }

    private fun cardNumberFilter(text: AnnotatedString): TransformedText {
        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }
        }

        return TransformedText(
            AnnotatedString(FormatterUtil.cardNumFormat(text.text)),
            offsetTranslator
        )
    }
}