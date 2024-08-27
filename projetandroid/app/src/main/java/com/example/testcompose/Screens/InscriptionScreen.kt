package com.example.testcompose.Screens


import android.content.Intent
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.testcompose.InterfacesAPI.CREATE_DRESSEUR
import com.example.testcompose.MainActivity
import com.example.testcompose.PokedexActivity
import com.example.testcompose.R
import com.example.testcompose.objects.AnswerServer
import com.example.testcompose.ui.theme.Routes
import com.example.testcompose.viewModel.DresseurViewModel
import retrofit2.*
import java.math.BigInteger
import java.security.MessageDigest


@Composable
fun ShowError(msg: String)
{
    Text(text = msg, style = TextStyle(Color.Red, fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscriptionScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF001527)
    ) {}
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val pseudo = remember { mutableStateOf(TextFieldValue()) }
        val email = remember { mutableStateOf(TextFieldValue()) }
        val mdp = remember { mutableStateOf(TextFieldValue()) }
        val mdp_confirm = remember { mutableStateOf(TextFieldValue()) }

        val showErrorMsg = remember { mutableStateOf(0) }
        val redirectLogin = remember { mutableStateOf(false) }
        val redirectMain = remember { mutableStateOf(false) }

        Image(
            painter = painterResource(id = R.drawable.polypoke_logo),
            contentDescription = "Logo de l'application",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Inscription", style = TextStyle(fontSize = 40.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Pseudo", style = TextStyle(fontFamily = FontFamily(Font(R.font.trebuc)), color=Color.White)) },
            value = pseudo.value,
            onValueChange = { pseudo.value = it },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color(0, 63, 122), textColor = Color.White))


        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Email", style = TextStyle(fontFamily = FontFamily(Font(R.font.trebuc)), color=Color.White)) },
            value = email.value,
            onValueChange = { email.value = it },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color(0, 63, 122), textColor = Color.White))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Mot de passe", style = TextStyle(fontFamily = FontFamily(Font(R.font.trebuc)), color=Color.White)) },
            value = mdp.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { mdp.value = it },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color(0, 63, 122), textColor = Color.White))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Confirmer le mot de passe", style = TextStyle(fontFamily = FontFamily(Font(R.font.trebuc)), color=Color.White)) },
            value = mdp_confirm.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { mdp_confirm.value = it },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color(0, 63, 122), textColor = Color.White))

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    fun String.sha256(): String {
                        val md = MessageDigest.getInstance("SHA-256")
                        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
                    }
                    fun isValidEmail(email : String) : Boolean{
                        return if (TextUtils.isEmpty(email)) {
                            false;
                        } else {
                            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                        }
                    }
                    if (email.value.text == "" || mdp.value.text == "" || mdp_confirm.value.text == "") {
                        showErrorMsg.value = 1
                    }
                    else if (!isValidEmail(email.value.text)) {
                        showErrorMsg.value = 2
                    }
                    else if (mdp.value.text != mdp_confirm.value.text) {
                        showErrorMsg.value = 3
                    }
                    else {
                        var reponse_server : AnswerServer
                        CREATE_DRESSEUR(email.value.text, pseudo.value.text, mdp.value.text.sha256(), object : retrofit2.Callback<AnswerServer> {
                            override fun onResponse(call: Call<AnswerServer>, response: Response<AnswerServer>) {

                                if (response.body() != null && response.isSuccessful == true) {
                                    reponse_server = response.body()!! //on récupère l'objet

                                    if (reponse_server.type == "REUSSITE") {
                                        //CREATION DE COMPTE RÉUSSIE
                                        Log.d("REQ CREATE DRESSEUR SERVER", reponse_server.message)
                                        redirectMain.value = true
                                    }
                                    else if (reponse_server.type == "ERREUR"){
                                        //ECHEC : MAIL DÉJA ASSOCIE A UN COMPTE
                                        //CREATION DE COMPTE RÉUSSIE
                                        Log.d("REQ CREATE DRESSEUR SERVER", reponse_server.message)
                                    }
                                }
                            }
                            override fun onFailure(call: Call<AnswerServer>, t: Throwable) {
                                Log.d("FAILED REQ CREATE DRESSEUR SERVER", "ECHEC REQUETE : " + t) //échec de connexion (mauvais login ou serveur hs)
                            }
                        })
                    }
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "S'inscrire", style = TextStyle(Color.White, fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Vous avez déjà un compte ? Cliquez ici pour vous connecter."),
            onClick = { redirectMain.value = true},
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.trebuc)),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        )

        if (showErrorMsg.value == 1)
            ShowError("Au moins 1 des champs est vide.")
        else if (showErrorMsg.value == 2)
            ShowError("Adresse e-mail invalide.")
        else if (showErrorMsg.value == 3)
            ShowError("Les deux mots de passe saisis sont différents.")

        if (redirectMain.value) {
            val context = LocalContext.current
            val navigateMain = Intent(context, MainActivity::class.java)
            startActivity(context ,navigateMain, null)
        }
    }
}