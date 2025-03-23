package kadyshev.dmitry.domain.repository

import kadyshev.dmitry.domain.entities.Course

interface CoursesRepository {
    suspend fun fetchRemoteCourses(): List<Course>
}
