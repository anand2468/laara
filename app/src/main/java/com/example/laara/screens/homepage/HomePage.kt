package com.example.laara.screens.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laara.R
import com.example.laara.ui.theme.AppTheme


@Composable
fun HomePage(vm: HomeVM = viewModel()){
//    val postItems = PostsData().loadOfflinePosts()
    val status = vm.uiState.collectAsState()
//    val scroll = rememberScrollState()
    vm.loadDummyPosts()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
//        when(status.value){
//            is homeUiState.Loading -> Text(text = "loading")
//            is homeUiState.Error -> Text(text = "error retry")
//            is homeUiState.Success -> LazyColumn(
//                modifier = Modifier.padding(horizontal = 5.dp)
//            ) {
//                items((status.value as homeUiState.Success).postsData){
//                        item -> PostCard(postItem = item)
//                }
//            }
//        }
        Text(text = "welcome", style = MaterialTheme.typography.titleLarge)

    }
}

@Composable
fun PostCard(postItem: post){

    Column {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.laralogo),
                contentDescription = "username",
                modifier = Modifier
                    .height(44.dp)
                    .width(44.dp)
                    .padding(5.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = postItem.rollno,
                    style = MaterialTheme.typography.labelLarge
                )//postItem.rollNo
                Text(
                    text = postItem.name,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = postItem.message, style = MaterialTheme.typography.bodyLarge)

            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colorScheme.outline)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    AppTheme {
//        PostCard(postItem = post(1, "22FE1A6129", "anand", "hello everyone"))
        HomePage()
    }
}
