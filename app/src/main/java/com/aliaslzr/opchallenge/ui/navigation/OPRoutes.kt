package com.aliaslzr.opchallenge.ui.navigation

sealed class OPRoutes(
    val route: String,
) {
    data object ArtistList : OPRoutes("Artist")
    data object Detail : OPRoutes("Detail")
}