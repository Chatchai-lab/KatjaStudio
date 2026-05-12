package de.kataia.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.kataia.app.data.ChallengeRepository
import de.kataia.app.model.ChallengeCompletion
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class GalleryViewModel (
    private val repository: ChallengeRepository
    ) : ViewModel() {
        val galleryItems: StateFlow<List<ChallengeCompletion>> = repository
            .getRecentCompletions()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }