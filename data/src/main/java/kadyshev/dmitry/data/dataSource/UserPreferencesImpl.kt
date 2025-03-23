package kadyshev.dmitry.data.dataSource

import android.content.SharedPreferences
import android.util.Log
import kadyshev.dmitry.data.mapper.Mapper
import kadyshev.dmitry.domain.entities.Course
import kadyshev.dmitry.domain.repository.UserPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserPreferencesImpl(
    private val sharedPreferences: SharedPreferences,
    private val mapper: Mapper
) : UserPreferences {


    override fun setOnboardingCompleted() {
        sharedPreferences.edit().putBoolean(ONBOARDING_COMPLETED_KEY, true).apply()
    }

    override fun getLocalCoursesFlow(): Flow<List<Course>> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == LOCAL_COURSES_KEY) {
                try {
                    val courses = getLocalCourses()
                    trySend(courses)
                } catch (e: Exception) {
                    close(e)
                }
            }
        }

        trySend(getLocalCourses())

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override fun isOnboardingCompleted(): Boolean {
        val savedValue = sharedPreferences.getBoolean(ONBOARDING_COMPLETED_KEY, false)
        return savedValue
    }


    override fun saveCourse(course: Course) {
        val existingCourses = getLocalCourses().toMutableList()
        existingCourses.add(course)
        Log.d("saveCourse", existingCourses.toString())
        val coursesSet = existingCourses.map { mapper.courseToString(it) }.toSet()
        sharedPreferences.edit().putStringSet(LOCAL_COURSES_KEY, coursesSet).apply()
    }

    override fun removeCourse(course: Course) {
        val updatedCourses = getLocalCourses().filterNot { it.id == course.id }
        val coursesSet = updatedCourses.map { mapper.courseToString(it) }.toSet()
        Log.d("removeCourse", getLocalCourses().toString())
        sharedPreferences.edit().putStringSet(LOCAL_COURSES_KEY, coursesSet).apply()
        Log.d("removeCourse", getLocalCourses().toString())
    }

    override fun getLocalCourses(): List<Course> {
        val coursesSet =
            sharedPreferences.getStringSet(LOCAL_COURSES_KEY, emptySet()) ?: return emptyList()
        Log.d("getLocalCourses", coursesSet.toString())
        return coursesSet.mapNotNull { mapper.stringToCourse(it) }
    }



    companion object {
        private const val ONBOARDING_COMPLETED_KEY = "onboarding_completed"
        private const val LOCAL_COURSES_KEY = "local_courses"
    }
}
