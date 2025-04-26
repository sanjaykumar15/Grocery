package com.sanjay.grocery.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sanjay.grocery.R
import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.ui.components.CustomText
import com.sanjay.grocery.ui.components.Text12
import com.sanjay.grocery.ui.components.Text16
import com.sanjay.grocery.ui.components.TextFieldWithPlaceHolder
import com.sanjay.grocery.ui.events.HomeScreenEvents
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.Black
import com.sanjay.grocery.ui.theme.PrimaryClr
import com.sanjay.grocery.ui.theme.TextDark
import com.sanjay.grocery.ui.theme.TextSecondary
import com.sanjay.grocery.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreenView(
    listItems: List<CategoryListItem>,
    onEvent: (HomeScreenEvents) -> Unit,
    isRefreshing: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CustomText(
            text = stringResource(R.string.categories),
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
                onEvent(HomeScreenEvents.OnSearch(it))
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
            isRefreshing = isRefreshing,
            modifier = Modifier.fillMaxSize(),
            onRefresh = {
                onEvent(HomeScreenEvents.OnRefresh(true))
            }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                itemsIndexed(listItems) { index, item ->
                    CategoryItem(
                        item = item,
                        onClick = {
                            if (it.isNullOrEmpty()) {
                                onEvent(HomeScreenEvents.ShowToast("Unable to view items"))
                            } else {
                                onEvent(HomeScreenEvents.OnCategorySelected(it))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    item: CategoryListItem,
    onClick: (String?) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(200.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = White)
            .clickable(enabled = true) {
                onClick(item.categoryType)
            },
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.categoryImage)
                    .crossfade(true)
                    .error(R.drawable.main_bg)
                    .build(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentScale = ContentScale.FillWidth,
                contentDescription = stringResource(R.string.category_image)
            )

            Text16(
                text = item.categoryName ?: "NA",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            )

            Text12(
                text = "(${item.totalItems})",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                textColor = TextSecondary
            )
        }
    }
}

@Preview
@Composable
private fun CategoryScreenViewPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Background
    ) {
        CategoryScreenView(
            listItems = listOf(
                CategoryListItem().apply {
                    categoryID = 1
                    categoryName = "Vegetables"
                    categoryType = "Vegetables"
                    totalItems = 10
                },
                CategoryListItem().apply {
                    categoryID = 2
                    categoryName = "Fruits"
                    categoryType = "Fruits"
                    totalItems = 15
                }
            ),
            onEvent = {}
        )
    }
}