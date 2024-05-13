package vn.kietnguyendev.translateapp.presentation.setting

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.mlkit.nl.translate.TranslateLanguage
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.presentation.CoreColors
import vn.kietnguyendev.translateapp.presentation.components.HeaderTitle
import vn.kietnguyendev.translateapp.presentation.components.NavigationHeader
import vn.kietnguyendev.translateapp.util.LanguageUtil

val languages = mutableListOf(
    TranslateLanguage.ENGLISH,
    TranslateLanguage.VIETNAMESE,
    TranslateLanguage.CHINESE,
    TranslateLanguage.JAPANESE,
    TranslateLanguage.FRENCH
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    state: SettingState,
    onSetLang: (Context, String, String) -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var languageKey by remember { mutableStateOf("") }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CoreColors.Background)
                .padding(16.dp)
        ) {
            SettingItem(title = "Default language", subTitle = LanguageUtil.getLanguageFromCode(state.defaultLang)) {
                languageKey = DEFAULT_LANG_KEY
                showBottomSheet = true
            }
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(title = "Second language", subTitle = LanguageUtil.getLanguageFromCode(state.secondLang)) {
                languageKey = SECOND_LANG_KEY
                showBottomSheet = true
            }
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(title = "Introduce") {

            }
            Spacer(modifier = Modifier.height(8.dp))
            SettingItem(title = "Policy") {

            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                fun isSelected(lang: String): Boolean {
                    return when (languageKey) {
                        DEFAULT_LANG_KEY -> state.defaultLang == lang
                        SECOND_LANG_KEY -> state.secondLang == lang
                        else -> false
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    languages.forEach {
                        Text(
                            text = LanguageUtil.getLanguageFromCode(it),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { onSetLang(context, languageKey, it) },
                            color = if (isSelected(it)) CoreColors.Primary else CoreColors.Text01
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}