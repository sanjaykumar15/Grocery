package com.sanjay.grocery.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanjay.grocery.R
import com.sanjay.grocery.navigation.CardDetailsNav
import com.sanjay.grocery.ui.components.CustomText
import com.sanjay.grocery.ui.components.TextFieldWithPlaceHolder
import com.sanjay.grocery.ui.components.TopBarComp
import com.sanjay.grocery.ui.events.CardDetailsEvents
import com.sanjay.grocery.ui.states.CardDetailsState
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.Black
import com.sanjay.grocery.ui.theme.PrimaryClr
import com.sanjay.grocery.ui.theme.TextDark
import com.sanjay.grocery.ui.theme.White

@Composable
fun CardDetailsScreen(
    state: CardDetailsState,
    navItem: CardDetailsNav,
    onEvent: (CardDetailsEvents) -> Unit,
) {
    if (state.isInit) {
        onEvent(
            CardDetailsEvents.OnInit(
                typeId = navItem.typeId,
                cardNo = navItem.cardNumber,
                typeName = navItem.typeName
            )
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,
        topBar = {
            TopBarComp(
                title = "${state.categoryItem == null}",
                onBackClick = {
                    onEvent(CardDetailsEvents.OnBackPressed)
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomText(
                    text = stringResource(R.string.credit_debit_card),
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
            }
        }
    }
}