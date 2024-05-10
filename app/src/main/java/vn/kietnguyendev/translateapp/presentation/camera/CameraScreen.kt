package vn.kietnguyendev.translateapp.presentation.camera

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.analyzer.TextAnalyzer
import vn.kietnguyendev.translateapp.presentation.CoreColors
import vn.kietnguyendev.translateapp.presentation.Destination
import vn.kietnguyendev.translateapp.presentation.navigate
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var resultText = remember { mutableStateOf("") }

    DisposableEffect(Unit) {
        permissionState.launchPermissionRequest()

        onDispose { cameraExecutor.shutdown() }
    }

    val previewView: PreviewView = remember { PreviewView(context) }

    LaunchedEffect(previewView) {
        context.setUpCamera(resultText, cameraExecutor, lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, previewView)
    }

    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = { /*TODO*/ },
        permissionNotAvailableContent = { /*TODO*/ }) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f)
            .background(Color.Black))
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, end = 20.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_flash),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp)
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp), contentAlignment = Alignment.Center){
                Box(modifier = Modifier
                    .width(260.dp)
                    .height(65.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CoreColors.Primary)
                    .clickable { navController.navigate(Destination.TranslateScreen.route, bundleOf("title" to "Camera", "initialText" to resultText.value)) }, contentAlignment = Alignment.Center) {
                    Text(text = "Translate", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener(
            {
                continuation.resume(future.get())
            },
            mainExecutor
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
suspend fun Context.setUpCamera(
    resultText: MutableState<String>,
    cameraExecutor: Executor,
    lifecycleOwner: LifecycleOwner,
    cameraSelector: CameraSelector,
    previewView: PreviewView
) {
    val preview = Preview.Builder()
        .build()
        .apply { setSurfaceProvider(previewView.surfaceProvider) }

    val cameraProvider = getCameraProvider()
    val imageAnalyzer = ImageAnalysis.Builder()
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .also {
            it.setAnalyzer(
                cameraExecutor,
                TextAnalyzer(
                    lifecycleOwner.lifecycle,
                    resultText
                )
            )
        }
    cameraProvider.unbindAll()
    cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        preview,
        imageAnalyzer
    )
}
