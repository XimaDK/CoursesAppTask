package kadyshev.dmitry.coursesapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import kadyshev.dmitry.coursesapp.di.appModule
import kadyshev.dmitry.di.dataSourceModule
import kadyshev.dmitry.di.domainModule
import kadyshev.dmitry.di.networkModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        startKoin {
            androidContext(this@App)
            modules(domainModule, networkModule, dataSourceModule, appModule)
        }
    }
}