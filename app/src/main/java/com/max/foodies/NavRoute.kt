package com.max.foodies

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute(val path: String) {
    @Serializable
    object Catalogue: NavRoute("Catalogue")
    @Serializable
    object Search: NavRoute("Search")
    @Serializable
    object Product: NavRoute("Product") {
        val id = "id"

    }


    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}