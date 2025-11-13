package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Place
import com.example.myapplication.data.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository
) : ViewModel() {

    val places: StateFlow<List<Place>> = repository.allPlaces
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getPlaceById(id: String): StateFlow<Place?> {
        return repository.getPlaceById(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }

    fun addPlace(nome: String, descricao: String, endereco: String) = viewModelScope.launch {
        repository.addPlace(nome, descricao, endereco)
    }

    fun updatePlace(place: Place) = viewModelScope.launch {
        repository.updatePlace(place)
    }

    fun deletePlace(place: Place) = viewModelScope.launch {
        repository.deletePlace(place)
    }
}