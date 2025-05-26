package com.sam.networkingwithretrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {
    private val _posts = MutableStateFlow<NetworkingUiState>(NetworkingUiState.None)
    val post: StateFlow<NetworkingUiState> get() = _posts

    fun getPosts() {
        viewModelScope.launch (Dispatchers.IO) {
            _posts.value = NetworkingUiState.Loading


            try {
                val retrievedPosts = postRepository.getPosts()
                _posts.value = NetworkingUiState.Success(retrievedPosts)
            } catch (e: Exception) {
                e.printStackTrace()
                _posts.value = NetworkingUiState.Error(e.message ?: "Unknown error")

            }

        }
    }

}

//For  POST Method
//class PostVM @Inject constructor(private val postRepository: PostRepository){
//    private val _createPostUiState = MutableStateFlow<NetworkingUiState>(NetworkingUiState.None)
//    val createPostUiState: StateFlow<NetworkingUiState> get() = _createPostUiState
//
//    fun submitNewPost(title: String, body: String, userId: Int) { // Or pass a Post object directly
//        viewModelScope.launch(Dispatchers.IO) {
//            _createPostUiState.value = NetworkingUiState.Loading // Indicate creation is in progress
//
//            // Construct the Post object.
//            // Note: The server typically assigns the 'id'. So, you might not set it here,
//            // or your Post data class should have a nullable id.
//            val newPost = Post(userId = userId, title = title, body = body /* id = null if applicable */)
//
//            try {
//                val createdPost = postRepository.createNewPost(newPost)
//                if (createdPost != null) {
//                    _createPostUiState.value = NetworkingUiState.Success(listOf(createdPost)) // Or a specific success state with the single created post
//                    // Optionally, you might want to refresh the main list of posts
//                    // getPosts()
//                    // Or, if your Success state can hold a single post, adjust NetworkingUiState accordingly
//                    // Or, you could update the _postsListUiState by adding the new post to the existing list
//                } else {
//                    _createPostUiState.value = NetworkingUiState.Error("Failed to create post: Server error")
//                }
//            } catch (e: Exception) {
//                _createPostUiState.value = NetworkingUiState.Error("Failed to create post: ${e.message ?: "Unknown error"}")
//            }
//        }
//    }
//
//    // Helper to reset the creation state if needed (e.g., after the user acknowledges a message)
//    fun resetCreatePostState() {
//        _createPostUiState.value = NetworkingUiState.None
//    }
//}
