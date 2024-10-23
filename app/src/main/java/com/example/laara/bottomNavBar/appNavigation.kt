package com.example.laara.bottomNavBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.laara.R
import com.example.laara.screens.homepage.HomePage
import com.example.laara.screens.homepage.HomeVM
import com.example.laara.screens.login.Login
import com.example.laara.screens.marks.MarksPage
import com.example.laara.screens.marks.MarksVM
import com.example.laara.screens.profile.Profile
import com.example.laara.screens.profile.ProfileVM
import com.example.laara.ui.theme.AppTheme


@Composable
fun TopBar(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painter = painterResource(id = R.drawable.laralogo),
            contentDescription = "Lara Logo",
            modifier = Modifier.height(42.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = "LAARA",
            fontSize = 40.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BottomNavBar(navController:NavController, navItems:List<NavItems>){

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    NavigationBar {
        navItems.forEach{
                navItem ->
            val ifSelected = currentDestination?.hierarchy?.any{ it.route == navItem.route} == true
            NavigationBarItem(
                selected = ifSelected,
                onClick = { navController.navigate(navItem.route){
                    popUpTo(NavItems.Home.route)
                } },
                icon = { if(ifSelected){
                    Icon(painterResource(id = navItem.selectedIcon), contentDescription = null)
                }else{
                    Icon(painterResource(id = navItem.icon), contentDescription = null)
                }
                })
        }
    }
}


@Composable
fun AppNavGraph(navController: NavHostController, startDestination:String, modifier: Modifier){
    val homeVm: HomeVM = viewModel()
    val profileVm: ProfileVM = viewModel()
    val marksVM:MarksVM = viewModel()
            NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier){

        //loginPage
        composable(NavItems.login.route){
            Login(navController)
        }

        /* home page*/
        composable(NavItems.Home.route){
            Column {
                HomePage(vm = homeVm)
            }
        }

        //Marks page
        composable(NavItems.Marks.route){
            MarksPage(marksVM)
        }

        //profile page
        composable(NavItems.Profile.route){
            Column {
                Profile(navController, profileVm)
            }
        }
    }
}

@Composable
fun AddPostFAB(){
    Button(onClick = { /*TODO*/ }) {
        Row (horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Filled.Edit, contentDescription = null)
            Text(text = "Add post")
        }
    }
}
@Preview
@Composable
private fun fadpev() {
    AppTheme {
        AddPostFAB()
    }

}