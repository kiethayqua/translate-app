package vn.kietnguyendev.translateapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.kietnguyendev.translateapp.presentation.bookmark.BookmarkScreen
import vn.kietnguyendev.translateapp.presentation.home.HomeScreen
import vn.kietnguyendev.translateapp.presentation.setting.SettingScreen
import vn.kietnguyendev.translateapp.presentation.translate.TranslateScreen
import vn.kietnguyendev.translateapp.presentation.translate.TranslateViewModel
 import androidx.hilt.navigation.compose.hiltViewModel
import vn.kietnguyendev.translateapp.presentation.camera.CameraScreen
import java.net.URLDecoder

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun App() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Destination.Home.name,
    ) {
        composable(Destination.Home.name) {
            HomeScreen(navController)
        }
        composable(Destination.Bookmark.name) {
            BookmarkScreen(navController)
        }
        composable(Destination.Setting.name) {
            SettingScreen(navController)
        }
        composable(Destination.Translate.name+"?title={title}?initialText={initialText}") {
            val title = it.arguments?.getString("title") ?: ""
            val initialText = it.arguments?.getString("initialText") ?: ""
            val viewModel: TranslateViewModel = hiltViewModel()
            val translateState by viewModel.state
            val onChangeText = remember { viewModel::onChangeText }
            LaunchedEffect(true) {
                if (initialText.isNotEmpty()) {
                    val decodeStr = URLDecoder.decode(initialText)
                    viewModel.initWithText(decodeStr)
                }
            }
            TranslateScreen(navController, title = title, state = translateState, onChangeText = onChangeText)
        }
        composable(Destination.Camera.name) {
            CameraScreen(navController)
        }
    }
}