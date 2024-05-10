package vn.kietnguyendev.translateapp.presentation.translate

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.presentation.CoreColors
import vn.kietnguyendev.translateapp.presentation.components.HeaderIcon
import vn.kietnguyendev.translateapp.presentation.components.HeaderTitle
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TranslateScreen(
    navController: NavController,
    title: String,
    state: TranslateState,
    showRecord: Boolean = false,
    onChangeText: (String) -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val isRecording = remember { mutableStateOf(false) }
    val permissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)
    val speechRecognizerIntent = remember { Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) }
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

    val statusBarHeight = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }

    DisposableEffect(Unit) {
        if (showRecord) {
            permissionState.launchPermissionRequest()
        }

        onDispose { speechRecognizer.destroy() }
    }

    LaunchedEffect(permissionState) {
        if (showRecord && permissionState.hasPermission) {
            val listener = object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {}

                override fun onResults(results: Bundle?) {
                    val data  = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    onChangeText(data?.get(0) ?: "")
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}

            }
            speechRecognizer.setRecognitionListener(listener)
            isRecording.value = true
            speechRecognizer.startListening(speechRecognizerIntent)
        }
    }

    Scaffold(
        topBar = {
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                        .background(CoreColors.Primary)
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 16.dp + statusBarHeight,
                            bottom = 72.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeaderIcon(
                        painter = painterResource(id = R.drawable.ic_back),
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                    HeaderTitle(text = title)
                    Box {}
                }
                Box(modifier = Modifier.offset(y = statusBarHeight + 16.dp + 24.dp + 36.dp)) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .shadow(elevation = 2.dp, RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(123.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CoreColors.Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.from, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.White)
                        }
                        Image(painter = painterResource(id = R.drawable.ic_arrow), contentDescription = null, modifier = Modifier.width(18.dp))
                        Box(
                            modifier = Modifier
                                .width(123.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CoreColors.Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.to, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.White)
                        }
                    }
                }

            }
        },
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(CoreColors.Background)
            .padding(top = paddingValues.calculateTopPadding() + 72.dp, start = 16.dp, end = 16.dp)) {
            TextInputBlock(
                leftTitle = "From",
                rightTitle = state.from,
                textContent = state.fromText,
                textColor = CoreColors.Text01,
                onChangeText = onChangeText,
                onPressBookmark = {}
            )
            Spacer(modifier = Modifier.height(40.dp))
            TextInputBlock(leftTitle = "To", rightTitle = state.to, textContent = state.toText, textColor = CoreColors.Primary, disable = true) {}
            if (showRecord) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    if (isRecording.value) "Listening..." else "Record",
                    color = if (permissionState.hasPermission) Color.Red else Color.Blue,
                    modifier = Modifier.clickable {
                        if (isRecording.value) {
                            speechRecognizer.stopListening()
                            isRecording.value = false
                        } else {
                            speechRecognizer.startListening(speechRecognizerIntent)
                            isRecording.value = true
                        }
                    }
                )
            }
        }
    }
}