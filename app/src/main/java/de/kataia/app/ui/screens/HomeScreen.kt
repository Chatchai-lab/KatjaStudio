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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val repository = remember { FakeChallengeRepository(context) }

    // Status für die Filterung
    var selectedCategory by remember { mutableStateOf("Alle") }
    val categories = listOf("Alle", "SHAPES", "ANATOMY", "STILL_LIFE", "CREATIVE")

    // Die Liste filtern, basierend auf der Auswahl
    val challenges = remember(selectedCategory) {
        if (selectedCategory == "Alle") {
            repository.getAllChallenges()
        } else {
            repository.getAllChallenges().filter { it.category.name == selectedCategory }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Katjas Zeichen-Coach") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

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
                items(challenges) { challenge ->
                    ChallengeItem(challenge)
                }
            }
        }
    }
}

@Composable
fun ChallengeItem(challenge: Challenge) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Titel & Schwierigkeit
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

            // Beschreibung
            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.typography.bodyMedium.color.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Footer: Kategorie & Dauer
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