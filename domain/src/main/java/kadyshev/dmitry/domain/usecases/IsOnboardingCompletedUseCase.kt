package kadyshev.dmitry.domain.usecases

import kadyshev.dmitry.domain.repository.UserPreferences

class IsOnboardingCompletedUseCase(private val userPreferences: UserPreferences) {
    operator fun invoke() = userPreferences.isOnboardingCompleted()
}