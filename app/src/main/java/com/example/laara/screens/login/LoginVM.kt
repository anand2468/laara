package com.example.laara.screens.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laara.internet.loginClass
import com.example.laara.internet.loginService
import com.example.laara.preferences.loginSharedPref
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.io.IOException


data class loginUIState(
    var username:String="",
    var password:String = "",
    var usernameError:Boolean = false,
    var passwordError:Boolean = false,
    var loggedIn:Boolean = false,
    var logStatus:String = " "
)

sealed interface FetchStatus{
    class Success( val Status:loginClass ):FetchStatus
    object Loading:FetchStatus
    class Error(val error:String):FetchStatus
}

class LoginVM:ViewModel() {
    private var _uiState = MutableStateFlow(loginUIState())
    var uiState = _uiState.asStateFlow()


    fun updateUsername(value:String){
        _uiState.update { currentState -> currentState.copy(username = value) }
    }
    fun updatePassword(value:String){
        _uiState.update { currentState -> currentState.copy(password = value) }
    }

    fun checkUser(context: Context){
        viewModelScope.launch{
            try {
                var results = loginService.getServiceApi.CheckUser(_uiState.value.username, _uiState.value.password)[0]
                when(results.UserStatus){
                    "User Not Found" -> _uiState.update { currentState -> currentState.copy(usernameError = true) }
                    "User Found but Wrong Credentials" -> _uiState.update { currentState -> currentState.copy(passwordError = true) }
                    "User Found with Correct Credentials" -> {
                        loginSharedPref(context).setCredentials(results)
                        _uiState.update { currentState -> currentState.copy(loggedIn = true) }
                    }
                }
//                _uiState.update { currentState -> currentState.copy(logStatus = "res is $results, ${gs.UserStatus}") }
            }
            catch (e:IOException){
                Log.d("LOGINVM", "res is $e")
                _uiState.update {it.copy(logStatus = "$e") }
            }
        }
    }


//    if(_uiState.value.username == "admin" && _uiState.value.password == "admin"){
//        Log.d("LOGINVM", "IF CONDITION IS TRUE")
//        return true
//    }
//    else{
//        Log.d("LOGINVM", "IF IS FALSE")
//        _uiState.update { curState -> curState.copy(passwordError = true, usernameError = true) }
//        return false
//    }

    fun removeErrors(){
        if (_uiState.value.usernameError || uiState.value.passwordError){
            _uiState.update {it->
                it.copy( usernameError = false, passwordError = false)
            }
        }
    }
}