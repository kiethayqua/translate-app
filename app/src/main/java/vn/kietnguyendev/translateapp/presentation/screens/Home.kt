package vn.kietnguyendev.translateapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import vn.kietnguyendev.translateapp.presentation.components.HeaderTitle
import vn.kietnguyendev.translateapp.presentation.components.NavigationHeader
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.presentation.Destination
import vn.kietnguyendev.translateapp.presentation.components.HeaderIcon

@Composable
fun HomeScreen(
    navController: NavController
) {
    NavigationHeader(
        navController = navController, 
        title = { HeaderTitle(text = "Home") },
        leftIcon = {
            HeaderIcon(
                painter = painterResource(id = R.drawable.ic_star),
                modifier = Modifier.clickable {
                    navController.navigate(Destination.Bookmark.name)
                }
            )
        },
        rightIcon = {
            HeaderIcon(
                painter = painterResource(id = R.drawable.ic_setting),
                modifier = Modifier.clickable {
                    navController.navigate(Destination.Setting.name)
                }
            )
        }
    ) {
        
    }
}
