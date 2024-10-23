package com.example.laara.screens.marks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laara.internet.subList
import com.example.laara.ui.theme.AppTheme


@Composable
fun MarksPage(vm:MarksVM = viewModel()){
    val scroll = rememberScrollState()
    val vmui = vm.ui.collectAsState()
    LazyColumn {
        items(items = vmui.value){
            item -> SemCard(data = item)
        }
    }
}


@Composable
fun SemCard(data:SemStatus){
    val state = data.uiState.collectAsState()
    val context = LocalContext.current
    val textmod = MaterialTheme.typography.bodySmall
    LaunchedEffect( Unit ) {
        data.getData(context)
    }

    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Text(
            text = "sem ${data.semister}",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
        )
        when(state.value) {
            is MarksUI.Loading -> Text(text = "loading...")
            is MarksUI.Error -> Text(text = "no data found or check ur connection !!")
            is MarksUI.Success ->
            Column {
                (state.value as MarksUI.Success).subjects.forEach {
                    item ->
                        Row {
                            Text(text = item.SubjectName,
                                modifier = Modifier.fillMaxWidth(0.4f),
                                style = textmod,
                                maxLines = 2)
//                    Text(text = item.SubjectCode, modifier = Modifier.fillMaxWidth(0.3f))
                            Text(
                                text =(state.value as MarksUI.Success).results.get(item.SubjectCode).toString(),
                                modifier = Modifier.fillMaxWidth(1f),
                                style = textmod
                            )
//                    Text(text = data.results.get(item.SubjectCode).toString(), modifier = Modifier.fillMaxWidth())
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .align(Alignment.CenterHorizontally)
                                .background(MaterialTheme.colorScheme.outline)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
//
@Composable
private fun semcardPrev() {
    val data = MarksUI.Success(
        subjects = listOf(
            subList(SubjectCode = "R201101", SubjectName = "MATHEMATICS - I"),
            subList(SubjectCode = "R201102", SubjectName = "COMMUNICATIVE ENGLISH"),
            subList(
                SubjectCode = "R201106",
                SubjectName = "ENGLISH COMMUNICATION SKILLS LABORATORY"
            ),
            subList(
                SubjectCode = "R201110",
                SubjectName = "PROGRAMMING FOR PROBLEM SOLVING USING C"
            ),
            subList(
                SubjectCode = "R201113",
                SubjectName = "PROGRAMMING FOR PROBLEM SOLVING USING C"
            ),
            subList(SubjectCode = "R201114", SubjectName = "ENVIRONMENTAL SCIENCE"),
            subList(SubjectCode = "R201115", SubjectName = "APPLIED CHEMISTRY"),
            subList(SubjectCode = "R201116", SubjectName = "APPLIED CHEMISTRY LAB"),
            subList(SubjectCode = "R201118", SubjectName = "COMPUTER ENGINEERING WORKSHOP")
        ),
        results = mapOf(
            "CollegeId" to "22FE1A6144",
            "R201101" to "(E,3,Feb 2023 RCRV)",
            "R201102" to "(C,3,Feb 2023)",
            "R201106" to "(A+,1.5,Feb 2023)",
            "R201110" to "(C,3,Feb 2023)",
            "R201113" to "(A+,1.5,Feb 2023)",
            "R201114" to "(COMPLETE,0,Feb 2023)",
            "R201115" to "(E,3,Feb 2023)",
            "R201116" to "(A+,1.5,Feb 2023)",
            "R201118" to "(A+,3,Feb 2023)"
        )
    )
    val textmod = MaterialTheme.typography.bodySmall
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "sem 1_1",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
        )
        Column {
            data.subjects.forEach { item ->
                Row {
                    Text(text = item.SubjectName,
                        modifier = Modifier.fillMaxWidth(0.4f),
                        style = textmod,
                        maxLines = 2)
//                    Text(text = item.SubjectCode, modifier = Modifier.fillMaxWidth(0.3f))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = data.results.get(item.SubjectCode).toString(),
                        modifier = Modifier.fillMaxWidth(1f),
                        style = textmod
                    )
//                    Text(text = data.results.get(item.SubjectCode).toString(), modifier = Modifier.fillMaxWidth())
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(MaterialTheme.colorScheme.outline)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun prevuioh() {
    AppTheme {
        semcardPrev()
    }
}