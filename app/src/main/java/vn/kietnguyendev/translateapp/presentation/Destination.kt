package vn.kietnguyendev.translateapp.presentation

sealed class Destination(val route: String) {
    data object HomeScreen: Destination("home_screen")
    data object BookmarkScreen: Destination("bookmark_screen")
    data object SettingScreen: Destination("setting_screen")
    data object CameraScreen: Destination("camera_screen")
    data object RecordScreen: Destination("record_screen")
    data object TranslateScreen: Destination("translate_screen")
    data object IntroduceScreen: Destination("introduce_screen")
    data object PolicyScreen: Destination("policy_screen")
}