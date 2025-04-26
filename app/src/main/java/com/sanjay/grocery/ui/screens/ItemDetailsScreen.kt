package com.sanjay.grocery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanjay.grocery.R
import com.sanjay.grocery.navigation.ItemDetailsNav
import com.sanjay.grocery.ui.components.CustomAnnotatedText
import com.sanjay.grocery.ui.components.CustomText
import com.sanjay.grocery.ui.components.Text14
import com.sanjay.grocery.ui.components.Text16
import com.sanjay.grocery.ui.events.ItemDetailsEvents
import com.sanjay.grocery.ui.states.ItemDetailsState
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.FavIconColor
import com.sanjay.grocery.ui.theme.Gray
import com.sanjay.grocery.ui.theme.Green
import com.sanjay.grocery.ui.theme.TextDark
import com.sanjay.grocery.ui.theme.White

@Composable
fun ItemDetailsScreen(
    state: ItemDetailsState,
    navItem: ItemDetailsNav,
    onEvent: (ItemDetailsEvents) -> Unit,
) {
    if (state.isInit) {
        onEvent(
            ItemDetailsEvents.OnInitRefresh(
                tyreId = navItem.tyreId,
                typeName = navItem.type
            )
        )
    }

    if (!state.error.isNullOrEmpty()) {
        onEvent(ItemDetailsEvents.ShowToast(state.error))
    }

    val pageState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            state.item?.sliderImages?.size ?: 0
        }
    )

    val priceStr = remember(state.item) {
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("${state.item?.pricePerPiece}")
            }
            append(" $/Kg")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxHeight(0.5f),
            state = pageState
        ) { pageIndex ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.item?.sliderImages?.get(pageIndex))
                    .crossfade(true)
                    .error(R.drawable.ic_image)
                    .build(),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                contentDescription = state.item?.typeName
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            contentDescription = stringResource(R.string.back),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .offset(5.dp, 40.dp)
                .clickable(enabled = true) {
                    onEvent(ItemDetailsEvents.OnBackPressed)
                }
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = Background,
                    shape = RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 25.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                CustomText(
                    text = state.item?.typeName ?: "NA",
                    modifier = Modifier,
                    fontSize = 28.sp
                )

                CustomAnnotatedText(
                    text = priceStr,
                    modifier = Modifier,
                    fontSize = 26.sp
                )

                Text14(
                    text = "~${state.item?.weightPerPiece ?: "NA"} gr/ piece",
                    modifier = Modifier,
                    textColor = Green
                )

                Text16(
                    text = state.item?.description ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    textAlign = TextAlign.Start
                )
            }

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(0.2f),
                    colors = ButtonColors(
                        containerColor = White,
                        contentColor = TextDark,
                        disabledContentColor = TextDark,
                        disabledContainerColor = Gray,
                    ),
                    onClick = {
                        onEvent(ItemDetailsEvents.OnFavClicked)
                    }
                ) {
                    Icon(
                        imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (state.isFavorite) stringResource(R.string.favorite)
                        else stringResource(R.string.unfavorite),
                        tint = if (state.isFavorite) FavIconColor else TextDark
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = ButtonDefaults.outlinedShape,
                    colors = ButtonColors(
                        containerColor = Green,
                        contentColor = White,
                        disabledContentColor = TextDark,
                        disabledContainerColor = Gray,
                    ),
                    onClick = {
                        onEvent(ItemDetailsEvents.OnAddToCart(state.selectedTypeID))
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = stringResource(R.string.add_to_cart),
                            colorFilter = ColorFilter.tint(color = White)
                        )

                        Text14(
                            text = stringResource(R.string.add_to_cart),
                            modifier = Modifier,
                            textColor = White
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ItemDetailsPreview() {
    ItemDetailsScreen(
        state = ItemDetailsState(),
        navItem = ItemDetailsNav("", 0),
        onEvent = {}
    )
}