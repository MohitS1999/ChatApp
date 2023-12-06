package com.mohit.chatapp.util

import androidx.navigation.NavController

fun navigateToLogin(navController: NavController,route: String){
    navController.navigate(route){
        popUpTo(route)
        launchSingleTop
    }

}