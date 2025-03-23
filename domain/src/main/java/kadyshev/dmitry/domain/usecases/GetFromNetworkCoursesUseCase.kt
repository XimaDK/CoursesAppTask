package kadyshev.dmitry.domain.usecases

import kadyshev.dmitry.domain.entities.Course
import kadyshev.dmitry.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFromNetworkCoursesUseCase(
    private val repository: CoursesRepository
) {
    operator fun invoke(): Flow<List<Course>> = flow {
        emit(repository.fetchRemoteCourses())
    }
}