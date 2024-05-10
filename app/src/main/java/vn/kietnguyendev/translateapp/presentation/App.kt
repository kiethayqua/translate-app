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

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun App() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Destination.HomeScreen.route,
    ) {
        composable(Destination.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Destination.BookmarkScreen.route) {
            BookmarkScreen(navController)
        }
        composable(Destination.SettingScreen.route) {
            SettingScreen(navController)
        }
        composable(Destination.TranslateScreen.route) { entry ->
            val title = entry.arguments?.getString("title") ?: ""
            val initialText = entry.arguments?.getString("initialText") ?: ""
            val showRecord = entry.arguments?.getBoolean("showRecord") ?: false
            val viewModel: TranslateViewModel = hiltViewModel()
            val translateState by viewModel.state
            val onChangeText = remember { viewModel::onChangeText }
            LaunchedEffect(true) {
                if (initialText.isNotEmpty()) {
                    viewModel.initWithText(initialText)
                }
            }
            TranslateScreen(navController, title = title, state = translateState, showRecord = showRecord, onChangeText = onChangeText)
        }
        composable(Destination.CameraScreen.route) {
            CameraScreen(navController)
        }
    }
}