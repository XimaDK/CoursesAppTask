package kadyshev.dmitry.coursesapp.viewmodels

import androidx.lifecycle.ViewModel
import kadyshev.dmitry.domain.usecases.IsOnboardingCompletedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase
) : ViewModel() {

    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted = _isOnboardingCompleted.asStateFlow()

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        _isOnboardingCompleted.value = isOnboardingCompletedUseCase()
    }
}
