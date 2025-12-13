package com.example.dailyplanet.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // <-- THIS IMPORT WAS MISSING
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dailyplanet.R
import com.example.dailyplanet.models.Article
import com.example.dailyplanet.repository.AppFont
import com.example.dailyplanet.repository.TextSize
import com.example.dailyplanet.repository.ViewType
import com.example.dailyplanet.ui.viewmodel.NewsViewModel

// Custom semantics property for font size testing
val FontSizeKey = SemanticsPropertyKey<TextUnit>("FontSize")
var SemanticsPropertyReceiver.fontSize by FontSizeKey

// Custom semantics property for font family testing
val FontFamilyKey = SemanticsPropertyKey<FontFamily>("FontFamily")
var SemanticsPropertyReceiver.fontFamily by FontFamilyKey

@Composable
fun NewsCard(
    article: Article,
    viewModel: NewsViewModel,
    isFavorite: Boolean,
    textSize: TextSize,
    font: AppFont,
    viewType: ViewType
) {
    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()

    val titleFontSize = when (textSize) {
        TextSize.Small -> 16.sp
        TextSize.Medium -> 20.sp
        TextSize.Large -> 24.sp
    }
    
    val bodyFontSize = when (textSize) {
        TextSize.Small -> 12.sp
        TextSize.Medium -> 14.sp
        TextSize.Large -> 16.sp
    }

    val fontFamily = when (font) {
        AppFont.Default -> FontFamily.Default
        AppFont.Serif -> FontFamily.Serif
        AppFont.Monospace -> FontFamily.Monospace
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                article.url.let { url ->
                    val builder = CustomTabsIntent.Builder()
                    builder.setDefaultColorSchemeParams(
                        androidx.browser.customtabs.CustomTabColorSchemeParams.Builder()
                            .setToolbarColor(primaryColor)
                            .build()
                    )
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(url))
                }
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (viewType != ViewType.HeadlinesOnly) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .build(),
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .testTag("NewsImage")
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.source?.name ?: "Unknown Source",
                    style = MaterialTheme.typography.labelMedium.copy(fontFamily = fontFamily),
                    color = Color.Red
                )
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = titleFontSize, fontFamily = fontFamily),
                    maxLines = if (viewType == ViewType.HeadlinesOnly) 3 else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .semantics {
                            fontSize = titleFontSize
                            this.fontFamily = fontFamily
                        }
                        .testTag("ArticleTitle")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (viewType != ViewType.HeadlinesOnly) {
                        Text(
                            text = article.description ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = bodyFontSize, fontFamily = fontFamily),
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("ArticleDescription")
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Row {
                        IconButton(onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, article.url)
                                putExtra(Intent.EXTRA_TITLE, article.title)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(onClick = {
                            if (isFavorite) {
                                viewModel.deleteArticle(article)
                            } else {
                                viewModel.saveArticle(article)
                            }
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}