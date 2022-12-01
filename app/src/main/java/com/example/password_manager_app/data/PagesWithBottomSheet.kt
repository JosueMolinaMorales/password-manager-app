package com.example.password_manager_app.data

enum class PagesWithBottomSheet {
    /* Used when on homepage, brings up create password and secret */
    HomePage,

    /* Used when on Create Password Screen, will bring up Generate Password Screen */
    CreatePasswordPage,

    /* Used when on Profile Page, Will bring up the edit Password Screen or edit Email Screen */
    ProfilePage,
}