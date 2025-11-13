package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.PlaceDetailScreen
import com.example.myapplication.ui.PlaceFormScreen
import com.example.myapplication.ui.PlaceListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "placeList"
    ) {

        composable(route = "placeList") {
            PlaceListScreen(
                onAddPlaceClick = {
                    navController.navigate("placeForm/new")
                },
                onPlaceClick = { place ->
                    navController.navigate("placeDetail/${place.id}")
                }
            )
        }

        composable(
            route = "placeForm/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")

            PlaceFormScreen(
                placeId = if (placeId == "new") null else placeId,
                onSaveClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "placeDetail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")

            if (placeId != null) {
                PlaceDetailScreen(
                    placeId = placeId,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = { place ->
                        navController.navigate("placeForm/${place.id}")
                    }
                )
            }
        }
    }
}