package kadyshev.dmitry.coursesapp.states

import kadyshev.dmitry.domain.entities.Course

sealed class HomeFragmentState {
    data object Loading : HomeFragmentState()
    data class Success(val courses: List<Course>) : HomeFragmentState()
    data class Error(val message: String) : HomeFragmentState()
}
