package kadyshev.dmitry.data.mapper

import kadyshev.dmitry.data.network.dto.CoursesResponseDto
import kadyshev.dmitry.domain.entities.Course
import java.text.SimpleDateFormat
import java.util.Locale

class Mapper {


    private fun mapStringDateToSimpleDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

        val formattedDate = inputFormat.parse(date)?.let { parsedDate ->
            outputFormat.format(parsedDate)
        } ?: throw Exception("Incorrect Date")
        return formattedDate
    }

    fun coursesDtoToDomain(dto: CoursesResponseDto): List<Course> {
        return dto.courses.map { dtoCourse ->
            Course(
                id = dtoCourse.id,
                title = dtoCourse.title,
                text = dtoCourse.text,
                price = dtoCourse.price,
                rate = dtoCourse.rate,
                startDate = mapStringDateToSimpleDate(dtoCourse.startDate),
                hasLike = dtoCourse.hasLike,
                publishDate = dtoCourse.publishDate
            )
        }
    }

    fun stringToCourse(courseString: String): Course? {
        val data = courseString.split("|")
        return if (data.size == 8) {
            Course(
                id = data[0].toInt(),
                title = data[1],
                text = data[2],
                price = data[3],
                rate = data[4],
                startDate = data[5],
                hasLike = data[6].toBoolean(),
                publishDate = data[7]
            )
        } else {
            null
        }
    }

    fun courseToString(course: Course): String {
        return "${course.id}|${course.title}|${course.text}|${course.price}|${course.rate}|${course.startDate}|${course.hasLike}|${course.publishDate}"
    }

}