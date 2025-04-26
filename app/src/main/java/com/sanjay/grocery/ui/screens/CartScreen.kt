package com.sanjay.grocery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanjay.grocery.R
import com.sanjay.grocery.core.Constants
import com.sanjay.grocery.core.Constants.BY_COURIER
import com.sanjay.grocery.core.Constants.BY_DRONE
import com.sanjay.grocery.core.Constants.BY_MYSELF
import com.sanjay.grocery.models.PaymentData
import com.sanjay.grocery.ui.components.CustomText
import com.sanjay.grocery.ui.components.Text14
import com.sanjay.grocery.ui.events.CartEvents
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.PrimaryClr
import com.sanjay.grocery.ui.theme.SecondaryClr
import com.sanjay.grocery.ui.theme.White

@Composable
fun CartScreen(
    paymentData: PaymentData,
    onEvent: (CartEvents) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(35.dp),
    ) {
        item {
            HeaderView(
                headerText = stringResource(R.string.payment_method),
                onChangeClick = {
                    onEvent(
                        CartEvents.OnChangeClicked(
                            changeFor = Constants.CHANGE_FOR_CARD
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            ContentView(
                contentText = paymentData.cardNumber.ifEmpty { "No Card Data" },
                imagePainter = painterResource(R.drawable.ic_card),
                description = stringResource(R.string.payment_method),
                onClick = {
                    onEvent(CartEvents.OnPaymentInit)
                }
            )
        }

        item {
            HeaderView(
                headerText = stringResource(R.string.delivery_address),
                onChangeClick = {
                    onEvent(
                        CartEvents.OnChangeClicked(
                            changeFor = Constants.CHANGE_FOR_ADDRESS
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            ContentView(
                contentText = paymentData.address.ifEmpty { "No Address" },
                imagePainter = painterResource(R.drawable.ic_address_home),
                description = stringResource(R.string.delivery_address)
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                HeaderView(
                    headerText = stringResource(R.string.delivery_option),
                    onChangeClick = {
                        onEvent(
                            CartEvents.OnChangeClicked(
                                changeFor = Constants.CHANGE_FOR_DELIVERY
                            )
                        )
                    }
                )

                ContentView(
                    contentText = stringResource(R.string.delivery_by_myself),
                    imagePainter = painterResource(R.drawable.ic_walk),
                    description = stringResource(R.string.delivery_by_myself),
                    isSelected = paymentData.deliveryMethod == BY_MYSELF,
                    onClick = {
                        onEvent(
                            CartEvents.OnDeliveryOptionClicked(
                                option = BY_MYSELF
                            )
                        )
                    }
                )

                ContentView(
                    contentText = stringResource(R.string.delivery_by_courier),
                    imagePainter = painterResource(R.drawable.ic_bike),
                    description = stringResource(R.string.delivery_by_courier),
                    isSelected = paymentData.deliveryMethod == BY_COURIER,
                    onClick = {
                        onEvent(
                            CartEvents.OnDeliveryOptionClicked(
                                option = BY_COURIER
                            )
                        )
                    }
                )

                ContentView(
                    contentText = stringResource(R.string.delivery_by_drone),
                    imagePainter = painterResource(R.drawable.ic_drone),
                    description = stringResource(R.string.delivery_by_drone),
                    isSelected = paymentData.deliveryMethod == BY_DRONE,
                    onClick = {
                        onEvent(
                            CartEvents.OnDeliveryOptionClicked(
                                option = BY_DRONE
                            )
                        )
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = "Non-Contact Delivery",
                    modifier = Modifier,
                    fontSize = 22.sp
                )

                Switch(
                    modifier = Modifier.heightIn(),
                    checked = true,
                    onCheckedChange = {

                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = White,
                        checkedTrackColor = SecondaryClr,
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedTrackColor = Color.LightGray
                    )
                )
            }
        }
    }
}

@Composable
private fun HeaderView(
    headerText: String,
    onChangeClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomText(
            text = headerText,
            modifier = Modifier,
            fontSize = 22.sp
        )

        Text14(
            text = stringResource(R.string.change),
            modifier = Modifier
                .clickable(onChangeClick != null) {
                    onChangeClick!!()
                },
            textColor = PrimaryClr,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ContentView(
    contentText: String,
    imagePainter: Painter,
    description: String,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) {
                onClick!!()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = description
        )

        Text14(
            text = contentText,
            modifier = Modifier.weight(1f),
        )

        if (isSelected) {
            Image(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = stringResource(R.string.selected)
            )
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Background
    ) {
        CartScreen(
            paymentData = PaymentData()
        )
    }
}