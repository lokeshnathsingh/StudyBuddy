package com.company.studybuddy.model

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val isUser: Boolean = false,
    val attachedFileName: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)