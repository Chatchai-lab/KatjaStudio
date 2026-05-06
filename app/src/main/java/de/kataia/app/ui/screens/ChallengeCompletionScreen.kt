package de.kataia.app.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
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
    //Launcher um Fotos aus der Galerie zu wählen
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onImageSelected(uri) }
    )
    //Launcher für das Foto-Ergebnis
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            viewModel.onImageCaptured(bitmap)
        }
    )
    //Launcher für die Berechtigungs-Anfrage
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(null)
            }else{

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

            // 1. Dauer Eingabefeld (verknüpft mit ViewModel)
            OutlinedTextField(
                value = viewModel.duration,
                onValueChange = { viewModel.duration = it },
                label = { Text("Dauer (in Minuten)") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("z.B. 20") },
                enabled = !viewModel.isSaving
            )

            // 2. Notiz Eingabefeld (verknüpft mit ViewModel)
            OutlinedTextField(
                value = viewModel.note,
                onValueChange = { viewModel.note = it },
                label = { Text("Deine Gedanken (optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = { Text("Was hast du heute gelernt?") },
                enabled = !viewModel.isSaving
            )

            // 3. Foto-Sektion
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //Galerie Button
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
                //Kamera Button
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
            // Status-Anzeige
            if (viewModel.selectedImageUri != null || viewModel.capturedBitmap != null) {
                Text("Bild ausgewählt ✓", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.weight(1f))


            //4. Der Abschließen Button mit Lade-Indikator
            val context = androidx.compose.ui.platform.LocalContext.current
            Button(
                onClick = {
                    viewModel.saveCompletion(context, challengeId){
                        onNavigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !viewModel.isSaving // Verhindert Doppelklicks
            ){
                if (viewModel.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                }else{
                    Text("Erfolg speichern")
                }
            }
        }
    }
}