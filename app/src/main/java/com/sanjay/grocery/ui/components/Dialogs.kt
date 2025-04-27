package com.sanjay.grocery.ui.components

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.HtmlCompat
import com.sanjay.grocery.R
import com.sanjay.grocery.ui.theme.LightGrey
import com.sanjay.grocery.ui.theme.PrimaryClr
import com.sanjay.grocery.ui.theme.White

@Composable
fun ProgressDialog(
    message: String? = null,
    isCancelable: Boolean = false,
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(White, shape = dialogCornerShape())
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                CircularProgressIndicator(
                    color = PrimaryClr
                )
                if (!message.isNullOrEmpty()) {
                    Text14(
                        modifier = Modifier.wrapContentSize(),
                        text = message
                    )
                }
            }
        }
    }
}

@Composable
fun AlertDialog(
    message: String,
    isCancelable: Boolean = true,
    positiveButtonText: String = "OK",
    negativeButtonText: String = "Cancel",
    onDialogDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDialogDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(White, shape = dialogCornerShape())
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { context ->
                        TextView(context).apply {
                            textSize = 16f
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            setTextColor(context.resources.getColor(R.color.black, null))
                        }
                    },
                    update = {
                        it.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                ) {
                    if (negativeButtonText.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = LightGrey.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    onDialogDismiss()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text14(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(vertical = 7.dp, horizontal = 20.dp),
                                text = negativeButtonText,
                                textColor = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                color = PrimaryClr,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                onConfirm()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text14(
                            modifier = Modifier
                                .padding(vertical = 7.dp, horizontal = 20.dp),
                            text = positiveButtonText,
                            textColor = White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun dialogCornerShape(): RoundedCornerShape {
    return RoundedCornerShape(10.dp)
}

@Preview
@Composable
private fun ProgressDialogPreview() {
    ProgressDialog(
        message = "Loading...",
        isCancelable = false
    )
}

@Preview
@Composable
private fun AlertDialogPreview() {
    AlertDialog(
        message = "<html>Are you sure you want to <b>delete</b> this item?</html>",
        onDialogDismiss = {},
        onConfirm = {}
    )
}