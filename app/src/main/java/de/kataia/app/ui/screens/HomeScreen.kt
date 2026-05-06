package de.kataia.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.kataia.app.data.FakeChallengeRepository
import de.kataia.app.model.Challenge
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import de.kataia.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    xpCount: Int,
    onChallengeClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val repository = remember { FakeChallengeRepository(context) }

    val dailyChallenge = remember { repository.getDailyChallenge() }

    var selectedCategory by remember { mutableStateOf("Alle") }
    val categories = listOf("Alle", "SHAPES", "ANATOMY", "STILL_LIFE", "CREATIVE")

    val challenges = remember(selectedCategory) {
        if (selectedCategory == "Alle") {
            repository.getAllChallenges()
        } else {
            repository.getAllChallenges().filter { it.category.name == selectedCategory }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Image(painter = painterResource(id = R.drawable.maskottchen), contentDescription = null)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "kataia",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                actions = {
                    Surface(
                        modifier = Modifier.padding(end = 16.dp),
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⭐ $xpCount XP",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            )
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            //Kleiner Willkommens-Text mit XP-Fortschritt
            Text(
                text = "Willkommen zurück du kleiner Popo!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            // 1. Filter-Zeile (Chips)
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = (selectedCategory == category),
                        onClick = { selectedCategory = category },
                        label = { Text(category) }
                    )
                }
            }

            // 2. Die Challenge-Liste
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (dailyChallenge != null && selectedCategory == "Alle") {
                    item {
                        Text(
                            text = "Deine heutige Aufgabe",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        DailyChallengeItem(
                            challenge = dailyChallenge,
                            onClick = { onChallengeClick(dailyChallenge.id) }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        Text(
                            text = "Entdecke mehr",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
                items(challenges.filter { it.id != dailyChallenge?.id }) { challenge ->
                    ChallengeItem(
                        challenge = challenge,
                        onClick = { onChallengeClick(challenge.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ChallengeItem(
    challenge: Challenge,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = challenge.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                DifficultyBadge(challenge.difficulty.name)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium,color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "#${challenge.category}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "${challenge.estimatedMinutes} Min.",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )

                Text(
                    text = "${challenge.xpReward} XP",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun DifficultyBadge(difficulty: String) {
    val color = when(difficulty) {
        "EASY" -> Color(0xFF4CAF50)
        "MEDIUM" -> Color(0xFFFF9800)
        "HARD" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, color)
    ) {
        Text(
            text = difficulty,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
fun DailyChallengeItem(
    challenge: Challenge,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = challenge.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                DifficultyBadge(challenge.difficulty.name)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "#${challenge.category}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${challenge.estimatedMinutes} Min.",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = "${challenge.xpReward} XP",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
