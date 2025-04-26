package com.sanjay.grocery.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanjay.grocery.R

@Composable
fun ErrorView(
    errorMsg: String,
    retry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(15.dp)
    ) {
        ErrorText(
            text = errorMsg,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center,
            textColor = Color.Red,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier
                .heightIn(min = 48.dp)
                .wrapContentSize()
                .align(Alignment.CenterHorizontally)
                .clickable(enabled = true) {
                    retry()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text18(
                text = stringResource(R.string.retry),
                modifier = Modifier
                    .padding(7.dp)
            )
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Rounded.Refresh,
                contentDescription = null
            )
        }
    }
}

@Composable
fun BorderView(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    borderWidth: Dp = 0.1.dp,
    borderColor: Color = Color.LightGray,
    cornerRadius: Dp = 10.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    padding: Dp = 7.dp,
    contentView: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(
                border = BorderStroke(
                    width = borderWidth,
                    color = borderColor,
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(padding),
        contentAlignment = contentAlignment
    ) {
        contentView()
    }
}