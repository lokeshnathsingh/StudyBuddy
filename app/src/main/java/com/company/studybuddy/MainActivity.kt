package com.company.studybuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.studybuddy.ui.theme.AuthScreen
import com.company.studybuddy.ui.theme.ChatScreen
import com.company.studybuddy.ui.theme.HistoryScreen
import com.company.studybuddy.ui.theme.SplashScreen
import com.company.studybuddy.ui.theme.StudyBuddyTheme
import com.company.studybuddy.viewmodel.AuthViewModel
import com.company.studybuddy.viewmodel.StudyBuddyViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val chatViewModel: StudyBuddyViewModel = viewModel()
                    val authViewModel: AuthViewModel = viewModel()

                    var currentScreen by remember { mutableStateOf("splash") }

                    when (currentScreen) {
                        "splash" -> {
                            SplashScreen(
                                onTimeout = {
                                    if (FirebaseAuth.getInstance().currentUser != null) {
                                        currentScreen = "chat"
                                    } else {
                                        currentScreen = "auth"
                                    }
                                }
                            )
                        }
                        "auth" -> {
                            AuthScreen(
                                viewModel = authViewModel,
                                onLoginSuccess = { currentScreen = "chat" }
                            )
                        }
                        "chat" -> {
                            var showHistory by remember { mutableStateOf(false) }

                            if (showHistory) {
                                HistoryScreen(
                                    viewModel = chatViewModel,
                                    onBackClick = { showHistory = false }
                                )
                            } else {
                                ChatScreen(
                                    viewModel = chatViewModel,
                                    onHistoryClick = { showHistory = true },
                                    onLogoutClick = {
                                        FirebaseAuth.getInstance().signOut()
                                        authViewModel.resetState()
                                        currentScreen = "auth"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}