package com.example.password_manager_app.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.password_manager_app.ui.theme.LavenderBlush

@Composable
fun passwordManagerTextFieldColors(
    textColor: Color = Color.Black,
    disabledTextColor: Color = Color.Black,
    backgroundColor: Color = LavenderBlush.copy(alpha = .8F),
    cursorColor: Color = Color.Black,
    errorCursorColor: Color = Color.Red,
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedIndicatorColor: Color = Color.Transparent,
    disabledIndicatorColor: Color = Color.Transparent,
    errorIndicatorColor: Color = Color.Red,
    leadingIconColor: Color = Color.Black,
    disabledLeadingIconColor: Color = Color.Black,
    errorLeadingIconColor: Color = Color.Red,
    trailingIconColor: Color = Color.Black,
    disabledTrailingIconColor: Color = Color.Black,
    errorTrailingIconColor: Color = Color.Red,
    focusedLabelColor: Color = Color.Black,
    unfocusedLabelColor: Color = Color.DarkGray,
    disabledLabelColor: Color = Color.Black,
    errorLabelColor: Color = Color.Red,
    placeholderColor: Color = Color.DarkGray,
    disabledPlaceholderColor: Color = Color.DarkGray
) = TextFieldDefaults.textFieldColors(
    textColor,
    disabledTextColor,
    backgroundColor,
    cursorColor,
    errorCursorColor,
    focusedIndicatorColor,
    unfocusedIndicatorColor,
    disabledIndicatorColor,
    errorIndicatorColor,
    leadingIconColor,
    disabledLeadingIconColor,
    errorLeadingIconColor,
    trailingIconColor,
    disabledTrailingIconColor,
    errorTrailingIconColor,
    focusedLabelColor,
    unfocusedLabelColor,
    disabledLabelColor,
    errorLabelColor,
    placeholderColor,
    disabledPlaceholderColor
)

@Composable
fun PasswordManagerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    colors: TextFieldColors = passwordManagerTextFieldColors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    readOnly: Boolean = false,
    enabled: Boolean = true,
    hideText: Boolean = false,
    isHiddenField: Boolean = false,
    onTrailingIconClick: (Boolean) -> Unit = {}
) {
    if (!isHiddenField) {
        TextField(
            value,
            onValueChange = onValueChange,
            label = label,
            modifier = modifier,
            placeholder = placeholder,
            readOnly = readOnly,
            isError = isError,
            colors = colors,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (hideText) { PasswordVisualTransformation() } else { VisualTransformation.None },
            enabled = enabled,
        )
    } else {
        TextField(
            value,
            onValueChange = onValueChange,
            label = label,
            modifier = modifier,
            placeholder = placeholder,
            readOnly = readOnly,
            isError = isError,
            colors = colors,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (hideText) { PasswordVisualTransformation() } else { VisualTransformation.None },
            enabled = enabled,
            trailingIcon = { IconToggleButton(
                checked = hideText,
                onCheckedChange = onTrailingIconClick
            ) {
                if (!hideText) {
                    Icon(Icons.Filled.Visibility, "")
                } else {
                    Icon(Icons.Filled.VisibilityOff, "")
                }
            }},
        )
    }
}