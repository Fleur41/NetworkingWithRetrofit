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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkingScreen(
    viewModel: PostViewModel = hiltViewModel()
) {
    val uiState by viewModel.post.collectAsState()

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
                .padding(innerPadding)

        ) {
            when(uiState){
                is NetworkingUiState.Loading -> {
                    LoadingIndicator(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is NetworkingUiState.Success -> {
                    val posts = (uiState as NetworkingUiState.Success).posts
                    PostList(
                        modifier = Modifier.fillMaxSize(),
                        posts = posts
                    )
                }
                is NetworkingUiState.Error -> {
                    val message = (uiState as NetworkingUiState.Error).message
                    ErrorView(
                        modifier = Modifier.fillMaxSize(),
                        msg = message,
                        onRetryClick = { viewModel.getPosts() }
                    )
                }
                else -> {}
            }

        }
    }
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    msg: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Column (
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Button(onClick = onRetryClick) {
                Text(text ="Retry")
            }
        }
    }
}


@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }

}


@Composable
fun PostList(
    modifier: Modifier = Modifier,
    posts: List<Post>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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

                Column {


                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = post.body,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


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
