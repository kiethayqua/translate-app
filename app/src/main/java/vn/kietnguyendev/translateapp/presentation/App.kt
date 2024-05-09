package vn.kietnguyendev.translateapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vn.kietnguyendev.translateapp.presentation.screens.BookmarkScreen
import vn.kietnguyendev.translateapp.presentation.screens.HomeScreen
import vn.kietnguyendev.translateapp.presentation.screens.SettingScreen
import vn.kietnguyendev.translateapp.presentation.screens.TranslateScreen

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
        composable(Destination.Translate.name+"/{title}") {
            val title = it.arguments?.getString("title") ?: ""
            TranslateScreen(navController, title = title)
        }
    }
}