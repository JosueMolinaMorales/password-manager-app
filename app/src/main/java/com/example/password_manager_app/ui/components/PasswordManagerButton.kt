package com.example.password_manager_app.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.password_manager_app.ui.theme.DisabledButtonColor
import com.example.password_manager_app.ui.theme.Opal

@Composable
fun passwordManagerButtonColors(
    backgroundColor: Color = Opal,
    contentColor: Color = Color.Black,
    disabledBackgroundColor: Color = DisabledButtonColor,
    disabledContentColor: Color = Color.DarkGray
) = ButtonDefaults.buttonColors(
    backgroundColor,
    contentColor,
    disabledBackgroundColor,
    disabledContentColor
)


@Composable
fun PasswordManagerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = passwordManagerButtonColors(),
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = content
    )
}