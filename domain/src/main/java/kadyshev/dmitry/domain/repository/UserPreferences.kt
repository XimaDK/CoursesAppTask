package kadyshev.dmitry.domain.repository

import kadyshev.dmitry.domain.entities.Course
import kotlinx.coroutines.flow.Flow


interface UserPreferences {
    fun isOnboardingCompleted(): Boolean
    fun setOnboardingCompleted()

    fun getLocalCoursesFlow(): Flow<List<Course>>
    fun saveCourse(course: Course)
    fun removeCourse(course: Course)
    fun getLocalCourses(): List<Course>
}
