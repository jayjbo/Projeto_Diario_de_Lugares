package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.PlaceViewModel

// Estes imports ficarão vermelhos por enquanto, o que é NORMAL
// import com.example.myapplication.ui.PlaceDetailScreen
// import com.example.myapplication.ui.PlaceEditScreen
// import com.example.myapplication.ui.PlaceListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Obtém uma instância do ViewModel com escopo de navegação
    val viewModel: PlaceViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "placeList") {

        // Tela 1: Lista de Lugares (Tela Principal)
        composable("placeList") {
            // PlaceListScreen(  // <-- Descomente quando a tela existir
            //     viewModel = viewModel,
            //     onAddPlace = {
            //         navController.navigate("placeEdit")
            //     },
            //     onPlaceClick = { placeId ->
            //         navController.navigate("placeDetail/$placeId")
            //     }
            // )
        }

        // Tela 2: Detalhes do Lugar
        composable(
            route = "placeDetail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")
            if (placeId != null) {
                // PlaceDetailScreen( // <-- Descomente quando a tela existir
                //     viewModel = viewModel,
                //     placeId = placeId,
                //     onEditClick = {
                //         navController.navigate("placeEdit/$placeId")
                //     },
                //     onDeleteSuccess = {
                //         navController.popBackStack()
                //     }
                // )
            }
        }

        // Tela 3: Cadastro/Edição de Lugar (Rota de Edição)
        composable(
            route = "placeEdit/{placeId}",
            arguments = listOf(navArgument("placeId") {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")
            // PlaceEditScreen( // <-- Descomente quando a tela existir
            //     viewModel = viewModel,
            //     placeId = placeId, // Se for nulo, é um novo lugar
            //     onSaveSuccess = {
            //         navController.popBackStack()
            //     }
            // )
        }

        // Tela 3: Cadastro/Edição de Lugar (Rota de Cadastro)
        composable("placeEdit") {
            // PlaceEditScreen( // <-- Descomente quando a tela existir
            //     viewModel = viewModel,
            //     placeId = null, // Nulo significa "criar novo"
            //     onSaveSuccess = {
            //         navController.popBackStack()
            //     }
            // )
        }
    }
}