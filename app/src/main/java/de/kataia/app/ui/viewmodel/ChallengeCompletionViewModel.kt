package de.kataia.app.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.kataia.app.data.ChallengeRepository
import de.kataia.app.data.UserPreferencesRepository
import de.kataia.app.model.ChallengeCompletion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class ChallengeCompletionViewModel(
    private val repository: ChallengeRepository,
    private val userPrefsRepository: UserPreferencesRepository
) : ViewModel() {

    var duration by mutableStateOf("")
    var note by mutableStateOf("")
    var isSaving by mutableStateOf(false)
        private set

    var selectedImageUri by mutableStateOf<Uri?>(null)
    var capturedBitmap by mutableStateOf<Bitmap?>(null) // Name vereinheitlicht

    fun onImageSelected(uri: Uri?) {
        selectedImageUri = uri
        if (uri != null) capturedBitmap = null
    }

    fun onImageCaptured(bitmap: Bitmap?) {
        capturedBitmap = bitmap
        if (bitmap != null) selectedImageUri = null
    }

    // Die Speicher-Funktion (jetzt mit Bild-Logik)
    fun saveCompletion(context: Context, challengeId: String, onFinished: () -> Unit) {
        if (isSaving) return
        isSaving = true

        viewModelScope.launch {
            // Bild permanent speichern und Pfad erhalten
            val imagePath = saveImageToInternalStorage(context)

            val completion = ChallengeCompletion(
                challengeId = challengeId,
                completedAt = System.currentTimeMillis(),
                durationMinutes = duration.toIntOrNull() ?: 0,
                note = note.ifBlank { null },
                imagePath = imagePath // Hier speichern wir den Pfad zur Datei!
            )

            repository.saveChallengeCompletion(completion)
            userPrefsRepository.updateXp(50)
            userPrefsRepository.saveLastCompletionTime(System.currentTimeMillis())

            isSaving = false
            onFinished()
        }
    }

    // Hilfsfunktion zum Speichern der Datei
    private suspend fun saveImageToInternalStorage(context: Context): String? = withContext(Dispatchers.IO) {
        val fileName = "completion_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)

        try {
            if (capturedBitmap != null) {
                // Kamera-Bitmap speichern
                FileOutputStream(file).use { out ->
                    capturedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
                }
                return@withContext file.absolutePath
            } else if (selectedImageUri != null) {
                // Galerie-Bild kopieren
                context.contentResolver.openInputStream(selectedImageUri!!)?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
                return@withContext file.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }
}