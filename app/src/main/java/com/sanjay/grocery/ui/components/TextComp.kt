package com.sanjay.grocery.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sanjay.grocery.ui.theme.TextDark

@Composable
fun DefaultText(
    text: String,
    fontSize: TextUnit = 14.sp,
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize
        )
    )
}

@Composable
fun ErrorText(
    text: String,
    modifier: Modifier,
    fontSize: TextUnit = 12.sp,
    fontStyle: FontStyle = FontStyle.Italic,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.Red,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle
        ),
        color = textColor,
        textAlign = textAlign
    )
}

@Composable
fun Text11(
    text: String,
    modifier: Modifier,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.Black,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = 11.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle
        ),
        color = textColor,
        textAlign = textAlign
    )
}

@Composable
fun Text12(
    text: String,
    modifier: Modifier,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.Black,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle
        ),
        color = textColor,
        textAlign = textAlign
    )
}

@Composable
fun Text14(
    text: String,
    modifier: Modifier,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.Black,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle
        ),
        color = textColor,
        textAlign = textAlign
    )
}

@Composable
fun Text16(
    text: String,
    modifier: Modifier,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = TextDark,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle
        ),
        color = textColor,
        textAlign = textAlign,
        overflow = TextOverflow.Clip
    )
}

@Composable
fun Text18(
    text: String,
    modifier: Modifier,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = TextDark,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle
        ),
        color = textColor,
        textAlign = textAlign,
        overflow = TextOverflow.Clip
    )
}

@Composable
fun CustomText(
    text: String,
    modifier: Modifier,
    fontSize: TextUnit,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = FontFamily.Default,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = TextDark,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily
        ),
        color = textColor,
        textAlign = textAlign
    )
}


@Composable
fun CustomAnnotatedText(
    text: AnnotatedString,
    modifier: Modifier,
    fontSize: TextUnit,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = FontFamily.Default,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = TextDark,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily
        ),
        color = textColor,
        textAlign = textAlign
    )
}