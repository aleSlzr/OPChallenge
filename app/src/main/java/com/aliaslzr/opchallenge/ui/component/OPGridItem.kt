package com.aliaslzr.opchallenge.ui.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.aliaslzr.opchallenge.R

@Composable
fun OPGridItem(
    name: String,
    imageUrl: String,
    rating: Long,
    onActionClicked: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    Card(
        modifier =
            Modifier
                .clickable {
                    onActionClicked()
                }.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.landscape_placeholder),
                modifier =
                    Modifier
                        .fillMaxSize()
                        .clip(RectangleShape),
            )
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier =
                    Modifier
                        .basicMarquee()
                        .focusRequester(focusRequester)
                        .focusable(),
            )
            Text(
                text = "${rating}%",
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun OPGridItemPreview() {
    OPGridItem(
        name = "Interpol",
        imageUrl = "some image",
        rating = 1L,
    ) { }
}