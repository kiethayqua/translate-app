package vn.kietnguyendev.translateapp.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.kietnguyendev.translateapp.R

@Composable
fun SettingItem(
    title: String,
    subTitle: String = "",
    onPress: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(10.dp)
            .clickable { onPress() }
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Row {
            if (subTitle.isNotEmpty()) {
                Text(text = subTitle, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(16.dp))
            }
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}