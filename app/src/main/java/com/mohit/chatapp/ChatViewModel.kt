package com.mohit.chatapp

import android.util.Log
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.mohit.chatapp.data.Event
import com.mohit.chatapp.data.USER_NODE
import com.mohit.chatapp.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "ChatViewModel"
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private var db:FirebaseFirestore
) : ViewModel(){

    var inProcess = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Event<String>?>(null)
    var signIn = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }
    fun signUp(name:String,number:String,email:String,password:String){
        inProcess.value = true
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                signIn.value = true
                createOrUpdateProfile(name,number)
                Log.d(TAG, "signUp: User logged In")

            }else{
                handleException(it.exception,"Sign Up Failed")
            }
        }
    }
    fun createOrUpdateProfile(name:String?=null,number:String?=null,imageUrl:String?=null){
        var uId = auth.currentUser?.uid
        val userData = UserData(
            userId = uId,
            name = name?:userData.value?.name,
            number = number?:userData.value?.number,
            imageUrl = imageUrl?:userData.value?.imageUrl
        )

        uId?.let{
            inProcess.value = true
            db.collection(USER_NODE).document(uId).get().addOnSuccessListener {
                if(it.exists()){
                    //update user data
                }else{
                    db.collection(USER_NODE).document(uId).set(userData)
                    inProcess.value=false
                    getUserData(uId)
                }
            }
                .addOnFailureListener {
                    handleException(it,"Cannot Retrieve User")
                }
        }
    }

    private fun getUserData(uId:String) {
        inProcess.value = true
        db.collection(USER_NODE).document(uId).addSnapshotListener { value, error ->
            if(error != null){
                handleException(error,"Can't retrieve User")
            }
            if(value != null){
                val user = value.toObject<UserData>()
                userData.value = user
                inProcess.value = false
            }
        }
    }

    fun handleException(exception: Exception?=null,customMessage:String=""){
        Log.e(TAG,"Live chat Exception: ",exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage?:""
        val message = if(customMessage.isNullOrEmpty()) errorMsg else customMessage
        eventMutableState.value = Event(message)
        inProcess.value = false
    }

}
