package com.example.laara.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laara.internet.loginClass
import com.example.laara.preferences.loginSharedPref
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileVM():ViewModel() {
    private val _uiState = MutableStateFlow(loginClass())
    val uiState = _uiState.asStateFlow()
    private var isDataLoaded = false

    fun getCreds(context: Context){
        if( isDataLoaded == false){
            viewModelScope.launch {
                loginSharedPref(context = context).getCredentials.collect{
                        prefs -> _uiState.update {
                        cur -> cur.copy(
                    Name= prefs.Name,
                    CollegeId = prefs.CollegeId,
                    MailId = prefs.MailId,
                    MobileNumber = prefs.MobileNumber,
                )
                }
                }
            }
            isDataLoaded = true
        }
    }

    fun removeCreds(context: Context){
        viewModelScope.launch {
            loginSharedPref(context).removeUserName()

        }
    }

}