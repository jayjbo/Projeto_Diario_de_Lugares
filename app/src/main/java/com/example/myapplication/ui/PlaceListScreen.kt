package com.example.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.data.Place


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListScreen(
    viewModel: PlaceViewModel = hiltViewModel(),
    onAddPlaceClick: () -> Unit,
    onPlaceClick: (Place) -> Unit
) {
    val places by viewModel.places.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Diário de Lugares") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddPlaceClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar lugar")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(places) { place ->
                PlaceListItem(
                    place = place,
                    onItemClick = { onPlaceClick(place) }
                )
                Divider()
            }
        }
    }
}

@Composable
fun PlaceListItem(
    place: Place,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = place.nome,
            style = MaterialTheme.typography.titleMedium
        )

    }
}