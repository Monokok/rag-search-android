package com.example.yeahapp.ui.components.home

/**
 * Описание возможных состояний для ChatScreen
 */
sealed interface ChatsUIState {
    object Loading : ChatsUIState

    data class Content(
        val chats: MutableList<ChatItem>
    ): ChatsUIState

    data class Error(
        val message: String
    ): ChatsUIState
}