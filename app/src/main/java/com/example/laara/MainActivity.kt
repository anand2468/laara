package com.example.laara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.laara.bottomNavBar.AppNavGraph
import com.example.laara.bottomNavBar.BottomNavBar
import com.example.laara.bottomNavBar.NavItems
import com.example.laara.bottomNavBar.TopBar
import com.example.laara.preferences.loginSharedPref
import com.example.laara.ui.theme.AppTheme
import com.example.laara.ui.theme.LaaraTheme
import kotlinx.coroutines.flow.first

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaaraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navItems = listOf(
        NavItems.Home,
        NavItems.Marks,
        NavItems.Profile
    )
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val context = LocalContext.current
    var loading by remember { mutableStateOf(true) }
    var startDestination by remember { mutableStateOf(NavItems.login.route) }


    LaunchedEffect(Unit) {
        val loginPref = loginSharedPref(context)
        if (loginPref.status.first() == true){
            startDestination = NavItems.Home.route
        }
        loading = false
    }
    if(loading){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "loading.....")
            return
        }
    }

    Scaffold(
            topBar = {
                if (currentRoute == NavItems.Home.route) {
                    TopBar()
                }
            },
            bottomBar = {

                if (currentRoute != NavItems.login.route) {
                    BottomNavBar(navController = navController, navItems = navItems)
                }
            }
        ) {
            AppNavGraph(
                navController = navController,
                startDestination = startDestination,
                Modifier.padding(it)
            )
        }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        App()
    }
}