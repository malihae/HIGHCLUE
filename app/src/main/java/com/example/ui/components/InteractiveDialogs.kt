package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MiniQuizQuestion
import com.example.ui.theme.HighClueAmber
import com.example.ui.theme.HighClueEmerald
import com.example.ui.theme.HighClueIndigo
import com.example.ui.theme.HighClueRose
import com.example.ui.theme.HighClueTeal

@Composable
fun GeniallyViewerDialog(
    url: String,
    lessonTitle: String = "Interactive Genially Lesson",
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(HighClueIndigo.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TouchApp,
                        contentDescription = "Genially",
                        tint = HighClueIndigo,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Genially Interactive Lesson",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "app.genially.com interactive learning",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = lessonTitle,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "This lesson contains clickable hotspots, branch navigation, and interactive animations created for ages 11–17.",
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = HighClueTeal.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "Verified Genially Template",
                                    fontSize = 11.sp,
                                    color = HighClueTeal,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Launch in browser or the Genially app for the complete full-screen interactive canvas experience:",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // fallback
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = HighClueIndigo)
            ) {
                Icon(Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Launch Genially")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun MiniQuizDialog(
    quiz: MiniQuizQuestion,
    onQuizCompleted: (score: Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var hasSubmitted by remember { mutableStateOf(false) }

    val isCorrect = selectedIndex == quiz.correctIndex

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "Quiz",
                    tint = HighClueIndigo
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Module Checkpoint",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = quiz.question,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(14.dp))

                quiz.options.forEachIndexed { index, option ->
                    val isThisSelected = selectedIndex == index
                    val backgroundColor = when {
                        !hasSubmitted && isThisSelected -> HighClueIndigo.copy(alpha = 0.12f)
                        hasSubmitted && index == quiz.correctIndex -> HighClueEmerald.copy(alpha = 0.15f)
                        hasSubmitted && isThisSelected && index != quiz.correctIndex -> HighClueRose.copy(alpha = 0.15f)
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }

                    val borderColor = when {
                        !hasSubmitted && isThisSelected -> HighClueIndigo
                        hasSubmitted && index == quiz.correctIndex -> HighClueEmerald
                        hasSubmitted && isThisSelected && index != quiz.correctIndex -> HighClueRose
                        else -> Color.Transparent
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = backgroundColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                            .clickable(enabled = !hasSubmitted) {
                                selectedIndex = index
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isThisSelected || (hasSubmitted && index == quiz.correctIndex))
                                            HighClueIndigo
                                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A' + index).toString(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isThisSelected || (hasSubmitted && index == quiz.correctIndex)) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = option,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                if (hasSubmitted) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrect) HighClueEmerald.copy(alpha = 0.1f) else HighClueRose.copy(alpha = 0.1f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (isCorrect) HighClueEmerald else HighClueRose,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isCorrect) "Spot on! (+100 XP)" else "Nice try! Here's why:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isCorrect) HighClueEmerald else HighClueRose
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = quiz.explanation,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (!hasSubmitted) {
                Button(
                    onClick = {
                        if (selectedIndex != null) {
                            hasSubmitted = true
                        }
                    },
                    enabled = selectedIndex != null,
                    colors = ButtonDefaults.buttonColors(containerColor = HighClueIndigo)
                ) {
                    Text("Check Answer")
                }
            } else {
                Button(
                    onClick = {
                        onQuizCompleted(if (isCorrect) 100 else 60)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HighClueIndigo)
                ) {
                    Text("Continue")
                }
            }
        },
        dismissButton = {
            if (!hasSubmitted) {
                TextButton(onClick = onDismiss) {
                    Text("Skip for Now")
                }
            }
        }
    )
}
