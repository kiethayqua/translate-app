package vn.kietnguyendev.translateapp.presentation.translate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.kietnguyendev.translateapp.presentation.CoreColors
import vn.kietnguyendev.translateapp.R

@Composable
fun TextInputBlock(
    leftTitle: String,
    rightTitle: String,
    textContent: String,
    textColor: Color,
    disable: Boolean = false,
    needFocus: Boolean = false,
    onChangeText: (String) -> Unit = {},
    onTranslate: (String) -> Unit = {},
    onPressBookmark: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardActions = remember(textContent) {
        KeyboardActions(onDone = {
            focusManager.clearFocus()
            onTranslate(textContent)
        })
    }
    val keyboardOptions = remember {
        KeyboardOptions(imeAction = ImeAction.Done)
    }

    LaunchedEffect(Unit) {
        if (needFocus && disable.not()) focusRequester.requestFocus()
    }

    Column {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = leftTitle, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CoreColors.Text01)
            Text(text = rightTitle, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = CoreColors.Text01)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(166.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
        ) {
            if (disable) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                    .weight(1f)
                    .verticalScroll(
                        rememberScrollState()
                    )) {
                    Text(
                        text = textContent,
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, color = textColor)
                    )
                }
            } else {
                TextField(
                    value = textContent,
                    onValueChange = onChangeText,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, color = textColor),
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                BottomIcon(painter = painterResource(id = R.drawable.ic_volumn))
                Spacer(modifier = Modifier.width(12.dp))
                BottomIcon(painter = painterResource(id = R.drawable.ic_star_gray))
                Spacer(modifier = Modifier.width(12.dp))
                BottomIcon(painter = painterResource(id = R.drawable.ic_copy))
            }
        }
    }
}

@Composable
private fun BottomIcon(
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier.size(18.dp)
    )
}