package vn.kietnguyendev.translateapp.analyzer

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.MutableState
import androidx.lifecycle.Lifecycle
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import vn.kietnguyendev.translateapp.util.ImageUtils

class TextAnalyzer(
    private val lifecycle: Lifecycle,
    private val resultText: MutableState<String>
): ImageAnalysis.Analyzer {
    private val detector = TextRecognition.getClient()

    init {
        lifecycle.addObserver(detector)
    }
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return
        val convertImageToBitmap = ImageUtils.convertYuv420888ImageToBitmap(mediaImage)
        recognizeText(InputImage.fromBitmap(convertImageToBitmap, 0)).addOnCompleteListener {
            imageProxy.close()
        }
    }

    private fun recognizeText(
        image: InputImage
    ): Task<Text> {
        // Pass image to an ML Kit Vision API
        return detector.process(image)
            .addOnSuccessListener { text ->
                resultText.value = text.text
            }
            .addOnFailureListener { exception ->
                // Task failed with an exception
            }
    }
}