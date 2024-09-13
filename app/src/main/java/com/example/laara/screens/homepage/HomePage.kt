package com.example.laara.screens.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laara.R
import com.example.laara.ui.theme.AppTheme

//data class HomePageCards( val imageResource:Int, val stringRes:String)
//
//
//data class Post(val username:String,
//                val rollNo:String,
//                val profile:Int,
//                val content:String,
//                )
//class PostsData(){
//    fun loadOfflinePosts():List<Post>{
//        return listOf(
//            Post("Anand","22FE1A6129", R.drawable.img3, "hello how are doing" ),
//            Post("sai","22FE1A6128", R.drawable.mylove, "im so bored can u talk to me sometime " ),
//            Post("Anand","22FE1A6129", R.drawable.img3, "ofc i will do anythong for you \ni will anythin" ),
//            Post("sai","22FE1A6128", R.drawable.mylove, "stop kidding " ),
//            Post("Anand","22FE1A6129", R.drawable.img3, "im not kidding its true " ),
//            Post("sai","22FE1A6128", R.drawable.mylove, "ok  " ),
//            Post("Anand","22FE1A6129", R.drawable.img3, "yep, you look very cute today" ),
//            Post("sai","22FE1A6128", R.drawable.mylove, "thank you, i wore it for you" ),
//            Post("Anand","22FE1A6129", R.drawable.img3, "awww.. im very lucy to have you" ),
//            Post("sai","22FE1A6128", R.drawable.mylove, "hahaha " ),
//            Post("Anand","22FE1A6129", R.drawable.img3, "you i leterally missed you so much " ),
//            Post("sai","22FE1A6128", R.drawable.mylove, "me to lets have some fun " ),
//
//        )
//    }
//}



@Composable
fun HomePage(vm: HomeVM){
//    val postItems = PostsData().loadOfflinePosts()
    Column {
        val status = vm.uiState

        when(status){
            is homeUiState.Loading -> Text(text = "loading")
            is homeUiState.Error -> Text(text = "error retry")
            is homeUiState.Success -> LazyColumn(
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                items(status.postsData){
                        item -> PostCard(postItem = item)
                }
            }
        }

    }
}

@Composable
fun PostCard(postItem: post){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row {
                Image(painter = painterResource(id = R.drawable.laralogo),
                    contentDescription = "username",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .padding(5.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)
                Column {
                    Text(text = postItem.rollno)//postItem.rollNo
                    Text(text = postItem.name)
                }
            }
            Text(text = postItem.message)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    AppTheme {
        HomePage(viewModel())
    }
}
