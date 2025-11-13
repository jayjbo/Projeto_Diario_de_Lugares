package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.data.Place
import kotlinx.coroutines.flow.MutableStateFlow

private data class FormState(
    val nome: String = "",
    val descricao: String = "",
    val endereco: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceFormScreen(
    viewModel: PlaceViewModel = hiltViewModel(),
    placeId: String?,
    onSaveClick: () -> Unit
) {
    val isEditMode = placeId != null

    val placeStateFlow = if (isEditMode) {
        remember(placeId, viewModel) {
            viewModel.getPlaceById(placeId!!)
        }
    } else {

        remember { MutableStateFlow<Place?>(null) }
    }

    val placeState by placeStateFlow.collectAsState()

    var formState by remember { mutableStateOf(FormState()) }

    LaunchedEffect(placeState) {
        if (isEditMode && placeState != null) {
            formState = FormState(
                nome = placeState!!.nome,
                descricao = placeState!!.descricao,
                endereco = placeState!!.endereco
            )
        }
    }

    val topBarTitle = if (isEditMode) "Editar Lugar" else "Adicionar Novo Lugar"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = onSaveClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = formState.nome,
                onValueChange = { formState = formState.copy(nome = it) },
                label = { Text("Nome do Lugar") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formState.descricao,
                onValueChange = { formState = formState.copy(descricao = it) },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formState.endereco,
                onValueChange = { formState = formState.copy(endereco = it) },
                label = { Text("Endereço") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isEditMode && placeState != null) {
                        val updatedPlace = placeState!!.copy(
                            nome = formState.nome,
                            descricao = formState.descricao,
                            endereco = formState.endereco
                        )
                        viewModel.updatePlace(updatedPlace)
                    } else {
                        viewModel.addPlace(
                            nome = formState.nome,
                            descricao = formState.descricao,
                            endereco = formState.endereco
                        )
                    }

                    onSaveClick()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SALVAR")
            }
        }
    }
}