package com.example.laara.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.laara.R
import com.example.laara.bottomNavBar.NavItems
import com.example.laara.ui.theme.AppTheme

@Composable
fun Profile(navController:NavController, vm: ProfileVM){
    val context = LocalContext.current
    vm.getCreds(context)
    val data = vm.uiState.collectAsState().value
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)) {
            Row (modifier= Modifier
                .padding(10.dp)
                .fillMaxWidth()){
                Image(painter = painterResource(id = R.drawable.laralogo),
                    contentDescription = "profile picture",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    contentScale = ContentScale.Crop)

                Column(modifier = Modifier
                    .padding()
                    .fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = data.CollegeId,
                        style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(text = data.Name, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)){
            Column (modifier = Modifier.padding(10.dp)){
                Text(text = "contact info:",
                    Modifier.padding(5.dp), style = MaterialTheme.typography.titleMedium)

                Row {
                    Text(text = "phone number:", Modifier.padding(5.dp), style = MaterialTheme.typography.bodyLarge)
                    Text(text = data.MobileNumber, Modifier.padding(5.dp))
                }
                Row {
                    Text(text = "email:", Modifier.padding(5.dp))
                    Text(text = data.MailId, Modifier.padding(5.dp))
                }
            }
        }
        Button(
            modifier = Modifier.padding(10.dp) ,
            onClick = {
                /* todo */
                vm.removeCreds(context)
            navController.navigate(NavItems.login.route){
                popUpTo(0){
                    inclusive = true
                }
            }
        }) {
            Text(text = "log out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    AppTheme {

    }
}