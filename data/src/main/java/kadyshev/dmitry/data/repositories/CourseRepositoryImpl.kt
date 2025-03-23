package kadyshev.dmitry.data.repositories

import kadyshev.dmitry.data.mapper.Mapper
import kadyshev.dmitry.data.network.ServiceApi
import kadyshev.dmitry.domain.entities.Course
import kadyshev.dmitry.domain.repository.CoursesRepository

class CourseRepositoryImpl(
    private val serviceApi: ServiceApi,
    private val mapper: Mapper
) : CoursesRepository {

    override suspend fun fetchRemoteCourses(): List<Course> {
        val response = serviceApi.getCourses()
        return mapper.coursesDtoToDomain(response)
    }
}