package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CourseRepository
import com.example.model.AssessmentResult
import com.example.model.MlDatasetDocumentation
import com.example.ui.viewmodel.AssessmentQuestion
import com.example.ui.theme.HighClueAmber
import com.example.ui.theme.HighClueEmerald
import com.example.ui.theme.HighClueIndigo
import com.example.ui.theme.HighClueTeal

@Composable
fun InterestLevelerScreen(
    questions: List<AssessmentQuestion>,
    answers: Map<String, Float>,
    latestResult: AssessmentResult?,
    onAnswerChange: (dimensionId: String, score: Float) -> Unit,
    onSubmit: () -> Unit,
    onCourseClick: (String) -> Unit
) {
    var showResultsView by remember { mutableStateOf(latestResult != null) }
    var showDatasetDoc by remember { mutableStateOf(false) }

    if (showResultsView && latestResult != null) {
        AssessmentResultsView(
            result = latestResult,
            onRetake = { showResultsView = false },
            onCourseClick = onCourseClick,
            onToggleDatasetDoc = { showDatasetDoc = !showDatasetDoc },
            showDatasetDoc = showDatasetDoc
        )
    } else {
        AssessmentQuestionnaireView(
            questions = questions,
            answers = answers,
            onAnswerChange = onAnswerChange,
            onSubmit = {
                onSubmit()
                showResultsView = true
            }
        )
    }
}

@Composable
private fun AssessmentQuestionnaireView(
    questions: List<AssessmentQuestion>,
    answers: Map<String, Float>,
    onAnswerChange: (String, Float) -> Unit,
    onSubmit: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(HighClueIndigo.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = HighClueIndigo,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Interest Leveler",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "14 dimensions • Career exploration, not prediction",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Rate how much you resonate with each statement from 1 (Not for me) to 5 (Love it!). There are no right or wrong answers!",
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }

        items(questions) { q ->
            val currentVal = answers[q.dimensionId] ?: 0.5f

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = q.dimensionName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = HighClueIndigo
                        )

                        val ratingLabel = when {
                            currentVal < 0.25f -> "1 - Not for me"
                            currentVal < 0.45f -> "2 - A little"
                            currentVal < 0.65f -> "3 - Neutral"
                            currentVal < 0.85f -> "4 - Enjoy it"
                            else -> "5 - Love it!"
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = HighClueIndigo.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = ratingLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HighClueIndigo,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = q.statement,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = currentVal,
                        onValueChange = { onAnswerChange(q.dimensionId, it) },
                        valueRange = 0.1f..1.0f,
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = HighClueIndigo,
                            activeTrackColor = HighClueIndigo,
                            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }

        item {
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HighClueIndigo)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Generate Career Exploration Profile",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun AssessmentResultsView(
    result: AssessmentResult,
    onRetake: () -> Unit,
    onCourseClick: (String) -> Unit,
    onToggleDatasetDoc: () -> Unit,
    showDatasetDoc: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Results Header
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Your Exploration Profile",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedButton(
                            onClick = onRetake,
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Retake", fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Based on your 14 assessment dimensions, here are the pathways that best match your natural curiosity and problem-solving style:",
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "🌟 Top Interest Areas",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        result.topInterestAreas.forEach { area ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = HighClueIndigo.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = area,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HighClueIndigo,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Suggested Courses
        item {
            Text(
                text = "Suggested Exploration Courses",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                result.suggestedCourseIds.forEach { courseId ->
                    val course = CourseRepository.getCourseById(courseId)
                    if (course != null) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCourseClick(course.id) }
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = course.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${course.domain} • ${course.estimatedTime}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = HighClueIndigo,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Suggested Skills and Recommended Next Steps
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🚀 Recommended Next Steps",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    result.recommendedNextSteps.forEach { step ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text(text = "•", fontSize = 14.sp, color = HighClueIndigo, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = step,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                            )
                        }
                    }
                }
            }
        }

        // REQUIRED DISCLAIMER
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = HighClueAmber.copy(alpha = 0.12f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Disclaimer",
                        tint = HighClueAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Important Note for Students & Parents",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = HighClueAmber
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "These are exploration recommendations, not a definitive career decision. Interests change as you learn and grow. Use these insights to experiment with subjects before committing to high school streams.",
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Kaggle ML Dataset Documentation Viewer
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "AI / ML Model Transparency",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(onClick = onToggleDatasetDoc) {
                            Text(if (showDatasetDoc) "Hide" else "View Dataset & Metrics", fontSize = 11.sp, color = HighClueIndigo)
                        }
                    }

                    if (showDatasetDoc) {
                        val doc = MlDatasetDocumentation()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Dataset: ${doc.datasetName}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Text("Source: ${doc.kaggleSource}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("License: ${doc.license}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Model: ${doc.modelUsed}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Text("Validation: ${doc.evaluationMetrics}", fontSize = 10.sp, color = HighClueTeal)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Limitations: ${doc.limitations}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    }
                }
            }
        }
    }
}
