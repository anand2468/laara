package com.example.laara.screens.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface homeUiState{
    data class Success(var postsData:List<post>): homeUiState
    object Loading: homeUiState
    object Error: homeUiState
}

class HomeVM: ViewModel(){
//    init {
////        loadPosts() //todo:remove this
//        loadDummyPosts()
//    }
    var _uiState:MutableStateFlow<homeUiState> = MutableStateFlow(homeUiState.Loading)
    val uiState = _uiState.asStateFlow()
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
                _uiState.value = homeUiState.Success(postdt)
            }
            .addOnFailureListener { exception ->
                Log.d("MAIN_ACTIVITY","exveptions ${exception}")
                _uiState.value = homeUiState.Error
            }
    }

    fun loadDummyPosts(){
        val postdt = listOf(
            post(1, "22FE1A6129", "ANAND", " Helll every one"),
            post(2, "22FE1A6148", "yaswanth", "hii"),
            post(3, "22FE1A6129", "ANAND", " Helll every one"),
            post(4, "22FE1A6148", "yaswanth", "???"),
            post(5, "22FE1A6129", "ANAND", " Helll every one"),
            post(1, "22FE1A6129", "ANAND", " Helll every one"),
            post(2, "22FE1A6148", "yaswanth", "hii"),
            post(3, "22FE1A6129", "ANAND", " Helll every one"),
            post(4, "22FE1A6148", "yaswanth", "???"),
            post(5, "22FE1A6129", "ANAND", " Helll every one"),
            post(1, "22FE1A6129", "ANAND", " Helll every one"),
            post(2, "22FE1A6148", "yaswanth", "hii"),
            post(3, "22FE1A6129", "ANAND", " Helll every one"),
            post(4, "22FE1A6148", "yaswanth", "???"),
            post(5, "22FE1A6129", "ANAND", " Helll every one"),
            )
        _uiState.value = homeUiState.Success(postdt)
    }
}