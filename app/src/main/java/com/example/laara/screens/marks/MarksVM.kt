package com.example.laara.screens.marks

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laara.internet.loginService
import com.example.laara.internet.subList
import kotlinx.coroutines.launch
import okio.IOException

sealed interface marksUiState{
    data class Success(val data:List<subList>):marksUiState
    object Loading:marksUiState
    object Error:marksUiState
}

//fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
//    when (val value = this[it])
//    {
//        is JSONArray ->
//        {
//            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
//            JSONObject(map).toMap().values.toList()
//        }
//        is JSONObject -> value.toMap()
//        JSONObject.NULL -> null
//        else            -> value
//    }
//}

class MarksVM:ViewModel() {
    init {
        getData()
    }

    var uiState:marksUiState by mutableStateOf(marksUiState.Loading)

    fun getData(){
        viewModelScope.launch {
            uiState = try {
                val subjectList = loginService.getServiceApi.getSubjects("r20_1_2_AIML_subjects")
//                var result = loginService.getServiceApi.getResults("22_1_2_AIML", "22FE1A6129")


                Log.d("LARA_LIVE", subjectList.toString())
                marksUiState.Success(subjectList)
            }catch (e:IOException){
                marksUiState.Error
            }
        }
    }

}