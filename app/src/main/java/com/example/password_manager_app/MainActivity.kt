package com.example.password_manager_app

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Notification
import android.app.PendingIntent
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.password_manager_app.nav.PasswordManagerNavigation
import com.example.password_manager_app.nav.Routes
import com.example.password_manager_app.ui.app.main_screen.MainScreenViewModel
import com.example.password_manager_app.ui.auth.login.LoginScreen
import com.example.password_manager_app.ui.theme.PasswordmanagerappTheme

class MainActivity : ComponentActivity(){
    @SuppressLint("ServiceCast")

    private lateinit var touchHandler: Handler
    private lateinit var run: Runnable
    private var time: Long = 5000
    private val CHANNEL_ID = "com.example.password_manager_app.channel"
    private val isLoggedIn: MutableState<Boolean> = mutableStateOf(false)
    private lateinit var mainScreenViewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordmanagerappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainScreenViewModel = viewModel()
                    val navController = rememberNavController()
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                    PasswordManagerNavigation(
                        navController = navController,
                        clipboard = clipboard,
                        mainScreenViewModel = mainScreenViewModel
                    )
                    isLoggedIn.value = mainScreenViewModel.isLoggedIn.value
                }
            }
        }
        touchHandler = Handler(Looper.getMainLooper())
        run = Runnable {
            NotificationManagerCompat.from(this)
                .notify(0, logoutNotification(time))
        }
    }

    override fun onPause() {
        super.onPause()
        if (isLoggedIn.value) {
            startHandler()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isLoggedIn.value) {
            stopHandler()
        }
    }

    override fun onStop() {
        mainScreenViewModel.logUserOut()
        super.onStop()
    }

    /**
     * Listens for user's interaction with the screen. When a user interacts with the screem,
     * the Handler is stopped and restarted to start the timer for inactivity.
     */
    override fun onUserInteraction() {
        super.onUserInteraction()
        if (isLoggedIn.value) {
            stopHandler()
        }
    }

    /**
     * Starts the handler for the inactivity timer.
     */
    private fun startHandler(){
        touchHandler.postDelayed(run, time)
    }

    /**
     * Stops the handler for the inactivity timer.
     */
    private fun stopHandler(){
        touchHandler.removeCallbacks(run)
    }

    /**
     * Creates the notification for when a user is inactive for a set amount of time.
     * When the user taps the notification, they will be logged out and returned to the Welcome Screen.
     * @param time The amount of time a user has been inactive.
     * @return The notification built with the set intent.
     */
    private fun logoutNotification(time: Long): Notification {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.koalalogo)
            .setContentTitle("Inactive")
            .setContentText("You've been inactive for ${time / 1000} seconds. Tap here to log out.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        return builder.build()
    }

    /**
     * Creates the notification channel for the inactivity notification
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
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
 * Notification/Intent: https://developer.android.com/develop/ui/views/notifications/build-notification
 * Inactivity Detection: https://www.geeksforgeeks.org/how-to-detect-user-inactivity-in-android/
 */