package com.example.password_manager_app.ui.components

import androidx.compose.runtime.Composable
import com.example.password_manager_app.data.PagesWithBottomSheet
import com.example.password_manager_app.data.User
import com.example.password_manager_app.ui.EditProfileSelection
import com.example.password_manager_app.ui.app.profile.components.EditUserInfoView
import com.example.password_manager_app.ui.app.records.components.create_record.CreateRecordView
import com.example.password_manager_app.ui.app.records.components.generate_password.GeneratePasswordView


@Composable
fun BottomSheetComponent(
    onCreatePasswordClick: () -> Unit,
    onCreateSecretClick: () -> Unit,
    onGeneratePassword: (String) -> Unit,
    currentPage: PagesWithBottomSheet,
    user: User?,
    onEditChange: () -> Unit
    ) {
    when (currentPage) {
        PagesWithBottomSheet.CreatePasswordPage -> {
            GeneratePasswordView(
                onGeneratePassword
            )
        }
        PagesWithBottomSheet.HomePage -> {
            CreateRecordView(
                onCreatePasswordClick,
                onCreateSecretClick
            )
        }
        PagesWithBottomSheet.ProfilePage -> {
            EditUserInfoView(
                EditProfileSelection.Email,
                user,
                onEditChange
            )
        }
    }
}
