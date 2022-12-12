package com.example.password_manager_app.ui.app.records.components.create_record

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.password_manager_app.ui.components.PasswordManagerButton
import com.example.password_manager_app.ui.theme.LavenderBlush

/**
 * The Bottom Sheet view for creating a new record
 */
@Composable
fun CreateRecordView(
    onCreatePasswordClick: () -> Unit,
    onCreateSecretClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(
                if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    .5F
                } else {
                    .25F
                }
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Create New Title
            Text(
                text = "Create New",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline,
                color = LavenderBlush
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Password Button
            PasswordManagerButton(
                onClick = onCreatePasswordClick,
                modifier = Modifier.fillMaxWidth(.5F)
            ) {
                Text("Password")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Secret Button
            PasswordManagerButton(
                onClick = onCreateSecretClick,
                modifier = Modifier.fillMaxWidth(.5F)
            ) {
                Text("Secret")
            }
        }
    }
}