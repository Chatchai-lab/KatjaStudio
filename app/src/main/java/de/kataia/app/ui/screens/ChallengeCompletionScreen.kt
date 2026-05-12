package de.kataia.app.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.kataia.app.ui.viewmodel.ChallengeCompletionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeCompletionScreen(
    challengeId: String,
    viewModel: ChallengeCompletionViewModel,
    onNavigateBack: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    // Launcher um Fotos aus der Galerie zu wählen
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onImageSelected(uri) }
    )

    // Launcher für das Foto-Ergebnis (Kamera)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            viewModel.onImageCaptured(bitmap)
        }
    )

    // Launcher für die Berechtigungs-Anfrage
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(null)
            }else{
                android.widget.Toast.makeText(
                        context,
                        "Kamera-Berechtigung wird für Fotos benötigt",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Aufgabe abgeschlossen!") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Super gemacht Popo! Wie lief's?",
                style = MaterialTheme.typography.headlineSmall
            )

            // 1. Dauer Eingabefeld
            OutlinedTextField(
                value = viewModel.duration,
                onValueChange = { viewModel.duration = it },
                label = { Text("Dauer (in Minuten)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("z.B. 20") },
                enabled = !viewModel.isSaving
            )

            // 2. Notiz Eingabefeld
            OutlinedTextField(
                value = viewModel.note,
                onValueChange = { viewModel.note = it },
                label = { Text("Deine Gedanken (optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Was hast du heute gelernt?") },
                enabled = !viewModel.isSaving
            )

            // 3. Foto-Sektion (Buttons nebeneinander)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(if (viewModel.selectedImageUri == null) "Galerie" else "Bild ändern")
                }

                Button(
                    onClick = {
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(if (viewModel.capturedBitmap == null) "Kamera" else "Neues Foto")
                }
            }

            if (viewModel.selectedImageUri != null || viewModel.capturedBitmap != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Vorschau ausgewählt ✓",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier.size(160.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        androidx.compose.foundation.Image(
                            painter = coil.compose.rememberAsyncImagePainter(
                                model = viewModel.selectedImageUri ?: viewModel.capturedBitmap
                            ),
                            contentDescription = "Vorschau deiner Zeichnung",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 4. Der Abschließen Button mit Validierung
            // Validierung: Dauer darf nicht leer sein und muss eine Zahl sein
            val isInputValid = viewModel.duration.isNotEmpty() && viewModel.duration.toIntOrNull() != null

            Button(
                onClick = {
                    viewModel.saveCompletion(context, challengeId) {
                        onNavigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !viewModel.isSaving && isInputValid // Aktiviert Button nur bei validem Input
            ) {
                if (viewModel.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("In Galerie verewigen (+50 XP)")
                }
            }
        }
    }
}