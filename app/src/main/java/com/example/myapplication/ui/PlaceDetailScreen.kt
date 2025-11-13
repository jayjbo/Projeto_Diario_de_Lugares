package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
fun PlaceDetailScreen(
    viewModel: PlaceViewModel = hiltViewModel(),
    placeId: String,
    onNavigateBack: () -> Unit,
    onEditClick: (Place) -> Unit,
) {

    val place by viewModel.getPlaceById(placeId).collectAsState()

    val currentPlace = place

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentPlace?.nome ?: "Carregando...") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (currentPlace == null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = currentPlace.nome,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "Descrição",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = currentPlace.descricao.ifEmpty { "(Sem descrição)" },
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Endereço",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = currentPlace.endereco.ifEmpty { "(Sem endereço)" },
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { onEditClick(currentPlace) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("EDITAR")
                }

                OutlinedButton(
                    onClick = {
                        viewModel.deletePlace(currentPlace)
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("EXCLUIR")
                }
            }
        }
    }
}