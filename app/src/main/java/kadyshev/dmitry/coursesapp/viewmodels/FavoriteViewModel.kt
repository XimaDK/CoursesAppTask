package kadyshev.dmitry.coursesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kadyshev.dmitry.coursesapp.states.FavoriteFragmentState
import kadyshev.dmitry.domain.entities.Course
import kadyshev.dmitry.domain.usecases.GetFromDataSourceCourseUseCase
import kadyshev.dmitry.domain.usecases.RemoveCourseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFromDataSourceCourseUseCase: GetFromDataSourceCourseUseCase,
    private val removeCourseUseCase: RemoveCourseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteFragmentState>(FavoriteFragmentState.Loading)
    val uiState: StateFlow<FavoriteFragmentState> = _uiState.asStateFlow()

    init {
        observeFavoriteCourses()
    }

    private fun observeFavoriteCourses() {
        getFromDataSourceCourseUseCase()
            .onEach { courses ->
                _uiState.value = if (courses.isNotEmpty()) {
                    FavoriteFragmentState.Success(courses)
                } else {
                    FavoriteFragmentState.Success(emptyList())
                }
            }
            .catch { error ->
                _uiState.value = FavoriteFragmentState.Error(error.message ?: "Unknown error")
            }
            .launchIn(viewModelScope)
    }

    fun onBookmarkClick(course: Course) {
        viewModelScope.launch {
            removeCourseUseCase(course)
        }
    }
}
