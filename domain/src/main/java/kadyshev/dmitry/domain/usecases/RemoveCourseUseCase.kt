package kadyshev.dmitry.domain.usecases

import kadyshev.dmitry.domain.entities.Course
import kadyshev.dmitry.domain.repository.UserPreferences

class RemoveCourseUseCase(private val userPreferences: UserPreferences) {
    operator fun invoke(course: Course) = userPreferences.removeCourse(course)
}