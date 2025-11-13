package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Place // Importe sua Data Class
import com.example.myapplication.data.PlaceRepository // Importe seu Repositório
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository
) : ViewModel() {

    // 1. A anotação @HiltViewModel pede ao Hilt para criar este ViewModel.
    // 2. O @Inject constructor pede ao Hilt para injetar o PlaceRepository
    //    (que ele sabe criar graças ao AppModule).

    // Expõe a lista de lugares do Room como um StateFlow
    // A UI vai "observar" esta variável
    val places: StateFlow<List<Place>> = repository.allPlaces
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Mantém ativo por 5s
            initialValue = emptyList() // Começa com uma lista vazia
        )

    /**
     * Pega um único lugar pelo ID. Usado na tela de Detalhes/Edição.
     */
    fun getPlaceById(id: String): StateFlow<Place?> {
        return repository.getPlaceById(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }

    /**
     * Função para adicionar um novo lugar.
     * Chama o repositório em uma Coroutine (viewModelScope).
     */
    fun addPlace(nome: String, descricao: String, endereco: String) = viewModelScope.launch {
        repository.addPlace(nome, descricao, endereco)
    }

    /**
     * Função para atualizar um lugar existente.
     * Função para atualizar um lugar existente.
     */
    fun updatePlace(place: Place) = viewModelScope.launch {
        repository.updatePlace(place)
    }

    /**
     * Função para deletar um lugar.
     */
    fun deletePlace(place: Place) = viewModelScope.launch {
        repository.deletePlace(place)
    }
}