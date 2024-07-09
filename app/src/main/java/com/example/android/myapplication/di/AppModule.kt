package com.example.android.myapplication.di

import com.example.android.myapplication.data.CityApiService
import com.example.android.myapplication.domain.OpenWeatherMapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

   @Provides
   fun provideOkHttp() = OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideCityApiService(retrofit: Retrofit): CityApiService {
        return retrofit.create(CityApiService::class.java)
    }
    @Provides
    fun provideApi(retrofit: Retrofit): OpenWeatherMapApi = retrofit.create(OpenWeatherMapApi::class.java)


}