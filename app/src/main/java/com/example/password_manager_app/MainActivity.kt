package com.example.password_manager_app

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Notification
import android.content.ClipboardManager
import android.content.Context
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.rememberNavController
import com.example.password_manager_app.nav.PasswordManagerNavigation
import com.example.password_manager_app.ui.auth.login.LoginScreen
import com.example.password_manager_app.ui.theme.PasswordmanagerappTheme

class MainActivity : ComponentActivity(){
    @SuppressLint("ServiceCast")

    private lateinit var touchHandler: Handler
    private lateinit var run: Runnable
    private var time: Long = 2000
    val CHANNEL_ID = "com.example.password_manager_app.channel"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordmanagerappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                    PasswordManagerNavigation(navController = navController, clipboard = clipboard)
                }
            }
        }
        touchHandler = Handler(Looper.getMainLooper())
        run = Runnable {
            NotificationManagerCompat.from(this)
                .notify(0, logoutNotification(time))
        }
        startHandler()
    }

    override fun onUserInteraction() {

        Log.e("main", "Touch!")
        stopHandler()

        startHandler()

        super.onUserInteraction()
    }

    private fun startHandler(){
        touchHandler.postDelayed(run, time)
    }

    private fun stopHandler(){
        touchHandler.removeCallbacks(run)
    }


    fun logoutNotification(time: Long): Notification {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_search)
            .setContentTitle("Inactive")
            .setContentText("You've been inactive for ${time} seconds. Log out?")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES .O){
            val name = "SecretSecured"
            val descriptionText = "Notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PasswordmanagerappTheme {
        LoginScreen(onNavigateToMainScreen = {}, onNavigateToRegister = {})
    }
}



/**
 * Loading Images: https://developer.android.com/jetpack/compose/graphics/images/loading
 * Navigation: https://developer.android.com/jetpack/compose/navigation
 * Clipboard: https://developer.android.com/develop/ui/views/touch-and-input/copy-paste#kotlin
 */