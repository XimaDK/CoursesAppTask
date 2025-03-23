package kadyshev.dmitry.coursesapp.states

import kadyshev.dmitry.domain.entities.Course

sealed class FavoriteFragmentState {
    data object Loading : FavoriteFragmentState()
    data class Success(val courses: List<Course>) : FavoriteFragmentState()
    data class Error(val message: String) : FavoriteFragmentState()
}
