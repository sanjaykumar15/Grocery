package com.sanjay.grocery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanjay.grocery.R
import com.sanjay.grocery.ui.components.CustomText
import com.sanjay.grocery.ui.components.DefaultText
import com.sanjay.grocery.ui.components.Text16
import com.sanjay.grocery.ui.events.MainScreenEvents
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.Gray
import com.sanjay.grocery.ui.theme.Green
import com.sanjay.grocery.ui.theme.TextDark
import com.sanjay.grocery.ui.theme.TextSecondary
import com.sanjay.grocery.ui.theme.White

@Composable
fun MainScreen(
    onEvent: (MainScreenEvents) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.main_bg),
            contentDescription = stringResource(R.string.background_image)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .background(
                    color = Background,
                    shape = RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(10.dp),
                painter = painterResource(R.drawable.ic_box),
                contentDescription = stringResource(R.string.box_image)
            )

            CustomText(
                text = stringResource(R.string.non_contact_deliveries),
                modifier = Modifier.wrapContentSize(),
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text16(
                text = stringResource(R.string.main_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                textColor = TextSecondary,
                textAlign = TextAlign.Center
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
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
                        onEvent(MainScreenEvents.OnOrderNow)
                    }
                ) {
                    DefaultText(
                        text = stringResource(R.string.order_now)
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonColors(
                        containerColor = White,
                        contentColor = TextDark,
                        disabledContentColor = TextDark,
                        disabledContainerColor = Gray,
                    ),
                    onClick = {
                        onEvent(MainScreenEvents.OnDismiss)
                    }
                ) {
                    DefaultText(
                        text = stringResource(R.string.dismiss)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        onEvent = {}
    )
}