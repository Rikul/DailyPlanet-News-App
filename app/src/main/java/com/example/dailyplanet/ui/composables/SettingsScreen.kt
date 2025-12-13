package com.example.dailyplanet.ui.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyplanet.R
import com.example.dailyplanet.repository.AppFont
import com.example.dailyplanet.repository.TextSize
import com.example.dailyplanet.repository.ViewType
import com.example.dailyplanet.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val textSize by viewModel.textSize.collectAsState()
    val font by viewModel.font.collectAsState()
    val viewType by viewModel.viewType.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Article Display Section
        Text(
            text = "Article Display",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Text Size
        SettingOption(title = "Text Size") {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextSize.values().forEach { size ->
                    val fontSize = when (size) {
                        TextSize.Small -> 12.sp
                        TextSize.Medium -> 14.sp
                        TextSize.Large -> 16.sp
                    }
                    SelectableChip(
                        selected = textSize == size,
                        text = size.name,
                        onClick = { viewModel.setTextSize(size) },
                        textStyle = TextStyle(fontSize = fontSize)
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // Font
        SettingOption(title = "Font") {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppFont.values().forEach { f ->
                    val fontFamily = when (f) {
                        AppFont.Default -> FontFamily.Default
                        AppFont.Serif -> FontFamily.Serif
                        AppFont.Monospace -> FontFamily.Monospace
                    }
                    SelectableChip(
                        selected = font == f,
                        text = f.name,
                        onClick = { viewModel.setFont(f) },
                        textStyle = TextStyle(fontFamily = fontFamily)
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        // View Type
        SettingOption(title = "View Type") {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ViewType.values().forEach { type ->
                    SelectableChip(
                        selected = viewType == type,
                        text = if (type == ViewType.HeadlinesOnly) "Headlines" else "Tile",
                        onClick = { viewModel.setViewType(type) }
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // About Section
        Text(
            text = "About DailyPlanet",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text("Version 1.0", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Developed by Mohammed Hamil P R", style = MaterialTheme.typography.bodyMedium)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // GitHub Button
            TextButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hamilwt"))
                context.startActivity(intent)
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_github_logo),
                        contentDescription = "GitHub Logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("GitHub", color = MaterialTheme.colorScheme.secondary)
                }
            }

            // LinkedIn Button
            TextButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mohammed-hamil-pr/"))
                context.startActivity(intent)
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_linkedin_logo),
                        contentDescription = "LinkedIn Logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("LinkedIn", color = MaterialTheme.colorScheme.secondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Powered by NewsAPI.org", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun SettingOption(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableChip(
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
    textStyle: TextStyle = LocalTextStyle.current
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text, style = textStyle) },
        leadingIcon = if (selected) {
            { Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
        } else null
    )
}
