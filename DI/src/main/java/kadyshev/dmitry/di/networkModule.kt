package kadyshev.dmitry.di

import kadyshev.dmitry.data.network.ServiceApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<ServiceApi> { get<Retrofit>().create(ServiceApi::class.java) }

}