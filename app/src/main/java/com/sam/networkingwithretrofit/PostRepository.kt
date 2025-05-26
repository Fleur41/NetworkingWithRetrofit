package com.sam.networkingwithretrofit

import javax.inject.Inject

interface PostRepository {
    suspend fun getPosts(): List<Post>
}

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi
): PostRepository{
    override suspend fun getPosts(): List<Post> {
        return postApi.getPosts()
    }

}