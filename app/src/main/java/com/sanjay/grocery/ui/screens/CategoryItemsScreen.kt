package com.sanjay.grocery.ui.screens

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanjay.grocery.R
import com.sanjay.grocery.models.CategoryItems
import com.sanjay.grocery.navigation.CategoryItemsNav
import com.sanjay.grocery.ui.components.CustomAnnotatedText
import com.sanjay.grocery.ui.components.CustomText
import com.sanjay.grocery.ui.components.ErrorView
import com.sanjay.grocery.ui.components.TextFieldWithPlaceHolder
import com.sanjay.grocery.ui.components.TopBarComp
import com.sanjay.grocery.ui.events.CategoryItemsEvents
import com.sanjay.grocery.ui.states.CategoryItemsState
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.Black
import com.sanjay.grocery.ui.theme.ItemBg
import com.sanjay.grocery.ui.theme.PrimaryClr
import com.sanjay.grocery.ui.theme.TextDark
import com.sanjay.grocery.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItemsScreen(
    state: CategoryItemsState,
    navItem: CategoryItemsNav,
    onEvent: (CategoryItemsEvents) -> Unit,
) {
    if (state.isInit) {
        onEvent(
            CategoryItemsEvents.OnInitRefresh(
                type = navItem.type,
                title = navItem.title,
                tyreId = navItem.typeId
            )
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,
        topBar = {
            TopBarComp(
                title = "",
                onBackClick = {
                    onEvent(CategoryItemsEvents.OnBackPressed)
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Background
        ) {
            if (state.error.isNotEmpty() && state.showErrorView) {
                ErrorView(
                    errorMsg = state.error
                ) {
                    onEvent(CategoryItemsEvents.OnRefresh)
                }
            } else {
                if (state.error.isNotEmpty()) {
                    onEvent(CategoryItemsEvents.ShowToast(state.error))
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomText(
                        text = state.title,
                        modifier = Modifier.wrapContentSize(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    TextFieldWithPlaceHolder(
                        placeholder = "Search",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = White,
                                shape = RoundedCornerShape(25.dp)
                            )
                            .clip(shape = RoundedCornerShape(25.dp)),
                        onValueChange = {
                            onEvent(CategoryItemsEvents.OnSearch(it))
                        },
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = stringResource(R.string.search),
                                tint = Black
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = TextDark,
                            unfocusedTextColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = PrimaryClr
                        )
                    )

                    PullToRefreshBox(
                        isRefreshing = state.isLoading,
                        modifier = Modifier.fillMaxSize(),
                        onRefresh = {
                            onEvent(CategoryItemsEvents.OnRefresh)
                        }
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            itemsIndexed(
                                if (state.searchList.isEmpty()) state.categoryItems
                                else state.searchList
                            ) { index, item ->
                                ListItemView(
                                    item = item,
                                    onClick = { id, name ->
                                        if (id == null || name.isNullOrEmpty()) {
                                            onEvent(CategoryItemsEvents.ShowToast("Failed to load item details"))
                                        } else {
                                            onEvent(
                                                CategoryItemsEvents.OnCategoryItemClicked(
                                                    typeId = id,
                                                    typeName = name
                                                )
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListItemView(
    item: CategoryItems,
    onClick: (typeId: Int?, typeName: String?) -> Unit,
) {
    val priceStr = remember(item) {
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("${item.pricePerPiece}")
            }
            append(" $/Kg")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ItemBg,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable {
                onClick(item.typeID, item.typeName)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.thumbnailImage)
                    .crossfade(true)
                    .error(R.drawable.ic_image)
                    .build(),
                modifier = Modifier
                    .widthIn(max = 180.dp)
                    .height(160.dp)
                    .clip(shape = RoundedCornerShape(15.dp)),
                contentScale = ContentScale.FillHeight,
                contentDescription = item.typeName
            )

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                CustomText(
                    text = item.typeName ?: "NA",
                    modifier = Modifier,
                    fontSize = 24.sp
                )

                CustomAnnotatedText(
                    text = priceStr,
                    modifier = Modifier,
                    fontSize = 14.sp
                )
            }
        }
    }
}