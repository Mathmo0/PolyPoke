package com.example.testcompose.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testcompose.objects.Dresseur

class DresseurViewModel : ViewModel() {

    var dresseurConnecte = MutableLiveData<Dresseur>()
    var errorMessage: String by mutableStateOf("")

    init{

        dresseurConnecte.value = Dresseur()

    }

    fun setData(dres:Dresseur)
    {
        dresseurConnecte.value = dres
    }

}