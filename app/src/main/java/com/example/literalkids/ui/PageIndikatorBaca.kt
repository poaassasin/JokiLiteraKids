package com.example.literalkids.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.literalkids.R

@Composable
fun PageIndicatorBaca(
    currentPage: Int,
    onPageChange: (Int) -> Unit,
    pageCount: Int = 5
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (currentPage > 0) {
            Icon(
                painter = painterResource(id = R.drawable.tombol_back),
                contentDescription = "Previous",
                tint = Color(0xFF5AD8FF),
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onPageChange(currentPage - 1) }
            )
        } else {
            Spacer(modifier = Modifier.width(30.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(pageCount) { index ->
                Image(
                    painter = painterResource(
                        id = if (index <= currentPage) R.drawable.star_filled else R.drawable.star_empty
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        if (currentPage < pageCount - 1) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = "Next",
                tint = Color(0xFF5AD8FF),
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onPageChange(currentPage + 1) }
            )
        } else {
            Spacer(modifier = Modifier.width(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPageIndicatorBaca() {
    var currentPage by remember { mutableStateOf(1) } // Misal: halaman ke-3

    PageIndicatorBaca(
        currentPage = currentPage,
        onPageChange = { currentPage = it },
        pageCount = 5
    )
}

