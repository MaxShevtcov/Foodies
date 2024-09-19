package com.max.foodies

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.max.foodies.screens.catalogueScreen.CatalogueScreen
import com.max.foodies.screens.productScreen.ProductScreen
import com.max.foodies.screens.searchScreen.SearchScreen

//
//@Composable
//fun Navigation() {
//
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = NavRoute.Catalogue
//    ) {
//        composable<NavRoute.Catalogue> {
//            CatalogueScreen(
//                onNavigateToSearch = { navController.navigate(route = NavRoute.Search) },
//                onNavigateToProduct = { id ->
//                    navController.navigate(
//                        route = NavRoute.Product.withArgs(
//                            "$id"
//                        )
//                    )
//                }
//            )
//        }
//        composable<NavRoute.Search> {
//            SearchScreen(
//                onBackPressed = { navController.popBackStack() },
//                onNavigateToProduct = { id ->
//                    navController.navigate(
//                        route = NavRoute.Product.withArgs(
//                            "$id"
//                        )
//                    )
//                }
//            )
//        }
//        composable<NavRoute.Product> {navBackStackEntry ->
//
//            val args = navBackStackEntry.arguments
//            ProductScreen(
//                onBackPressed = { navController.popBackStack() },
//                id = args?.getInt(NavRoute.Product.id)!!
//            )
//        }
//    }
//}
@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.Catalogue.path
    ) {
        addCatalogueScreen(navController, this)

        addSearchScreen(navController, this)

        addProductScreen(navController, this)
    }
}

private fun addCatalogueScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Catalogue.path) {
        CatalogueScreen(
            onNavigateToSearch = { navController.navigate(route = NavRoute.Search.path) },
            onNavigateToProduct = { id ->
                navController.navigate(route = NavRoute.Product.withArgs("$id")
                )
            }
        )
    }
}

private fun addSearchScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Search.path) {

        SearchScreen(
            onBackPressed = { navController.popBackStack() },
            onNavigateToProduct = { id ->
                navController.navigate(
                    route = NavRoute.Product.withArgs(
                        "$id"
                    )
                )
            }
        )
    }
}



private fun addProductScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = NavRoute.Product.withArgsFormat(NavRoute.Product.id),
        arguments = listOf(
            navArgument(NavRoute.Product.id) {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->

        val args = navBackStackEntry.arguments
        ProductScreen(
            onBackPressed = { navController.popBackStack() },
            id = args?.getInt(NavRoute.Product.id)!!
        )
    }
}
