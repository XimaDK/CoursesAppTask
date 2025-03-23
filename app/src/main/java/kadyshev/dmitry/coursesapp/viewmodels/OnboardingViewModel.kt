package kadyshev.dmitry.coursesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kadyshev.dmitry.coursesapp.states.OnboardingState
import kadyshev.dmitry.domain.usecases.SetOnboardingCompletedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OnboardingViewModel(
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OnboardingState>(OnboardingState.Idle)
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun onContinueClicked() {
        viewModelScope.launch {
            setOnboardingCompletedUseCase()
            _state.value = OnboardingState.NavigateToLogin
        }
    }
}
