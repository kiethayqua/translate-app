package vn.kietnguyendev.translateapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import vn.kietnguyendev.translateapp.presentation.components.HeaderTitle
import vn.kietnguyendev.translateapp.presentation.components.NavigationHeader
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.presentation.CoreColors
import vn.kietnguyendev.translateapp.presentation.Destination
import vn.kietnguyendev.translateapp.presentation.components.HeaderIcon

val mainFeatures = listOf(
    MainFeature(
        id = 0,
        resId = R.drawable.ic_text,
        title = "Text",
        route = Destination.Translate.name
    ),
    MainFeature(
        id = 1,
        resId = R.drawable.ic_camera,
        title = "Camera",
        route = Destination.Camera.name
    ),
    MainFeature(
        id = 2,
        resId = R.drawable.ic_conversation,
        title = "Record",
        route = Destination.Record.name
    )
)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoreColors.Background)
                .padding(8.dp)
        ) {
            mainFeatures.forEach {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(it.route + "/${it.title}") }
                        .shadow(elevation = 2.dp, RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(vertical = 28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = it.resId), contentDescription = null)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = it.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = CoreColors.Text01
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

data class MainFeature(
    val id: Int,
    val resId: Int,
    val title: String,
    val route: String
)
