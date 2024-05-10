package vn.kietnguyendev.translateapp.presentation.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.presentation.components.HeaderTitle
import vn.kietnguyendev.translateapp.presentation.components.NavigationHeader

@Composable
fun SettingScreen(navController: NavController) {
    NavigationHeader(
        navController = navController,
        title = {
            HeaderTitle(
                text = "Setting", icon = painterResource(
                    id = R.drawable.ic_setting
                )
            )
        })
    {

    }
}