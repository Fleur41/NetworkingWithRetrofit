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
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val post: StateFlow<List<Post>> get() = _posts

    fun getPosts() {
        viewModelScope.launch (Dispatchers.IO){
            _posts.value = postRepository.getPosts()
        }
    }
}