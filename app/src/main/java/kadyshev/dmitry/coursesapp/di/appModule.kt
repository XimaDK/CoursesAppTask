package kadyshev.dmitry.coursesapp.di

import kadyshev.dmitry.coursesapp.viewmodels.FavoriteViewModel
import kadyshev.dmitry.coursesapp.viewmodels.HomeViewModel
import kadyshev.dmitry.coursesapp.viewmodels.LoginViewModel
import kadyshev.dmitry.coursesapp.viewmodels.MainViewModel
import kadyshev.dmitry.coursesapp.viewmodels.OnboardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MainViewModel> {
        MainViewModel(get())
    }

    viewModel<HomeViewModel> {
        HomeViewModel(get(), get(), get(), get())
    }

    viewModel<FavoriteViewModel> {
        FavoriteViewModel(get(), get())
    }



    viewModel<OnboardingViewModel> {
        OnboardingViewModel(get())
    }

    viewModel<LoginViewModel> {
        LoginViewModel()
    }
}