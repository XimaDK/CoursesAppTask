package kadyshev.dmitry.di

import kadyshev.dmitry.domain.usecases.GetFromDataSourceCourseUseCase
import kadyshev.dmitry.domain.usecases.GetFromNetworkCoursesUseCase
import kadyshev.dmitry.domain.usecases.IsOnboardingCompletedUseCase
import kadyshev.dmitry.domain.usecases.RemoveCourseUseCase
import kadyshev.dmitry.domain.usecases.SaveCourseUseCase
import kadyshev.dmitry.domain.usecases.SetOnboardingCompletedUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetFromDataSourceCourseUseCase(get())
    }
    factory {
        GetFromNetworkCoursesUseCase(get())
    }
    factory {
        RemoveCourseUseCase(get())
    }
    factory {
        SaveCourseUseCase(get())
    }
    factory {
        IsOnboardingCompletedUseCase(get())
    }
    factory {
        SetOnboardingCompletedUseCase(get())
    }
}