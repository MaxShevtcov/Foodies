package com.max.foodies

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.max.foodies.screens.catalogueScreen.CatalogueScreen
import com.max.foodies.screens.searchScreen.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
object Catalogue

@Serializable
object Search

@Composable
fun Navigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Catalogue
    ) {
        composable<Catalogue> {
            CatalogueScreen(
                onNavigateToSearch = { navController.navigate(route = Search) }
            )
        }
        composable<Search> {
            SearchScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}