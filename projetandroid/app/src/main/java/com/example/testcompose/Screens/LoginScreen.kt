package com.example.testcompose.Screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.testcompose.InterfacesAPI.CONNECTION_DRESSEUR
import com.example.testcompose.R
import com.example.testcompose.objects.Dresseur
import com.example.testcompose.ui.theme.Routes
import com.example.testcompose.viewModel.DresseurViewModel
import okhttp3.Route
import retrofit2.Call
import java.math.BigInteger
import java.security.MessageDigest
import retrofit2.Callback
import retrofit2.Response



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: DresseurViewModel) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF001527)
    ) {}
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var mail = remember { mutableStateOf(TextFieldValue()) }
        var mdp = remember { mutableStateOf(TextFieldValue()) }

        var redirectInscription = remember { mutableStateOf(false) }
        var showErrorMsg = remember { mutableStateOf(0) }

        Image(
            painter = painterResource(id = R.drawable.polypoke_logo),
            contentDescription = "Logo de l'application",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Login", style = TextStyle(fontSize = 40.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Mail", style = TextStyle(fontFamily = FontFamily(Font(R.font.trebuc)), color=Color.White)) },
            value = mail.value,
            onValueChange = { mail.value = it },
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
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    if (mail.value.text == "" || mdp.value.text == "")
                        showErrorMsg.value = 1

                    fun String.sha256(): String {
                        val md = MessageDigest.getInstance("SHA-256")
                        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
                    }

                    var DresseurTest : Dresseur?
                    val mdp_sha256 = mdp.value.text.sha256()

                    CONNECTION_DRESSEUR(mail.value.text, mdp_sha256, object : Callback<Dresseur> {
                        override fun onResponse(call: Call<Dresseur>, response: Response<Dresseur>) {
                            if (response.body() != null && response.isSuccessful) {
                                DresseurTest = response.body() //on récupère l'objet
                                Log.d("REQ CONNECTION DRESSEUR SERVER", "CONNEXION ACCEPTEE")
                                viewModel.setData(DresseurTest!!)
                                navController.navigate("MapScreen")
                                //val mainActivity = MainActivity()
                                //mainActivity.dresseur = DresseurTest!!
                            }
                        }
                        override fun onFailure(call: Call<Dresseur>, t: Throwable) {
                            Log.d("FAILED REQ CONNECTION DRESSEUR SERVER", "ECHEC REQUETE : " + t) //échec de connexion (mauvais login ou serveur hs)
                            showErrorMsg.value = 2
                        }
                    })
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Se connecter", style = TextStyle(Color.White, fontFamily = FontFamily(Font(R.font.trebuc, FontWeight.Bold))))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ClickableText(
            text = AnnotatedString("Vous n'avez pas de compte ? Cliquez ici pour vous inscire."),
            onClick = { redirectInscription.value = true },
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
            ShowError("Echec de la connexion.")

        if (redirectInscription.value) {
            mail.value = TextFieldValue()
            mdp.value = TextFieldValue()

            redirectInscription.value = false
            showErrorMsg.value = 0
            navController.navigate(Routes.Inscription.route)
        }
    }
}