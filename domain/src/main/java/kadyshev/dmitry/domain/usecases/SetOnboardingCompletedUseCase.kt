package kadyshev.dmitry.domain.usecases

import kadyshev.dmitry.domain.repository.UserPreferences

class SetOnboardingCompletedUseCase(private val userPreferences: UserPreferences) {
    operator fun invoke() = userPreferences.setOnboardingCompleted()
}