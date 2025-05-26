package com.sam.networkingwithretrofit

sealed interface NetworkingUiState {
    object None: NetworkingUiState
    object Loading: NetworkingUiState
    data class Success(val posts: List<Post>): NetworkingUiState
    data class Error(val message: String): NetworkingUiState

}
