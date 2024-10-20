package com.example.laara.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.laara.R
import com.example.laara.bottomNavBar.NavItems
import com.example.laara.internet.loginClass
import com.example.laara.preferences.loginSharedPref
import kotlinx.coroutines.launch

@Composable
fun Login(navController:NavController, vm:LoginVM = viewModel()){
    val vmUI = vm.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scroll = rememberScrollState()
    val offset by remember { mutableStateOf(0f) }

    val tfModifier = Modifier
        .fillMaxWidth()
    val loginPref = loginSharedPref(context)
    if (vmUI.value.loggedIn){
        navController.navigate(NavItems.Home.route){
            popUpTo(NavItems.Home.route)
        }
    }
    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .scrollable(orientation = Orientation.Vertical, state = scroll)
    ){
        Spacer(modifier = Modifier.height(50.dp))
        Image(painter = painterResource(id = R.drawable.laralogo),
            contentDescription =null,
            modifier= Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .width(150.dp)
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "WELCOME BACK",
            modifier = Modifier
                .padding(5.dp)
                .align(alignment = Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp)

        OutlinedTextField(value = vmUI.value.username,
            onValueChange = {if(it.length <=10){vm.updateUsername(it)}
                            vm.removeErrors()},
            modifier = tfModifier,
            label = { Text(text = "Roll Number") },
            isError = vmUI.value.usernameError,
            singleLine = true,
            supportingText = {
                if(vmUI.value.usernameError){
                    Text(text = "Invalid rollNo")
                }
            })

        OutlinedTextField(value = vmUI.value.password,
            onValueChange = {vm.updatePassword(it)},
            modifier = tfModifier,
            label = { Text(text = "Password") },
            singleLine = true,
            isError =  vmUI.value.passwordError,
            supportingText = {
                if (vmUI.value.passwordError){
                    Text(text = "Invalid password")
                }
            }
            )

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { scope.launch {
                loginPref.setCredentials(loginClass(Name = "admin"))
                navController.navigate(NavItems.Home.route)
            } },
                modifier = Modifier
                    .padding( end = 10.dp)
                    .fillMaxWidth(0.5f)
                    .background(shape = RoundedCornerShape(4.dp), color = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Preview", fontSize = 20.sp, fontWeight = FontWeight.Normal)
            }
            Button(onClick = { vm.checkUser(context) },
                modifier = Modifier
//                    .padding(top = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .background(shape = RoundedCornerShape(4.dp), color = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Normal)
            }
        }
        Text(text = vmUI.value.logStatus)
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
Login(navController = rememberNavController())
}