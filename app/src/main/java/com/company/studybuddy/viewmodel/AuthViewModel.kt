package com.company.studybuddy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Success
        }
    }

    fun authenticate(name: String, email: String, password: String, isSignUp: Boolean) {
        if (email.isBlank() || password.isBlank() || (isSignUp && name.isBlank())) {
            _authState.value = AuthState.Error("Please fill out all required fields.")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                if (isSignUp) {
                    val result = auth.createUserWithEmailAndPassword(email, password).await()

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    result.user?.updateProfile(profileUpdates)?.await()
                } else {
                    auth.signInWithEmailAndPassword(email, password).await()
                }
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Authentication failed.")
            }
        }
    }

    fun authenticateWithGoogle(idToken: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).await()
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.localizedMessage ?: "Google Authentication failed.")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}