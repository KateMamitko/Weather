package com.example.weather.module

import com.example.weather.BuildConfig
import com.example.weather.data.impl.FavoriteRepositoryImpl
import com.example.weather.data.impl.SearchRepositoryImpl
import com.example.weather.data.impl.WeatherRepositoryImpl
import com.example.weather.data.network.ApiService
import com.example.weather.domain.repository.FavoriteRepository
import com.example.weather.domain.repository.SearchRepository
import com.example.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideFavoriteRepositoryImpl(impl: FavoriteRepositoryImpl): FavoriteRepository


    @Binds
    @Singleton
    abstract fun provideSearchRepositoryImpl(impl: SearchRepositoryImpl): SearchRepository


    @Binds
    @Singleton
    abstract fun provideWeatherRepositoryImpl(impl: WeatherRepositoryImpl): WeatherRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun okhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { it ->
            val originalRequest = it.request()
            val newRequest = originalRequest
                .url
                .newBuilder()
                .addQueryParameter("key", BuildConfig.WEATHER_API_KEY)
                .build()
            val newRes = originalRequest.newBuilder().url(newRequest).build()
            it.proceed(newRes)
        }
        .build()

    @Provides
    @Singleton
    fun retrofitBuilder(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
