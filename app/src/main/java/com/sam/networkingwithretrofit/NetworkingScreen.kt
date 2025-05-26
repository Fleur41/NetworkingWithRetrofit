package com.sam.networkingwithretrofit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkingScreen(
    viewModel: PostViewModel = hiltViewModel()
) {
    val posts by viewModel.post.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPosts()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "NetworkingScreen") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Button(onClick = {}) {
//                Text(text = "Networking")
//            }
            PostList(
                modifier = Modifier.fillMaxSize(),
                posts = posts
            )
        }
    }
}

@Composable
fun PostList(
    modifier: Modifier = Modifier,
    posts: List<Post>
) {
    LazyColumn(modifier = modifier) {
        items(
            items = posts,
            key = {post -> post.id}
        ){ post ->
            PostItem(
                modifier = Modifier.fillMaxWidth(),
                post = post
            )
        }
    }
}

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post,
    ) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = post.id.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun PostItemPreview() {
    PostItem(
        modifier = Modifier.fillMaxWidth(),
        post = Post(
            id = 1,
            title = "Title",
            body = "Body",
            userId = 123
        )
    )
}
