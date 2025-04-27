package com.sanjay.grocery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.sanjay.grocery.R
import com.sanjay.grocery.navigation.CardDetailsNav
import com.sanjay.grocery.ui.components.BorderView
import com.sanjay.grocery.ui.components.CustomText
import com.sanjay.grocery.ui.components.DefaultText
import com.sanjay.grocery.ui.components.Text12
import com.sanjay.grocery.ui.components.Text18
import com.sanjay.grocery.ui.components.TextFieldWithPlaceHolderWithValue
import com.sanjay.grocery.ui.components.TopBarComp
import com.sanjay.grocery.ui.events.CardDetailsEvents
import com.sanjay.grocery.ui.states.CardDetailsState
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.Black
import com.sanjay.grocery.ui.theme.Gray
import com.sanjay.grocery.ui.theme.Green
import com.sanjay.grocery.ui.theme.TextDark
import com.sanjay.grocery.ui.theme.White
import com.sanjay.grocery.ui.util.FieldType
import com.sanjay.grocery.ui.util.FormatterUtil
import com.sanjay.grocery.ui.util.InputTransformation

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
                title = "",
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                item {
                    CustomText(
                        text = stringResource(R.string.credit_debit_card),
                        modifier = Modifier.wrapContentSize(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            painter = painterResource(R.drawable.credit_card),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "CardImage"
                        )

                        CustomText(
                            text = FormatterUtil.cardNumFormat(state.cardNumber),
                            modifier = Modifier
                                .align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            textColor = White,
                            fontSize = 24.sp
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .align(Alignment.BottomCenter),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text18(
                                text = FormatterUtil.cardHolderFormat(state.cardHolderName),
                                modifier = Modifier.wrapContentSize(),
                                textColor = White
                            )

                            Text18(
                                text = FormatterUtil.expireDateFormat(state.expiryDate),
                                modifier = Modifier.wrapContentSize(),
                                textColor = White
                            )
                        }
                    }
                }

                item {
                    EditFieldView(
                        modifier = Modifier.fillMaxWidth(),
                        hintText = stringResource(R.string.name_on_card),
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        value = state.cardHolderName,
                        onValueChange = {
                            onEvent(
                                CardDetailsEvents.OnCardHolderNameChange(
                                    name = it
                                )
                            )
                        }
                    )
                }

                item {
                    EditFieldView(
                        modifier = Modifier.fillMaxWidth(),
                        hintText = stringResource(R.string.card_number),
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        isOnlyNumber = true,
                        value = state.cardNumber,
                        onValueChange = {
                            if (it.length <= 16 && it.isDigitsOnly()) {
                                onEvent(
                                    CardDetailsEvents.OnCardNoChange(
                                        number = if (it.length >= 16) it.substring(0..15) else it
                                    )
                                )
                            }
                        },
                        visualTransformation = InputTransformation(FieldType.CARD_NUMBER)
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        EditFieldView(
                            modifier = Modifier.weight(1f),
                            hintText = stringResource(R.string.expiry_date),
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next,
                            isOnlyNumber = true,
                            value = state.expiryDate,
                            onValueChange = {
                                if (it.length <= 4 && it.isDigitsOnly()) {
                                    onEvent(
                                        CardDetailsEvents.OnExpiryDateChange(
                                            date = if (it.length >= 4) it.substring(0..3) else it
                                        )
                                    )
                                }
                            },
                            visualTransformation = InputTransformation(FieldType.EXPIRE_DATE)
                        )

                        EditFieldView(
                            modifier = Modifier.weight(1f),
                            hintText = stringResource(R.string.cvc),
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                            isOnlyNumber = true,
                            showCvvHint = true,
                            maxLength = 3,
                            value = state.cvv,
                            onValueChange = {
                                if (it.length <= 3 && it.isDigitsOnly()) {
                                    onEvent(
                                        CardDetailsEvents.OnCvvChange(
                                            cvv = it
                                        )
                                    )
                                }
                            }
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(0.8f),
                            shape = ButtonDefaults.outlinedShape,
                            colors = ButtonColors(
                                containerColor = Green,
                                contentColor = White,
                                disabledContentColor = TextDark,
                                disabledContainerColor = Gray,
                            ),
                            onClick = {
                                onEvent(CardDetailsEvents.Submit)
                            }
                        ) {
                            DefaultText(
                                text = stringResource(R.string.use_this_card)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EditFieldView(
    modifier: Modifier,
    hintText: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    isOnlyNumber: Boolean = false,
    onValueChange: (String) -> Unit,
    showCvvHint: Boolean = false,
    maxLength: Int? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    value: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text12(
            text = hintText,
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 8.dp),
        )

        BorderView(
            modifier = Modifier
                .fillMaxWidth(),
            padding = 0.dp,
            borderColor = Black,
            borderWidth = 1.dp,
            backgroundColor = Color.Transparent
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextFieldWithPlaceHolderWithValue(
                    placeholder = hintText,
                    modifier = Modifier.weight(1f),
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = TextDark,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    onValueChange = onValueChange,
                    visualTransformation = visualTransformation,
                    value = value
                )

                if (showCvvHint) {
                    Image(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(R.drawable.ic_cvv_hint),
                        contentDescription = stringResource(R.string.cvc),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CardDetailsScreenPreview() {
    CardDetailsScreen(
        state = CardDetailsState(),
        navItem = CardDetailsNav(0, "", ""),
        onEvent = {}
    )
}