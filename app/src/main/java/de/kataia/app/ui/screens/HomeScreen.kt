package de.kataia.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.kataia.app.data.FakeChallengeRepository
import de.kataia.app.model.Challenge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // Wir holen uns die Test-Daten vom Fake-Lagerverwalter
    val repository = FakeChallengeRepository()
    val challenges = repository.getAllChallenges()

    Scaffold(
        topBar = {
            TopAppBar( // Wir nutzen die Standard TopAppBar
                title = { Text("Katjas Challenges") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(challenges) { challenge ->
                ChallengeItem(challenge)
            }
        }
    }
}



@Composable
fun ChallengeItem(challenge: Challenge) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = challenge.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "${challenge.category} • ${challenge.xpReward} XP", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = challenge.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}