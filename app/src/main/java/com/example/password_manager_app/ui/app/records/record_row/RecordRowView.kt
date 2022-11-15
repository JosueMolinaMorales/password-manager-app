package com.example.password_manager_app.ui.app.records.record_row

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.password_manager_app.data.RecordType
import com.example.password_manager_app.ui.theme.PewterBlue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecordRowView(
    title: String,
    recordType: RecordType
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp)
            .height(70.dp),
        onClick = { /*TODO*/ }
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.MoreVert, "", tint = Color.Black)
            }
        }
    }
}