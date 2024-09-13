package com.example.laara.screens.marks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun marksPage(){
    val vm:MarksVM = viewModel()
    val scroll = rememberScrollState()
    val vmui = vm.uiState
    when(vmui){
        is marksUiState.Success -> Column (modifier = Modifier.verticalScroll(scroll)){
            LazyColumn {
                items(items = vmui.data){item ->
                    Text(text = item.SubjectName)
                }
            }
        }
        is marksUiState.Error -> Text(text = "Error..")
        is marksUiState.Loading -> Text(text = "loading..")
    }
}

