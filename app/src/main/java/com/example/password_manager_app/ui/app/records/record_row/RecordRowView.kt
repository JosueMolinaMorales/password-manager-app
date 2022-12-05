package com.example.password_manager_app.ui.app.records.record_row

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.model.RecordType
import com.example.password_manager_app.ui.theme.Charcoal
import com.example.password_manager_app.ui.theme.LavenderBlush
import com.example.password_manager_app.ui.theme.PewterBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecordRowView(
    title: String,
    recordType: RecordType,
    onCardClick: () -> Unit,
    onCopyToClipboardClick: () -> Unit,
    onEditClick: (RecordType) -> Unit,
    onDeleteClick: () -> Unit
) {
    val isDropDownOpen = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp)
            .height(70.dp),
        onClick = onCardClick

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black)
                .background(color = PewterBlue),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text=title,
                    color = Color.Black,
                    fontSize = 32.sp,
                )
                Text(
                    text=recordType.value,
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
            Column {
                IconButton(onClick = { isDropDownOpen.value = !isDropDownOpen.value }) {
                    Icon(Icons.Default.MoreVert, "", tint = Color.Black)
                }
                DropdownMenu(
                    expanded = isDropDownOpen.value,
                    onDismissRequest = { isDropDownOpen.value = false },
                    modifier = Modifier
                        .background(color = Charcoal)
                        .fillMaxWidth(.45F),
                ) {
                    DropdownMenuItem(onClick = { onCopyToClipboardClick() }) {
                        Text(
                            text="Copy To Clipboard",
                            color = LavenderBlush
                        )
                    }
                    Divider()
                    DropdownMenuItem(onClick = { onEditClick(recordType) }) {
                        Text(
                            text="Edit",
                            color = LavenderBlush
                        )
                    }
                    Divider()
                    DropdownMenuItem(onClick = onDeleteClick) {
                        Text(
                            text="Delete",
                            color = LavenderBlush
                        )
                    }
                }
            }
        }
    }
}