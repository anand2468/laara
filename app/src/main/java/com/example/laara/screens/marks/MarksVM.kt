package com.example.laara.screens.marks

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laara.internet.loginService
import com.example.laara.internet.subList
import com.example.laara.preferences.loginSharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okio.IOException


data class subjectMarks(
    val subjectName:String,
    val grade:String,
    val internals:Int,
    val completed:String)

data class academics(
    val branch:String,
    val batch:String,
    val regulation:String,
    val collegeId:String
)

sealed interface MarksUI{
    data class Success(val subjects:List<subList>, val results:Map<String, Any>):MarksUI{

    }//, val results:Map<String, Any>
    object Loading:MarksUI
    object Error:MarksUI
}

class SemStatus(val semister:String, val viewModelScope:CoroutineScope){
    private val _uiState:MutableStateFlow<MarksUI> = MutableStateFlow(MarksUI.Loading)
    val uiState = _uiState.asStateFlow()
    suspend fun getData( context: Context){
        val data:academics = loginSharedPref(context).getAcademics.first()
        viewModelScope.launch {
            try {
                Log.d("MARKS_MODULE", " TABLE NAME IS ${data.regulation}_${semister}_${data.branch}_subjects table ${data.batch}_${semister}_${data.branch}, clg id ${data.collegeId } ")
                val load_subjects = loginService.getServiceApi.getSubjects("${data.regulation}_${semister}_${data.branch}_subjects")
                val load_results = loginService.getServiceApi.getResults("${data.batch}_${semister}_${data.branch}",data.collegeId )
                if (load_results.isNotEmpty()){
                    _uiState.value = MarksUI.Success(load_subjects, load_results[0])
                }
                else{
                    _uiState.value = MarksUI.Error
                }
                Log.d("MARKS_MODULE", "$load_subjects \n ${load_results}")

            }catch (error:IOException){
                _uiState.value = MarksUI.Error
                Log.d("MARKS_MODULE", "error occurred ${error}")
            }
        }
    }
}

class MarksVM:ViewModel() {

    private var _uiState:MutableStateFlow<MarksUI> = MutableStateFlow(MarksUI.Loading)
    private var _State = MutableStateFlow(listOf(SemStatus("1_1", viewModelScope),
        SemStatus("1_2", viewModelScope),
        SemStatus("2_1", viewModelScope),
        SemStatus("2_2", viewModelScope),
        SemStatus("3_1", viewModelScope),
        SemStatus("3_2", viewModelScope),
        SemStatus("4_1", viewModelScope),
        SemStatus("4_2", viewModelScope)
        ))
    val ui = _State.asStateFlow()

//    fun getSubjectData(data: academics){
//        viewModelScope.launch {
//            try {
//                val load_subjects = loginService.getServiceApi.getSubjects("${data.regulation}_${data.sem}_${data.branch}_subjects")
//                val load_results = loginService.getServiceApi.getResults("${data.batch}_${data.sem}_${data.batch}",data.collegeId, )[0]
//                Log.d("MARKS_MODULE", "$load_subjects \n ${load_results.get("R201201")!!::class.simpleName} ")
//                _uiState.value = MarksUI.Success(load_subjects, load_results)
//            }catch (error:IOException){
//                _uiState.value = MarksUI.Error
//                Log.d("MARKS_MODULE", "error occurred ${error}")
//            }
//        }
//    }

}