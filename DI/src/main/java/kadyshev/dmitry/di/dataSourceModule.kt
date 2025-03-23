package kadyshev.dmitry.di

import android.content.Context
import android.content.SharedPreferences
import kadyshev.dmitry.data.dataSource.UserPreferencesImpl
import kadyshev.dmitry.data.mapper.Mapper
import kadyshev.dmitry.data.repositories.CourseRepositoryImpl
import kadyshev.dmitry.domain.repository.CoursesRepository
import kadyshev.dmitry.domain.repository.UserPreferences
import org.koin.dsl.module

val dataSourceModule = module {

    single {
        Mapper()
    }

    single<UserPreferences> {
        UserPreferencesImpl(get(), get())
    }

    single<SharedPreferences> {
        get<Context>().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }

    single<CoursesRepository> {
        CourseRepositoryImpl(get(), get())
    }

}