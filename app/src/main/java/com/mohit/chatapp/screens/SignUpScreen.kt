package com.mohit.chatapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.IconCompatParcelizer
import androidx.navigation.NavController
import com.mohit.chatapp.ChatViewModel
import com.mohit.chatapp.DestinationScreen
import com.mohit.chatapp.R
import com.mohit.chatapp.util.CheckSignedIn
import com.mohit.chatapp.util.CommonProgressBar
import com.mohit.chatapp.util.navigateToLogin


@Composable
fun SignUpScreen(navController: NavController, viewModel: ChatViewModel) {
    CheckSignedIn(viewModel = viewModel, navController = navController )
    Box (modifier = Modifier.fillMaxSize()){
        Column (modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally){
            val nameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val numberState = remember {
                mutableStateOf(TextFieldValue())
            }
            val emailState = remember {
                mutableStateOf(TextFieldValue())
            }
            val passwordState = remember {
                mutableStateOf(TextFieldValue())
            }
            var showPassword by remember { mutableStateOf(value = false) }
            val focus = LocalFocusManager.current
            Image(painter = painterResource(id = R.drawable.chat), contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            Text(text = "Sign Up",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = nameState.value,
                onValueChange = {
                    nameState.value = it
                },
                label = { Text(text = "Name")},
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = numberState.value,
                onValueChange = {
                    numberState.value = it
                },
                label = { Text(text = "Number")},
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = emailState.value,
                onValueChange = {
                    emailState.value = it
                },
                label = { Text(text = "Email  ")},
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = passwordState.value,
                onValueChange = {
                    passwordState.value = it
                },
                label = { Text(text = "Password")},
                modifier = Modifier.padding(8.dp),
                placeholder = { Text(text = "Type password here")},
                shape = RoundedCornerShape(percent = 20),
                visualTransformation = if (showPassword){
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (showPassword){
                        IconButton(onClick = {showPassword = false}) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password"
                            )
                        }
                    }else{
                        IconButton(onClick = {showPassword = true}) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "show_password"
                            )
                        }
                    }
                }
            )
            Button(onClick = {viewModel.signUp(
                nameState.value.text,
                numberState.value.text,
                emailState.value.text,
                passwordState.value.text
            )},
                modifier = Modifier.padding(8.dp)) {
                Text(text = "Sign Up")
            }
            Text(text = "Already a User? Go to Login ->",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateToLogin(navController, DestinationScreen.Login.route)
                    }
            )
        }
    }
    if(viewModel.inProcess.value){
        CommonProgressBar()
    }
}