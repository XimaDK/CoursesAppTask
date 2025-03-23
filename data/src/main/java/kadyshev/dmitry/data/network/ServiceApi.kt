package kadyshev.dmitry.data.network

import kadyshev.dmitry.data.network.dto.CoursesResponseDto
import retrofit2.http.GET

interface ServiceApi {
    @GET("https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponseDto
}