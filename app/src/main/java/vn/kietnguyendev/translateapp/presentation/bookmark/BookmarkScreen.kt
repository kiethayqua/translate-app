package vn.kietnguyendev.translateapp.presentation.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import vn.kietnguyendev.translateapp.presentation.components.HeaderTitle
import vn.kietnguyendev.translateapp.presentation.components.NavigationHeader
import vn.kietnguyendev.translateapp.R
import vn.kietnguyendev.translateapp.presentation.CoreColors

@Composable
fun BookmarkScreen(
    navController: NavController,
    state: BookmarkState,
    onRemoveBookmark: (BookmarkItem) -> Unit
) {
    NavigationHeader(
        navController = navController,
        title = {
            HeaderTitle(
                text = "Bookmark", icon = painterResource(
                    id = R.drawable.ic_star
                )
            )
        })
    {
        if (state.data.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CoreColors.Background)
                    .padding(20.dp)
            ) {
                state.data.forEachIndexed { index, data ->
                    item {
                        BookmarkItem(fromText = data.fromText, toText = data.toText, onRemove = {
                            onRemoveBookmark(data)
                        })
                        if (index != state.data.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(CoreColors.Line)
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CoreColors.Background),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        colorFilter = ColorFilter.tint(CoreColors.Line)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Bookmark the translation to view here",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CoreColors.Primary
                    )
                }
            }
        }
    }
}