package kadyshev.dmitry.coursesapp.states

sealed class OnboardingState {

    data object Idle : OnboardingState()
    data object NavigateToLogin : OnboardingState()
}