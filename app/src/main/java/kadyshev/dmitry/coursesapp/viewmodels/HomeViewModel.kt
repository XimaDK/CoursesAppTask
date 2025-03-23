package kadyshev.dmitry.coursesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kadyshev.dmitry.coursesapp.states.HomeFragmentState
import kadyshev.dmitry.domain.entities.Course
import kadyshev.dmitry.domain.usecases.GetFromDataSourceCourseUseCase
import kadyshev.dmitry.domain.usecases.GetFromNetworkCoursesUseCase
import kadyshev.dmitry.domain.usecases.RemoveCourseUseCase
import kadyshev.dmitry.domain.usecases.SaveCourseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel(
    private val getFromNetworkCoursesUseCase: GetFromNetworkCoursesUseCase,
    private val getFromDataSourceCourseUseCase: GetFromDataSourceCourseUseCase,
    private val saveCourseUseCase: SaveCourseUseCase,
    private val removeCourseUseCase: RemoveCourseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeFragmentState>(HomeFragmentState.Loading)
    val uiState: StateFlow<HomeFragmentState> = _uiState.asStateFlow()
    private var isSortedDescending = false
    private var originalCourses: List<Course> = emptyList()

    init {
        observeCourses()
    }

    private fun observeCourses() {
        combine(
            getFromNetworkCoursesUseCase(),
            getFromDataSourceCourseUseCase()
        ) { networkCourses, localLikedCourses ->
            networkCourses.map { networkCourse ->
                networkCourse.copy(
                    hasLike = localLikedCourses.any { it.id == networkCourse.id }
                )
            }
        }
            .onStart { _uiState.value = HomeFragmentState.Loading }
            .onEach { mergedCourses ->
                originalCourses = mergedCourses
                applyCurrentSorting()
            }
            .catch { error ->
                _uiState.value = HomeFragmentState.Error(error.message ?: "Ошибка загрузки")
            }
            .launchIn(viewModelScope)
    }

    fun sortCoursesDescending() {
        isSortedDescending = !isSortedDescending
        applyCurrentSorting()
    }
    private fun applyCurrentSorting() {
        val processedCourses = if (isSortedDescending) {
            sortCoursesByDateDescending(originalCourses)
        } else {
            originalCourses
        }
        _uiState.value = HomeFragmentState.Success(processedCourses)
    }

    private  fun sortCoursesByDateDescending(courses: List<Course>): List<Course> {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return courses.sortedByDescending {
            inputFormat.parse(it.publishDate) ?: throw Exception("Incorrect Date")
        }
    }

    fun onBookmarkClick(course: Course) {
        viewModelScope.launch {
            if (course.hasLike) {
                saveCourseUseCase(course)
            } else {
                removeCourseUseCase(course)
            }
        }
    }
}