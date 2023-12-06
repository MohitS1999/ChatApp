package com.mohit.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohit.chatapp.screens.ChatListScreen
import com.mohit.chatapp.screens.LoginScreen
import com.mohit.chatapp.screens.SignUpScreen
import com.mohit.chatapp.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint


sealed class DestinationScreen(var route:String){
    object SignUp : DestinationScreen("signup")
    object Login : DestinationScreen("login")
    object Profile : DestinationScreen("profile")
    object ChatList : DestinationScreen("chatList")
    object SingleChat : DestinationScreen("singleChat/{chatId}"){
        fun createRoute(id:String) = "singleChat/$id"
    }

    object StatusList : DestinationScreen("statusList")
    object SingleStatus : DestinationScreen("singleStatus/{userId}"){
        fun createRoute(userId:String) = "singleStatus/$userId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }
            }
        }
    }
    @Composable
    fun ChatAppNavigation(){
        val navController = rememberNavController()
        var viewModel = hiltViewModel<ChatViewModel>()
        NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route){
            composable(DestinationScreen.SignUp.route){
                SignUpScreen(navController,viewModel)
            }
            composable(DestinationScreen.Login.route){
                LoginScreen()
            }
            composable(DestinationScreen.ChatList.route){
                ChatListScreen()
            }
        }
    }
}
