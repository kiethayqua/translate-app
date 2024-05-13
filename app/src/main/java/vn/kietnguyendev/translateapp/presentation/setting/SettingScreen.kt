package vn.kietnguyendev.translateapp.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.presentation.CoreColors
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
        Column(modifier = Modifier
            .fillMaxSize()
            .background(CoreColors.Background)
            .padding(16.dp)) {
            SettingItem(title = "Default language", subTitle = "English") {

            }
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(title = "Second language", subTitle = "Vietnamese") {

            }
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(title = "Introduce") {

            }
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(title = "Policy") {

            }
        }
    }
}