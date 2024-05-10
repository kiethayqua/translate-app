package vn.kietnguyendev.translateapp.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import vn.kietnguyendev.translateapp.presentation.components.HeaderTitle
import vn.kietnguyendev.translateapp.presentation.components.NavigationHeader
import vn.kietnguyendev.translateapp.R

@Composable
fun BookmarkScreen(navController: NavController) {
    NavigationHeader(
        navController = navController,
        title = {
            HeaderTitle(
                text = "Bookmark", icon = painterResource(
                    id = R.drawable.ic_star
                )
            )
        })
    {

    }
}