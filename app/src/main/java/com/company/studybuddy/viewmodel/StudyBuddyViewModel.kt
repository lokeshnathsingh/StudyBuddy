package com.company.studybuddy.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.studybuddy.model.ChatMessage
import com.company.studybuddy.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StudyBuddyViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val userId = auth.currentUser?.uid ?: "anonymous"
    private val chatCollection = db.collection("users").document(userId).collection("chat_history")

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _historyMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val historyMessages: StateFlow<List<ChatMessage>> = _historyMessages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini3-flash",
        apiKey = BuildConfig.GEMINI_API_KEY,
        systemInstruction = content {
            text("You are an expert Study Buddy. Always format your responses using standard Markdown. NEVER use LaTeX (like ->) for formatting, arrows, or math. Instead, use standard Unicode symbols like '→' and normal text.")
        }
    )
    private var chat = generativeModel.startChat()

    private fun addMessageToScreen(message: ChatMessage) {
        _messages.value = _messages.value + message
    }

    fun sendMessage(inputText: String) {
        val userMessage = ChatMessage(text = inputText, isUser = true)

        addMessageToScreen(userMessage)
        saveMessageToCloud(userMessage)

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(inputText)
                response.text?.let { aiResponse ->
                    val aiMsg = ChatMessage(text = aiResponse, isUser = false)
                    addMessageToScreen(aiMsg)
                    saveMessageToCloud(aiMsg)
                }
            } catch (e: Exception) {
                val errorMsg = ChatMessage(text = "Error: ${e.localizedMessage}", isUser = false)
                addMessageToScreen(errorMsg)
                saveMessageToCloud(errorMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadFileAndAnalyze(context: Context, uri: Uri, prompt: String, fileName: String, mimeType: String) {
        val userMessage = ChatMessage(text = prompt, isUser = true, attachedFileName = fileName)

        addMessageToScreen(userMessage)
        saveMessageToCloud(userMessage)

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val fileBytes = inputStream?.readBytes()
                inputStream?.close()

                if (fileBytes != null) {
                    val response = generativeModel.generateContent(
                        content {
                            blob(mimeType, fileBytes)
                            text(prompt)
                        }
                    )

                    response.text?.let { aiResponse ->
                        val aiMsg = ChatMessage(text = aiResponse, isUser = false)
                        addMessageToScreen(aiMsg)
                        saveMessageToCloud(aiMsg)
                    }
                } else {
                    val errorMsg = ChatMessage(text = "Error: Could not read file.", isUser = false)
                    addMessageToScreen(errorMsg)
                    saveMessageToCloud(errorMsg)
                }
            } catch (e: Exception) {
                val errorMsg = ChatMessage(text = "Upload failed: ${e.localizedMessage}", isUser = false)
                addMessageToScreen(errorMsg)
                saveMessageToCloud(errorMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            try {
                val snapshot = chatCollection.orderBy("timestamp", Query.Direction.DESCENDING).get().await()
                val messages = snapshot.documents.mapNotNull {
                    it.toObject(ChatMessage::class.java)
                }
                _historyMessages.value = messages
            } catch (e: Exception) {
            }
        }
    }

    fun clearChat() {
        _messages.value = emptyList()
        chat = generativeModel.startChat()
    }

    private fun saveMessageToCloud(message: ChatMessage) {
        chatCollection.document(message.id).set(message)
    }
}