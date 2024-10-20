package com.example.laara.screens.marks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laara.ui.theme.AppTheme


@Composable
fun MarksPage(){
    val vm:MarksVM = viewModel()
    vm.getSubjectList()
    val scroll = rememberScrollState()
    val vmui = vm.uiState
    when(vmui){
//        is marksUiState.Success -> Column (modifier = Modifier.verticalScroll(scroll)){
//            LazyColumn {
//                items(items = vmui.data){item ->
//                    
//                }
//            }
//        }
        is marksUiState.Success ->
            Column {
                Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)){
                    SemCard(data = vmui.data)
                }

                Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)){
                    SemCard(data = vmui.data)
                }
                Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)){
                    SemCard(data = vmui.data)
                }
            }
        is marksUiState.Error -> Text(text = "Error..")
        is marksUiState.Loading -> Text(text = "loading..")
    }
}


@Composable
fun SemCard(data:List<subjectMarks>){
    val cellModifier = Modifier.width(100.dp)
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "sem 1", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
        LazyColumn {
            items(data){
                    item ->
                Row() {

                    Text(text = item.subjectName, modifier = Modifier.fillMaxWidth(0.5f))
                    Text(text = item.internals.toString(), modifier = Modifier.fillMaxWidth(0.25f))
                    Text(text = item.grade, modifier = Modifier.fillMaxWidth(0.25f))
                    Text(text = item.completed, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun prevuioh() {
    AppTheme {
        MarksPage()
    }
}