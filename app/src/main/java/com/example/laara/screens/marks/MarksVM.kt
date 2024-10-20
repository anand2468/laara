package com.example.laara.screens.marks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

sealed interface marksUiState{
    data class Success(val data:List<subjectMarks>):marksUiState
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

data class subjectMarks(
    val subjectName:String,
    val grade:String,
    val internals:Int,
    val completed:String)

class MarksVM:ViewModel() {
    init {
//        getData()
    }

    var uiState:marksUiState by mutableStateOf(marksUiState.Loading)

//    fun getData(){
//        viewModelScope.launch {
//            uiState = try {
//                val subjectList = loginService.getServiceApi.getSubjects("r20_1_2_AIML_subjects")
////                var result = loginService.getServiceApi.getResults("22_1_2_AIML", "22FE1A6129")
//
//
//                Log.d("LARA_LIVE", subjectList.toString())
//                marksUiState.Success(subjectList)
//            }catch (e:IOException){
//                marksUiState.Error
//            }
//        }
//    }

    fun getSubjectList(){
        val data = listOf(
            subjectMarks("computer organisation", "A", 29, "dec 23"),
            subjectMarks("dwdm", "A", 29, "dec 23"),
            subjectMarks("flat", "A", 29, "dec 23"),
            subjectMarks("mefa", "A", 29, "dec 23"),
            subjectMarks("p&s", "A", 29, "dec 23"),

        )
        uiState = marksUiState.Success( data )
    }

}