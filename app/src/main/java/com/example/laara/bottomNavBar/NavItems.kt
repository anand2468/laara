package com.example.laara.bottomNavBar

import com.example.laara.R

//sealed class NavItems(
//    val route:String,
//    val icon: ImageVector,
//    val selectedIcon:ImageVector
//) {
//    object Home:NavItems("Home", Icons.Outlined.Home, Icons.Filled.Home)
//    object Marks:NavItems("Marks", Icons.Outlined.FavoriteBorder , Icons.Filled.Favorite)
//    object  Profile:NavItems("profile", Icons.Outlined.Person, Icons.Filled.Person)
//}

sealed class NavItems(
    val route:String,
    val icon: Int,
    val selectedIcon: Int
) {
    object Home:NavItems("Home", R.drawable.home_outline, R.drawable.home_filled)
    object Marks:NavItems("Marks",R.drawable.marks_outline, R.drawable.marks_filled)
    object  Profile:NavItems("profile", R.drawable.profile_outline, R.drawable.profile_filled)
    object login:NavItems("login", R.drawable.marks_outline, R.drawable.marks_outline, )
}