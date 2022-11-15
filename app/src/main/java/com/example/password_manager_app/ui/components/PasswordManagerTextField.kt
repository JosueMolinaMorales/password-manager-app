package com.example.password_manager_app.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.password_manager_app.ui.theme.LavenderBlush

@Composable
fun passwordManagerTextFieldColors(
    textColor: Color = Color.Black,
    disabledTextColor: Color = Color.Black,
    backgroundColor: Color = LavenderBlush.copy(alpha = .8F),
    cursorColor: Color = Color.Black,
    errorCursorColor: Color = Color.Red,
    focusedIndicatorColor: Color = Color.Black,
    unfocusedIndicatorColor: Color = Color.Gray,
    disabledIndicatorColor: Color = Color.Black,
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
    colors: TextFieldColors = passwordManagerTextFieldColors()
) {
    TextField(
        value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        placeholder = placeholder,
        isError = isError,
        colors = colors
    )
}