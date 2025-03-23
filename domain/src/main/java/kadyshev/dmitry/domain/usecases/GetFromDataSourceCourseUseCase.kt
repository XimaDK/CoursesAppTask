package kadyshev.dmitry.domain.usecases

import kadyshev.dmitry.domain.entities.Course
import kadyshev.dmitry.domain.repository.UserPreferences
import kotlinx.coroutines.flow.Flow

class GetFromDataSourceCourseUseCase(
    private val repository: UserPreferences
) {
    operator fun invoke(): Flow<List<Course>> = repository.getLocalCoursesFlow()
}