package com.sanjay.grocery.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.sanjay.grocery.R
import com.sanjay.grocery.ui.theme.LightBg
import com.sanjay.grocery.ui.theme.PrimaryClr
import com.sanjay.grocery.ui.theme.TextDark

@Composable
fun TextFieldWithPlaceHolder(
    placeholder: String,
    modifier: Modifier,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    isEnabled: Boolean = true,
    autoCorrect: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = TextDark,
        disabledTextColor = TextDark,
        focusedContainerColor = LightBg,
        unfocusedContainerColor = LightBg,
        disabledContainerColor = Color.LightGray,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        errorContainerColor = LightBg,
        focusedLeadingIconColor = PrimaryClr,
        focusedTrailingIconColor = PrimaryClr,
        focusedLabelColor = PrimaryClr,
        cursorColor = PrimaryClr
    ),
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    isOnlyDigits: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
) {
    val textValue = remember {
        mutableStateOf("")
    }

    val hasValue = remember {
        mutableStateOf(false)
    }

    TextField(
        placeholder = { DefaultText(text = placeholder) },
        modifier = modifier,
        value = textValue.value,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrectEnabled = autoCorrect,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        enabled = isEnabled,
        colors = colors,
        onValueChange = {
            val it = it.trim()
            if (maxLength == null || it.length <= maxLength) {
                if (isOnlyDigits) {
                    if (it.isDigitsOnly()) {
                        textValue.value = it
                        hasValue.value = it.trim().isNotEmpty()
                        onValueChange(it)
                    }
                } else {
                    textValue.value = it
                    hasValue.value = it.trim().isNotEmpty()
                    onValueChange(it)
                }
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (hasValue.value) {
                IconButton(onClick = {
                    textValue.value = ""
                    hasValue.value = false
                    onValueChange("")
                }) {
                    Icon(
                        painterResource(id = R.drawable.ic_cancel),
                        tint = Color.LightGray,
                        contentDescription = stringResource(R.string.clear)
                    )
                }
            }
        },
        isError = isError
    )
}

@Composable
fun ReadOnlyTextField(
    label: String,
    modifier: Modifier,
    value: String,
    colors: TextFieldColors = TextFieldDefaults.colors(
        disabledTextColor = TextDark,
        disabledContainerColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent
    ),
    isError: Boolean = false,
    errorText: String = "",
    textAlign: TextAlign = TextAlign.Start,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        label = { DefaultText(text = label, fontSize = 12.sp) },
        modifier = modifier,
        textStyle = TextStyle(
            fontSize = 14.sp,
            textAlign = textAlign
        ),
        value = value,
        enabled = false,
        readOnly = true,
        colors = colors,
        onValueChange = {},
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        supportingText = if (isError && errorText.isNotEmpty()) {
            {
                ErrorText(
                    text = errorText,
                    modifier = Modifier
                )
            }
        } else {
            null
        }
    )
}