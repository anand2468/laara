package com.example.laara.screens.homepage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

sealed interface homeUiState{
    data class Success(var postsData:List<post>): homeUiState
    object Loading: homeUiState
    object Error: homeUiState
}

class HomeVM: ViewModel(){
    init {
        loadPosts()
    }
    var uiState: homeUiState by mutableStateOf(homeUiState.Loading)

    /*
    private fun loadPosts(){
        viewModelScope.launch {
            uiState = try {
                val posts = postServiceApi.fetchPhotos.getPosts()
                homeUiState.Success(posts)
            }catch(e:IOException){
                Log.d("NET_CONNECTION", "THE ERROR IS $e")
                homeUiState.Error
            }
        }
    }*/

    private fun loadPosts(){
        val db = Firebase.firestore
        db.collection("posts").get()
            .addOnSuccessListener { result ->
                var postdt = listOf<post>()
                for (document in result){
                    postdt = postdt + post(rollno = document.data.get("by").toString(),
                        message = document.data.get("message").toString(), pid = 5, name = document.data.get("by").toString())
                }
                uiState = homeUiState.Success(postdt)
            }
            .addOnFailureListener { exception ->
                Log.d("MAIN_ACTIVITY","exveptions ${exception}")
                uiState = homeUiState.Error
            }
    }
}